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
) {
    fun toUser() = User(
        id = id,
        name = name,
        email = email,
        image = image,
        type = type,
        phone = phone,
        status = status,
        balance = balance,
        addresses = addresses
    )
}