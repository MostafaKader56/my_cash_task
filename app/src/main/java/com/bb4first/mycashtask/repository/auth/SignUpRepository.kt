package com.bb4first.mycashtask.repository.auth

import com.bb4first.mycashtask.nerwork.api_helper.SignUpApiHelper
import javax.inject.Inject

class SignUpRepository @Inject constructor(private val signUpApiHelper: SignUpApiHelper) {
    suspend fun signUp(
        name: String,
        email: String,
        password: String,
        phone: String,
        deviceToken: String,
    ) = try {
        signUpApiHelper.signUp(
            name = name,
            email = email,
            password = password,
            phone = phone,
            deviceToken = deviceToken
        )
    } catch (ex: Exception) {
        ex.printStackTrace()
        null
    }
}