package com.bb4first.mycashtask.model.auth

import com.google.gson.Gson

class User(
    val id: Int,
    var name: String,
    var email: String,
    var image: String,
    var type: Int,
    var phone: String,
    var status: Int,
    var balance: String?,
    var addresses: List<Address>,
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