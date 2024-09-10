package com.bb4first.mycashtask.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.bb4first.mycashtask.MyCashTaskApplication
import com.bb4first.mycashtask.R
import com.bb4first.mycashtask.sealed.ExecutePaginationCallResult
import com.bb4first.mycashtask.utlis.NetworkUtils
import com.bb4first.mycashtask.utlis.Utils.logCat
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

@ViewModelScoped
abstract class BaseViewModel() : ViewModel() {
    protected val startLoading = MutableLiveData<Boolean>()
    val startLoadingLiveData: LiveData<Boolean>
        get() = startLoading

    private val forceLogin = MutableLiveData<Unit>()
    val forceLoginLiveData: LiveData<Unit>
        get() = forceLogin

    private val forceUpdateApp = MutableLiveData<String?>()
    val forceUpdateAppLiveData: LiveData<String?>
        get() = forceUpdateApp


    suspend fun <T : Any, I : BaseItemUIState<T>> executeRequest(
        id: Int,
        request: suspend () -> Unit,
        uiState: BaseItemUIState<Any>?,
    ) {
        if (NetworkUtils.isConnected(MyCashTaskApplication.instance)) {
            request.invoke()
        } else {
            withContext(Dispatchers.Main) {
                onFailedResponse(
                    id,
                    MyCashTaskApplication.instance.getString(R.string.network_disconnected_message),
                    uiState = uiState,
                )
            }
        }
    }

    suspend fun <T : Any> handleResponse(
        id: Int,
        response: Response<BaseResponse<T>>?,
        uiState: BaseItemUIState<Any>?,
    ) {
        withContext(Dispatchers.Main) {
            if (response != null) {
                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()
                    when (responseBody?.responseCode) {
                        // TODO: Return to the back end if this codes is working in this project?
                        200, 201, 204 -> {
                            onSuccessfulResponse(
                                id,
                                responseBody.data,
                                responseBody.message,
                                uiState,
                            )
                        }

                        401 -> {
                            forceLogin.postValue(Unit)
                        }

                        422 -> {
                            forceUpdateApp.postValue(responseBody.message ?: "")
                        }

                        405 -> {
                            response.errorBody().logCat()
                            onFailedResponse(id, responseBody.message ?: "", uiState)
                        }

                        else -> {
                            onFailedResponse(id, responseBody?.message ?: "", uiState)
                        }
                    }
                } else if (response.isSuccessful.not() && response.code() == 500) {
                    onFailedResponse(id, "Server error", uiState)
                } else if (response.isSuccessful.not() && response.code() == 403) {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = try {
                        if (errorBody != null) {
                            JSONObject(errorBody).getString("message")
                        } else {
                            ""
                        }
                    } catch (e: JSONException) {
                        ""
                    }
                    onFailedResponse(id, errorMessage, uiState)
                } else {
                    onFailedResponse(id, response.errorBody().toString(), uiState)
                }
            } else {
                onFailedResponse(id, "", uiState)
            }
        }
    }

    // Example: Method to get paginated data
    fun <T : Any, A> getPaginatedData(
        pagingSource: BasePagingSource<T, A>, pageSize: Int
    ): LiveData<PagingData<T>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize, enablePlaceholders = false
            ),
            pagingSourceFactory = {
                pagingSource.setSuperErrorsHandler(
                    object : BasePagingSource.SuperErrorsHandler<T> {
                        override fun handleForceLoginError(error: ExecutePaginationCallResult.Error.ForceLogin): PagingSource.LoadResult.Error<Int, T> {
                            forceLogin.postValue(Unit)
                            return PagingSource.LoadResult.Error(Throwable(error.message))
                        }

                        override fun handleForceUpdateError(error: ExecutePaginationCallResult.Error.ForceUpdate): PagingSource.LoadResult.Error<Int, T> {
                            forceUpdateApp.postValue(error.message)
                            return PagingSource.LoadResult.Error(Throwable(error.message))
                        }
                    },
                )
            },
        ).liveData.cachedIn(viewModelScope)
    }

    abstract fun <T : Any?> onSuccessfulResponse(
        id: Int, response: T, message: String?, uiState: BaseItemUIState<Any>? = null
    )

    abstract fun onFailedResponse(
        id: Int,
        response: String,
        uiState: BaseItemUIState<Any>? = null
    )
}