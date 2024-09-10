package com.bb4first.mycashtask.model.auth

data class SignUpResponse(
    val addresses: List<Address>,
    val balance: String?,
    val email: String,
    val id: Int,
    val image: String,
    val name: String,
    val phone: String,
    val status: Int,
    val type: Int,
    val token: String,
) : User(
    userAddresses = addresses,
    userBalance = balance,
    userEmail = email,
    userId = id,
    userImage = image,
    userName = name,
    userPhone = phone,
    userStatus = status,
    userType = type,
)