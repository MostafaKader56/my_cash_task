package com.bb4first.mycashtask.base

open class BaseItemUIState<T : Any?>(
    open val item: T,
    open var success: Boolean = false,
    open var errorMessage: String? = null
)

