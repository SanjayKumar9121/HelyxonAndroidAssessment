package com.sanjay.helyxonandroidassessment.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sanjay.helyxonandroidassessment.MainActivity
import com.sanjay.helyxonandroidassessment.R
import com.sanjay.helyxonandroidassessment.databinding.FragmentProductDetailsBinding
import com.squareup.picasso.Picasso

class ProductDetailsFragment : Fragment() {

    lateinit var binding: FragmentProductDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailsBinding.inflate(layoutInflater, container, false)

        val activity = requireActivity() as? MainActivity

        // Enable navigation icon (back button)
        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity?.supportActionBar?.setHomeAsUpIndicator(R.drawable.back_arrow)

        activity?.binding?.toolbar?.title = "Product Details"

        // Handle back button click
        activity?.binding?.toolbar?.setNavigationOnClickListener {
            activity.onBackPressed()
        }

        //Get the data from AllProductFragment
        val productName = arguments?.getString("productName")
        val productDescription = arguments?.getString("productDescription")
        val productImage = arguments?.getString("productImage")
        val productCategory = arguments?.getString("productCategory")
        val productPrice = arguments?.getInt("productPrice")
        val productRating = arguments?.getFloat("productRating") ?: 0.0f

        Picasso.get().load(productImage).into(binding.viewProductImgIV)
        binding.viewProductNameTV.text = productName
        binding.viewProductPriceTV.text = "₹ ${productPrice}"
        binding.viewProductDescriptionTV.text = "Description: ${productDescription}"
        binding.productCategoryTV.text = productCategory
        binding.viewProductRatingBar.rating = productRating


        binding.viewProductOrderBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Ordered Successfully", Toast.LENGTH_LONG).show()
        }

        return binding.root
    }

}