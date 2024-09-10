package com.bb4first.mycashtask.viewmodel.home

import com.bb4first.mycashtask.base.BaseItemUIState
import com.bb4first.mycashtask.base.BaseViewModel

class MainViewModel : BaseViewModel() {
    override fun <T> onSuccessfulResponse(
        id: Int, response: T, message: String?, uiState: BaseItemUIState<Any>?
    ) {

    }

    override fun onFailedResponse(id: Int, response: String, uiState: BaseItemUIState<Any>?) {
    }
}