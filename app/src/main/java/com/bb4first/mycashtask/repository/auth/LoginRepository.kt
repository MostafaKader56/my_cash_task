package com.bb4first.mycashtask.repository.auth

import com.bb4first.mycashtask.nerwork.api_helper.LoginApiHelper
import javax.inject.Inject

class LoginRepository @Inject constructor(private val loginApiHelper: LoginApiHelper) {
    suspend fun login(
        email: String,
        password: String,
        deviceToken: String,
    ) = try {
        loginApiHelper.login(
            email = email,
            password = password,
            deviceToken = deviceToken
        )
    } catch (ex: Exception) {
        ex.printStackTrace()
        null
    }
}