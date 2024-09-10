package com.bb4first.mycashtask.model.auth

data class Address(
    val address: Any,
    val apartment: String,
    val building: String,
    val floor: Any,
    val id: Int,
    val lat: String,
    val lng: String,
    val name: Any,
    val street: String
)