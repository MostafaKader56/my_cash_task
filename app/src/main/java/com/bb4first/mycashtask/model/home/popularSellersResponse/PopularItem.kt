package com.bb4first.mycashtask.model.home.popularSellersResponse

import com.google.gson.annotations.SerializedName

data class PopularItem(
    val address: String,
    val appointments: String,
    val categories: List<Category>,
    val description: String,
    val distance: String?,
    val email: String,
    val id: Int,
    val image: String,
    val information: Information,
    @SerializedName("is_favorite")
    val isFavorite: Boolean,
    val lat: String,
    val lng: String,
    val logo: String,
    val name: String,
    val phone: String,
    val popular: Int,
    @SerializedName("product_categories")
    val productCategories: List<ProductCategory>,
    val rate: String,
    val status: Int,
    val token: String,
    val trending: Int,
    val type: Int
)