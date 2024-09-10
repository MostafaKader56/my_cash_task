package com.bb4first.mycashtask.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bb4first.mycashtask.R
import com.bb4first.mycashtask.base.BaseItemUIState
import com.bb4first.mycashtask.base.BaseViewModel
import com.bb4first.mycashtask.base.SingleLiveEvent
import com.bb4first.mycashtask.model.auth.LoginResponse
import com.bb4first.mycashtask.nerwork.YjahzResource
import com.bb4first.mycashtask.repository.auth.LoginRepository
import com.bb4first.mycashtask.utlis.SharedPreferencesModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    BaseViewModel() {
    private val loginUserResponse = SingleLiveEvent<YjahzResource<LoginResponse, Any?>>()
    val loginUserResponseLiveData: LiveData<YjahzResource<LoginResponse, Any?>>
        get() = loginUserResponse

    fun login(email: String, password: String, deviceToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            startLoading.postValue(true)

            executeRequest<Any, BaseItemUIState<Any>>(
                id = R.id.login,
                request = {
                    handleResponse(
                        R.id.login,
                        loginRepository.login(
                            email = email,
                            password = password,
                            deviceToken = deviceToken
                        ),
                        uiState = null
                    )
                },
                uiState = null,
            )

            startLoading.postValue(false)
        }
    }


    override fun <T> onSuccessfulResponse(
        id: Int,
        response: T,
        message: String?,
        uiState: BaseItemUIState<Any>?
    ) {
        when (id) {
            R.id.login -> {
                val loginResponse = response as LoginResponse
                SharedPreferencesModule.setStringValue(SharedPreferencesModule.PREF_APP_TOKEN, loginResponse.token)
                SharedPreferencesModule.setUserValue(loginResponse)

                loginUserResponse.value =
                    YjahzResource.Success(
                        data = loginResponse,
                        message = message,
                        uiState = uiState,
                    )
            }
        }
    }

    override fun onFailedResponse(
        id: Int,
        response: String,
        uiState: BaseItemUIState<Any>?
    ) {
        when (id) {
            R.id.login -> {
                loginUserResponse.value =
                    YjahzResource.Failure(
                        error = response,
                        uiState = uiState,
                    )
            }
        }
    }
}