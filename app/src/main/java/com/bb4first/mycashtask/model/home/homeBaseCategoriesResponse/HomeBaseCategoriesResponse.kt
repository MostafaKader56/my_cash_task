package com.bb4first.mycashtask.model.home.homeBaseCategoriesResponse

import com.google.gson.annotations.SerializedName

data class HomeBaseCategoriesResponse(
    @SerializedName("cart_count")
    val cartCount: Int,
    val `data`: List<Category>
)