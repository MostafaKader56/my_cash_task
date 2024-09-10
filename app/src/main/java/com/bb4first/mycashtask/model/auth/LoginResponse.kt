package com.bb4first.mycashtask.model.auth

data class LoginResponse(
    val addresses: List<Address>,
    val balance: String,
    val email: String,
    val id: Int,
    val image: String,
    val name: String,
    val phone: String,
    val status: Int,
    val token: String,
    val type: Int
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