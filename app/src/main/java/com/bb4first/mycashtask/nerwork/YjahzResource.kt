package com.bb4first.mycashtask.nerwork

import com.bb4first.mycashtask.base.BaseItemUIState

sealed class YjahzResource<out T, out U> {
    data class Success<T : Any?, U : Any?>(
        val data: T? = null,
        val message: String? = null,
        val uiState: BaseItemUIState<U>?
    ) : YjahzResource<T, U>()

    data class Failure<U>(
        val error: String? = null,
        val uiState: BaseItemUIState<U>?
    ) : YjahzResource<Nothing, U>()

    data object Loading : YjahzResource<Nothing, Nothing>()
}


