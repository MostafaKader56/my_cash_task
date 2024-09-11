package com.bb4first.mycashtask.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bb4first.mycashtask.R
import com.bb4first.mycashtask.base.BaseItemUIState
import com.bb4first.mycashtask.base.BaseViewModel
import com.bb4first.mycashtask.model.home.homeBaseCategoriesResponse.HomeBaseCategoriesResponse
import com.bb4first.mycashtask.model.home.popularSellersResponse.PopularSellersResponse
import com.bb4first.mycashtask.model.home.trendingSellersResponse.TrendingSerllersResponse
import com.bb4first.mycashtask.nerwork.YjahzResource
import com.bb4first.mycashtask.repository.home.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) :
    BaseViewModel() {


    private val getHomeCategoriesResponse =
        MutableLiveData<YjahzResource<HomeBaseCategoriesResponse, Any?>>()
    val getHomeCategoriesResponseLiveData: LiveData<YjahzResource<HomeBaseCategoriesResponse, Any?>>
        get() = getHomeCategoriesResponse


    private val getTrendingSellersResponse =
        MutableLiveData<YjahzResource<TrendingSerllersResponse, Any?>>()
    val getTrendingSellersResponseLiveData: LiveData<YjahzResource<TrendingSerllersResponse, Any?>>
        get() = getTrendingSellersResponse


    private val getPopularSellersResponse =
        MutableLiveData<YjahzResource<PopularSellersResponse, Any?>>()
    val getPopularSellersResponseLiveData: LiveData<YjahzResource<PopularSellersResponse, Any?>>
        get() = getPopularSellersResponse

    fun setLoading(value:Boolean){
        startLoading.value = value
    }

    fun getBaseHomeCategories() {
        getHomeCategoriesResponse.value = YjahzResource.Loading
        viewModelScope.launch(Dispatchers.IO) {
            executeRequest<Any, BaseItemUIState<Any>>(
                id = R.id.get_home_base_categories,
                request = {
                    handleResponse(
                        R.id.get_home_base_categories,
                        mainRepository.getBaseHomeCategories(),
                        uiState = null
                    )
                },
                uiState = null,
            )
        }
    }

    fun getTrendingSellers(
        lat: Double? = null,
        long: Double? = null,
        filter: Double? = null,
        name: String? = null,
    ) {
        getTrendingSellersResponse.value = YjahzResource.Loading
        viewModelScope.launch(Dispatchers.IO) {
            executeRequest<Any, BaseItemUIState<Any>>(
                id = R.id.get_trending_sellers,
                request = {
                    handleResponse(
                        R.id.get_trending_sellers,
                        mainRepository.getTrendingSellers(lat, long, filter, name),
                        uiState = null
                    )
                },
                uiState = null,
            )
        }
    }

    fun getPopularSellers(
        lat: Double? = null,
        long: Double? = null,
        filter: Double? = null,
        name: String? = null,
    ) {
        getPopularSellersResponse.value = YjahzResource.Loading
        viewModelScope.launch(Dispatchers.IO) {
            executeRequest<Any, BaseItemUIState<Any>>(
                id = R.id.get_popular_sellers,
                request = {
                    handleResponse(
                        R.id.get_popular_sellers,
                        mainRepository.getPopularSellers(lat, long, filter, name),
                        uiState = null
                    )
                },
                uiState = null,
            )
        }
    }

    override fun <T> onSuccessfulResponse(
        id: Int, response: T, message: String?, uiState: BaseItemUIState<Any>?
    ) {
        when (id) {
            R.id.get_home_base_categories -> {
                getHomeCategoriesResponse.value = YjahzResource.Success(
                    data = response as HomeBaseCategoriesResponse,
                    uiState = uiState,
                    message = message,
                )
            }

            R.id.get_trending_sellers -> {
                getTrendingSellersResponse.value = YjahzResource.Success(
                    data = response as TrendingSerllersResponse,
                    uiState = uiState,
                    message = message,
                )
            }

            R.id.get_popular_sellers -> {
                getPopularSellersResponse.value = YjahzResource.Success(
                    data = response as PopularSellersResponse,
                    uiState = uiState,
                    message = message,
                )
            }
        }
    }

    override fun onFailedResponse(id: Int, response: String, uiState: BaseItemUIState<Any>?) {
        when (id) {
            R.id.get_home_base_categories -> {
                getHomeCategoriesResponse.value =
                    YjahzResource.Failure(error = response, uiState = uiState)
            }

            R.id.get_trending_sellers -> {
                getTrendingSellersResponse.value =
                    YjahzResource.Failure(error = response, uiState = uiState)
            }

            R.id.get_popular_sellers -> {
                getPopularSellersResponse.value =
                    YjahzResource.Failure(error = response, uiState = uiState)
            }
        }
    }
}