package com.bb4first.mycashtask.nerwork.api_helper_impl.home

import com.bb4first.mycashtask.nerwork.ApiService
import com.bb4first.mycashtask.nerwork.RequestHeaders
import com.bb4first.mycashtask.nerwork.api_helper.home.MainApiHelper
import javax.inject.Inject

class MainApiHelperImpl @Inject constructor(private val apiService: ApiService) : MainApiHelper {
    override suspend fun getHomeBaseCategories() =
        apiService.getHomeCategories(RequestHeaders.getHeaders())

    override suspend fun getTrendingSellers(
        lat: Double?,
        long: Double?,
        filter: Double?,
        name: String?
    ) = apiService.getTrendingSellers(
        lat = lat, lng = long, headers = RequestHeaders.getHeaders(),
    )

    override suspend fun getPopularSellers(
        lat: Double?,
        long: Double?,
        filter: Double?,
        name: String?
    ) = apiService.getPopularSellers(
        lat = lat, lng = long, headers = RequestHeaders.getHeaders(),
    )
}