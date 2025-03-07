package com.sanjay.helyxonandroidassessment.ViewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanjay.helyxonandroidassessment.Models.AllProductsList
import com.sanjay.helyxonandroidassessment.Object.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllProductsViewModel: ViewModel() {
     var products = MutableLiveData<ArrayList<AllProductsList>>()

    val isLoading = MutableLiveData<Boolean>()

    val errorMessage = MutableLiveData<String>()

    private var currentPage = 1
    private val pageSize = 4
    private val allProductsList = ArrayList<AllProductsList>()


    //Fetching data from API
    fun fetchAllProducts() {

        if (isLoading.value == true) return

        isLoading.value = true

        viewModelScope.launch {
            try {
                val apiService = RetrofitClient.instance
                apiService.getAllProducts().enqueue(object : Callback<ArrayList<AllProductsList>> {
                    override fun onResponse(
                        call: Call<ArrayList<AllProductsList>>,
                        response: Response<ArrayList<AllProductsList>>
                    ) {
                        isLoading.value = false
                        if (response.isSuccessful) {
                            val apiResponses = response.body()
                            if (apiResponses != null) {
                                allProductsList.clear()
                                allProductsList.addAll(apiResponses)
                                currentPage = 1
                                loadPaginatedData()
                            } else {
                                errorMessage.value = "No products found."
                            }
                        } else {
                            errorMessage.value = "Failed to fetch data. Error: ${response.code()}"
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<AllProductsList>>, t: Throwable) {
                        isLoading.value = false
                        errorMessage.value = "Please check Your Internet Connectivity"
                        Log.e("TAG", "Error fetching message: $t")
                    }

                })
            } catch (e: Exception) {
                isLoading.value = false
                Log.e("TAG", "Exception fetching products: ${e.message}")
                errorMessage.postValue("Unexpected error occurred.")
            }
        }
    }

    //Load Pagination
    fun loadPaginatedData() {
        val start = (currentPage - 1) * pageSize
        val end = minOf(start + pageSize, allProductsList.size)
        if (start < allProductsList.size) {
            products.postValue(ArrayList(allProductsList.subList(0, end)))
            currentPage++
        }
    }

    //Sort Products by price
    fun sortProductsByPrice(ascending: Boolean) {
        val sortedList = if (ascending) {
            products.value?.sortedBy { it.price }
        } else {
            products.value?.sortedByDescending { it.price }
        }
        products.postValue(ArrayList(sortedList))
    }

}