package com.bb4first.mycashtask.nerwork.api_helper.auth

import com.bb4first.mycashtask.base.BaseResponse
import com.bb4first.mycashtask.model.auth.SignUpResponse
import retrofit2.Response

interface SignUpApiHelper {
    suspend fun signUp(
        name: String,
        email: String,
        password: String,
        phone: String,
        deviceToken: String,
    ): Response<BaseResponse<SignUpResponse>>?
}