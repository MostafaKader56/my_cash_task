package com.bb4first.mycashtask.nerwork.api_helper.home

import com.bb4first.mycashtask.base.BaseResponse
import com.bb4first.mycashtask.model.home.homeBaseCategoriesResponse.HomeBaseCategoriesResponse
import com.bb4first.mycashtask.model.home.popularSellersResponse.PopularSellersResponse
import com.bb4first.mycashtask.model.home.trendingSellersResponse.TrendingSerllersResponse
import retrofit2.Response

interface MainApiHelper {
    suspend fun getHomeBaseCategories(): Response<BaseResponse<HomeBaseCategoriesResponse>>?
    suspend fun getTrendingSellers(
        lat: Double?,
        long: Double?,
        filter: Double?,
        name: String?,
    ): Response<BaseResponse<TrendingSerllersResponse>>?

    suspend fun getPopularSellers(
        lat: Double?,
        long: Double?,
        filter: Double?,
        name: String?,
    ): Response<BaseResponse<PopularSellersResponse>>?
}