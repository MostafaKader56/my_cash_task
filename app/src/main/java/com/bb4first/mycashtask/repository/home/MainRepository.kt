package com.bb4first.mycashtask.repository.home

import com.bb4first.mycashtask.nerwork.api_helper.home.MainApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(private val mainApiHelper: MainApiHelper) {
    suspend fun getBaseHomeCategories() = mainApiHelper.getHomeBaseCategories()
    suspend fun getPopularSellers(
        lat: Double?,
        long: Double?,
        filter: Double?,
        name: String?,
    ) = mainApiHelper.getPopularSellers(lat, long, filter, name)

    suspend fun getTrendingSellers(
        lat: Double?,
        long: Double?,
        filter: Double?,
        name: String?,
    ) = mainApiHelper.getTrendingSellers(lat, long, filter, name)
}