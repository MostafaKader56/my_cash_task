package com.bb4first.mycashtask.sealed

import com.bb4first.mycashtask.enums.PaginationStaticErrorsKind

sealed class ExecutePaginationCallResult<T> {
    data class Success<T>(val data: List<T>) : ExecutePaginationCallResult<T>()

    sealed class Error : ExecutePaginationCallResult<Nothing>() {
        data class Message(val message: String) : Error()
        data class StaticKinds(val kind: PaginationStaticErrorsKind) : Error()
        data class Exception(val throwable: Throwable) : Error()

        // Error that should be handled in BaseViewModel
        data class ForceLogin(val message: String) : Error()

        // Error that should be handled in BaseViewModel
        data class ForceUpdate(val message: String) : Error()
    }
}