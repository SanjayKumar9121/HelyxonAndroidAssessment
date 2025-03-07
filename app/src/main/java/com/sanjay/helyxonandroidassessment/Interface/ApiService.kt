package com.sanjay.helyxonandroidassessment.Interface

import com.sanjay.helyxonandroidassessment.Models.AllProductsList
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    fun getAllProducts(): retrofit2.Call<ArrayList<AllProductsList>>
}