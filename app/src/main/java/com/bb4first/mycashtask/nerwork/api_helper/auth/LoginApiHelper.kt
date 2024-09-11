package com.bb4first.mycashtask.nerwork.api_helper.auth

import com.bb4first.mycashtask.base.BaseResponse
import com.bb4first.mycashtask.model.auth.LoginResponse
import retrofit2.Response

interface LoginApiHelper {
    suspend fun login(
        email: String,
        password: String,
        deviceToken: String
    ): Response<BaseResponse<LoginResponse>>?

}