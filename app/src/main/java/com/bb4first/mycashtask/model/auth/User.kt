package com.bb4first.mycashtask.model.auth

import com.google.gson.Gson

open class User(
    val userId: Int,
    var userName: String,
    var userEmail: String,
    var userImage: String,
    var userType: Int,
    var userPhone: String,
    var userStatus: Int,
    var userBalance: String?,
    var userAddresses: List<Address>,
) {
    fun toJson(): String {
        return Gson().toJson(this)
    }

    companion object {
        val TAG: String = User::class.java.simpleName

        fun fromJson(json: String): User {
            return Gson().fromJson(json, User::class.java)
        }
    }
}