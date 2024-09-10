package com.bb4first.mycashtask.nerwork.api_helper_impl

import com.bb4first.mycashtask.nerwork.ApiService
import com.bb4first.mycashtask.nerwork.RequestHeaders
import com.bb4first.mycashtask.nerwork.api_helper.SignUpApiHelper
import okhttp3.MultipartBody
import javax.inject.Inject

class SignUpApiHelperImpl @Inject constructor(private val apiService: ApiService) :
    SignUpApiHelper, RequestHeaders() {
    override suspend fun signUp(
        name: String,
        email: String,
        password: String,
        phone: String,
        deviceToken: String
    ) = apiService.signUp(
        headers = getHeaders(),
        name = MultipartBody.Part.createFormData("name", name),
        email = MultipartBody.Part.createFormData("email", email),
        password = MultipartBody.Part.createFormData("password", password),
        phone = MultipartBody.Part.createFormData("phone", phone),
        deviceToken = MultipartBody.Part.createFormData("device_token", deviceToken),
    )
}