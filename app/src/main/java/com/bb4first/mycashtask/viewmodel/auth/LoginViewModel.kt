package com.bb4first.mycashtask.viewmodel.auth

import com.bb4first.mycashtask.base.BaseItemUIState
import com.bb4first.mycashtask.base.BaseViewModel

//@HiltViewModel
class LoginViewModel
//@Inject constructor(
//    // Add your dependencies here, if any
//)
    : BaseViewModel() {
    override fun <T, I, U : BaseItemUIState<I>> onSuccessfulResponse(
        id: Int,
        response: T,
        message: String?,
        uiState: U?
    ) {

    }

    override fun <I, U : BaseItemUIState<I>> onFailedResponse(
        id: Int,
        response: String,
        uiState: U?
    ) {
    }
}