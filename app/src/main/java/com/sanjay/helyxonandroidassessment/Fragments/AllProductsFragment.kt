package com.sanjay.helyxonandroidassessment.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sanjay.helyxonandroidassessment.Adapters.AllProductsAdapter
import com.sanjay.helyxonandroidassessment.MainActivity
import com.sanjay.helyxonandroidassessment.Models.AllProductsList
import com.sanjay.helyxonandroidassessment.R
import com.sanjay.helyxonandroidassessment.ViewModels.AllProductsViewModel
import com.sanjay.helyxonandroidassessment.databinding.FragmentAllProductsBinding

class AllProductsFragment : Fragment() {

    lateinit var binding: FragmentAllProductsBinding
    private val allProductsVM: AllProductsViewModel by viewModels()
    private var appListsData = ArrayList<AllProductsList>()
    private var filteredData = ArrayList<AllProductsList>()
    lateinit var adapter: AllProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Inflate the layout for this fragment
        binding = FragmentAllProductsBinding.inflate(layoutInflater, container, false)

        val activity = requireActivity() as? MainActivity
        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        activity?.binding?.toolbar?.title = "All Products"

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity.finishAffinity()
            }
        })

        setupRecyclerView()
        setupSorting()
        setupObservers()
        setupSearchView()

        binding.progressLoader.visibility = View.VISIBLE

        allProductsVM.fetchAllProducts()

        return binding.root
    }

    //Recycler View
    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = AllProductsAdapter(filteredData)
        binding.recyclerView.adapter = adapter

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!allProductsVM.isLoading.value!! &&
                    (visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                    firstVisibleItemPosition >= 0) {
                    allProductsVM.loadPaginatedData()
                }
            }
        })

        adapter.setOnClickListeners(object : AllProductsAdapter.OnItemClickListener {
            override fun onItemClick(itemLists: AllProductsList, position: Int) {
                val productsDetailsFragment = ProductDetailsFragment().apply {
                    arguments = Bundle().apply {
                        putString("productName", itemLists.title)
                        putString("productDescription", itemLists.description)
                        putString("productImage", itemLists.image)
                        putString("productCategory", itemLists.category)
                        putInt("productPrice", itemLists.price?.toInt() ?: 0)
                        putFloat("productRating", itemLists.rating?.rate!!)
                    }
                }
                replaceFragment(productsDetailsFragment)
            }
        })
    }

    //Observes live data from ViewModel and updates UI
    private fun setupObservers() {
        binding.progressLoader.visibility = View.VISIBLE

        allProductsVM.products.observe(viewLifecycleOwner) { response ->
            binding.progressLoader.visibility = View.GONE
            response?.let {
                appListsData.clear()
                appListsData.addAll(it)
                filteredData.clear()
                filteredData.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }

        allProductsVM.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressLoader.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        allProductsVM.errorMessage.observe(viewLifecycleOwner) { errorMsg ->
            if (!errorMsg.isNullOrEmpty()) {
                Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Search View
    private fun setupSearchView() {
        binding.productsSearchBar.queryHint = "Search"
        binding.productsSearchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterProducts(newText)
                return true
            }
        })
    }

    //Filtering Products
    private fun filterProducts(query: String?) {
        filteredData.clear()

        if (query.isNullOrEmpty()) {
            filteredData.addAll(appListsData)
        } else {
            filteredData.addAll(
                appListsData.filter { it.title?.contains(query, true) ?: false }
            )
        }

        // Toggle visibility of RecyclerView and "No Data Found" text
        binding.recyclerView.visibility = if (filteredData.isEmpty()) View.GONE else View.VISIBLE
        binding.noDataFoundTV.visibility = if (filteredData.isEmpty()) View.VISIBLE else View.GONE

        adapter.notifyDataSetChanged()
    }

    //Setup Sort Button
    fun setupSorting() {
        binding.sortButton.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), binding.sortButton)
            popupMenu.menuInflater.inflate(R.menu.sort_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.sort_price_asc -> allProductsVM.sortProductsByPrice(true)
                    R.id.sort_price_desc -> allProductsVM.sortProductsByPrice(false)
                }
                true
            }
            popupMenu.show()
        }
    }

    //Replace Fragment
    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}