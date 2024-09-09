package com.bb4first.mycashtask.base

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bb4first.mycashtask.MyCashTaskApplication
import com.bb4first.mycashtask.R
import com.bb4first.mycashtask.enums.PaginationStaticErrorsKind
import com.bb4first.mycashtask.sealed.ExecutePaginationCallResult
import com.bb4first.mycashtask.utlis.NetworkUtils
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class BasePagingSource<ItemType : Any, CallResponseType>(
    private val errorsHandler: ErrorHandler<ItemType>,
    private val startingPageIndex: Int,
    private val loadDataRequest: suspend (pageIndex: Int, pageSize: Int) -> Response<BaseResponse<CallResponseType>>?,
) : PagingSource<Int, ItemType>() {

    lateinit var superErrorsHandlers: SuperErrorsHandler<ItemType>

    fun setSuperErrorsHandler(handler: SuperErrorsHandler<ItemType>): BasePagingSource<ItemType, CallResponseType> {
        this.superErrorsHandlers = handler
        return this
    }

    abstract fun convert(input: CallResponseType): List<ItemType>
    suspend fun executeRequest(
        request: suspend () -> Response<BaseResponse<CallResponseType>>?
    ): ExecutePaginationCallResult<out ItemType> {
        return if (NetworkUtils.isConnected(MyCashTaskApplication.instance)) {
            handleResponse(request)
        } else {
            ExecutePaginationCallResult.Error.Message(MyCashTaskApplication.instance.getString(R.string.network_disconnected_message))
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemType> {
        val page = params.key ?: startingPageIndex
        val response = executeRequest(request = { loadDataRequest.invoke(page, params.loadSize) })
        return when (response) {
            is ExecutePaginationCallResult.Error -> {
                when (response) {
                    is ExecutePaginationCallResult.Error.Exception -> {
                        errorsHandler.handleExceptionError(response)
                    }

                    is ExecutePaginationCallResult.Error.ForceLogin -> {
                        superErrorsHandlers.handleForceLoginError(response)
                    }

                    is ExecutePaginationCallResult.Error.ForceUpdate -> {
                        superErrorsHandlers.handleForceUpdateError(response)
                    }

                    is ExecutePaginationCallResult.Error.Message -> {
                        errorsHandler.handleMessageError(response)
                    }

                    is ExecutePaginationCallResult.Error.StaticKinds -> {
                        errorsHandler.handleStaticKindError(response)
                    }
                }
            }

            is ExecutePaginationCallResult.Success -> {
                LoadResult.Page(
                    data = response.data,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (response.data.isEmpty()) null else page + 1
                )
            }
        }
    }

    private suspend fun handleResponse(
        request: suspend () -> Response<BaseResponse<CallResponseType>>?,
    ): ExecutePaginationCallResult<out ItemType> {
        return try {
            val response = request.invoke()
            if (response != null) {
                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()
                    when (responseBody?.status) {
                        // TODO: Return to the back end if this codes is working in this project?
                        200, 201, 204 -> {
                            if (responseBody.response != null) return ExecutePaginationCallResult.Success(
                                convert(responseBody.response!!)
                            )
                            else return ExecutePaginationCallResult.Error.StaticKinds(
                                PaginationStaticErrorsKind.Unknown
                            )
                        }

                        401 -> {
                            return ExecutePaginationCallResult.Error.ForceLogin(
                                message = responseBody.message ?: ""
                            )
                        }

                        422 -> {
                            return ExecutePaginationCallResult.Error.ForceUpdate(
                                message = responseBody.message ?: ""
                            )
                        }

                        else -> {
                            val message = responseBody?.message
                            if (!message.isNullOrBlank()) return ExecutePaginationCallResult.Error.Message(
                                message
                            )
                            else return ExecutePaginationCallResult.Error.StaticKinds(
                                PaginationStaticErrorsKind.Unknown
                            )
                        }
                    }
                } else if (response.isSuccessful.not() && response.code() == 500) {
                    return ExecutePaginationCallResult.Error.StaticKinds(
                        PaginationStaticErrorsKind.ServerError
                    )
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
                    if (!errorMessage.isNullOrBlank()) return ExecutePaginationCallResult.Error.Message(
                        errorMessage
                    )
                    else return ExecutePaginationCallResult.Error.StaticKinds(
                        PaginationStaticErrorsKind.Unknown
                    )
                } else {
                    return ExecutePaginationCallResult.Error.Message(
                        response.errorBody().toString()
                    )
                }
            } else {
                return ExecutePaginationCallResult.Error.StaticKinds(
                    PaginationStaticErrorsKind.Unknown
                )
            }
        } catch (ex: Exception) {
            ExecutePaginationCallResult.Error.Exception(ex)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ItemType>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


    // Errors to handle in Subclass of BaseViewModel
    interface ErrorHandler<ItemType : Any> {
        fun handleStaticKindError(error: ExecutePaginationCallResult.Error.StaticKinds): LoadResult.Error<Int, ItemType>
        fun handleExceptionError(error: ExecutePaginationCallResult.Error.Exception): LoadResult.Error<Int, ItemType>
        fun handleMessageError(error: ExecutePaginationCallResult.Error.Message): LoadResult.Error<Int, ItemType>
    }

    // Errors to handle in BaseViewModel
    interface SuperErrorsHandler<ItemType : Any> {
        fun handleForceLoginError(error: ExecutePaginationCallResult.Error.ForceLogin): LoadResult.Error<Int, ItemType>
        fun handleForceUpdateError(error: ExecutePaginationCallResult.Error.ForceUpdate): LoadResult.Error<Int, ItemType>
    }
}




