package com.bb4first.mycashtask.model.home.homeBaseCategoriesResponse

import com.google.gson.annotations.SerializedName

data class Category(
    val active: Int,
    val id: Int,
    val image: String,
    val name: String,
    @SerializedName("name_ar")
    val nameAr: String,
    @SerializedName("name_en")
    val nameEn: String
)