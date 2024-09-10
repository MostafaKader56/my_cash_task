package com.bb4first.mycashtask.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bb4first.mycashtask.R
import com.bb4first.mycashtask.base.BaseItemUIState
import com.bb4first.mycashtask.base.BaseViewModel
import com.bb4first.mycashtask.base.SingleLiveEvent
import com.bb4first.mycashtask.model.auth.SignUpResponse
import com.bb4first.mycashtask.nerwork.YjahzResource
import com.bb4first.mycashtask.repository.auth.SignUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpRepository: SignUpRepository) :
    BaseViewModel() {

    private val registerUserResponse = SingleLiveEvent<YjahzResource<SignUpResponse, Any?>>()
    val registerUserResponseLiveData: LiveData<YjahzResource<SignUpResponse, Any?>>
        get() = registerUserResponse

    override fun <T, I, U : BaseItemUIState<I>> onSuccessfulResponse(
        id: Int, response: T, message: String?, uiState: U?
    ) {
        when (id) {
            R.id.sign_up -> {
                registerUserResponse.value =
                    YjahzResource.Success<SignUpResponse, Any>(
                        data = response as SignUpResponse,
                        message = message,
                        uiState = null,
                    )
            }
        }
    }

    override fun <I, U : BaseItemUIState<I>> onFailedResponse(
        id: Int, response: String, uiState: U?
    ) {
        when (id) {
            R.id.sign_up -> {
                registerUserResponse.value =
                    YjahzResource.Failure<Any>(
                        uiState = null,
                        error = response,
                    )
            }
        }
    }

    fun signUp(
        name: String,
        email: String,
        password: String,
        phone: String,
        deviceToken: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            startLoading.postValue(true)

            executeRequest<Any, BaseItemUIState<Any>>(
                id = R.id.sign_up,
                request = {
                    handleResponse(
                        R.id.sign_up, signUpRepository.signUp(
                            name = name,
                            email = email,
                            password = password,
                            phone = phone,
                            deviceToken = deviceToken,
                        ), uiState = null
                    )
                },
                uiState = null,
            )

            startLoading.postValue(false)
        }
    }
}