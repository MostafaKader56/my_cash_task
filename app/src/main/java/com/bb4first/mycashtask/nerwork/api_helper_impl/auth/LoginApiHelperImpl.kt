package com.bb4first.mycashtask.nerwork.api_helper_impl.auth

import com.bb4first.mycashtask.nerwork.ApiService
import com.bb4first.mycashtask.nerwork.RequestHeaders
import com.bb4first.mycashtask.nerwork.api_helper.auth.LoginApiHelper
import okhttp3.MultipartBody
import javax.inject.Inject

class LoginApiHelperImpl @Inject constructor(private val apiService: ApiService) : LoginApiHelper {
    override suspend fun login(
        email: String,
        password: String,
        deviceToken: String,
    ) = apiService.login(
        headers = RequestHeaders.getHeaders(),
        email = MultipartBody.Part.createFormData("email", email),
        password = MultipartBody.Part.createFormData("password", password),
        deviceToken = MultipartBody.Part.createFormData("deviceToken", deviceToken),
    )
}