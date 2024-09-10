package com.bb4first.mycashtask.base

import com.google.gson.annotations.SerializedName

open class BaseResponse<T>(
    open val success: Boolean? = null,
    open val message: String? = null,
    open var data: T? = null,
    @SerializedName("response_code")
    open var responseCode: Int? = null,
)


















