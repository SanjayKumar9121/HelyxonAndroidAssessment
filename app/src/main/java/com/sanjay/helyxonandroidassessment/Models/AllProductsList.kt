package com.sanjay.helyxonandroidassessment.Models

import com.google.gson.annotations.SerializedName

data class AllProductsList(
    @SerializedName("id"          ) var id          : Int?    = null,
    @SerializedName("title"       ) var title       : String? = null,
    @SerializedName("price"       ) var price       : Double? = null,
    @SerializedName("description" ) var description : String? = null,
    @SerializedName("category"    ) var category    : String? = null,
    @SerializedName("image"       ) var image       : String? = null,
    @SerializedName("rating"      ) var rating      : Rating? = Rating()
)

data class Rating (
    @SerializedName("rate"  ) var rate  : Float? = null,
    @SerializedName("count" ) var count : Int?    = null
)
