package com.bb4first.mycashtask.nerwork

import com.bb4first.mycashtask.base.BaseResponse
import com.bb4first.mycashtask.model.auth.LoginResponse
import com.bb4first.mycashtask.model.auth.SignUpResponse
import com.bb4first.mycashtask.model.home.homeBaseCategoriesResponse.HomeBaseCategoriesResponse
import com.bb4first.mycashtask.model.home.popularSellersResponse.PopularSellersResponse
import com.bb4first.mycashtask.model.home.trendingSellersResponse.TrendingSerllersResponse
import com.bb4first.mycashtask.utlis.Constants.GET_HOME_CATEGORIES
import com.bb4first.mycashtask.utlis.Constants.GET_POPULAR
import com.bb4first.mycashtask.utlis.Constants.GET_TRENDING
import com.bb4first.mycashtask.utlis.Constants.LOGIN
import com.bb4first.mycashtask.utlis.Constants.SIGN_UP
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @Multipart
    @POST(SIGN_UP)
    suspend fun signUp(
        @HeaderMap headers: Map<String, String>,
        @Part name: MultipartBody.Part,
        @Part email: MultipartBody.Part,
        @Part password: MultipartBody.Part,
        @Part phone: MultipartBody.Part,
        @Part deviceToken: MultipartBody.Part
    ): Response<BaseResponse<SignUpResponse>>?

    @Multipart
    @POST(LOGIN)
    suspend fun login(
        @HeaderMap headers: Map<String, String>,
        @Part email: MultipartBody.Part,
        @Part password: MultipartBody.Part,
        @Part deviceToken: MultipartBody.Part
    ): Response<BaseResponse<LoginResponse>>?

    @GET(GET_HOME_CATEGORIES)
    suspend fun getHomeCategories(
        @HeaderMap headers: Map<String, String>,
    ): Response<BaseResponse<HomeBaseCategoriesResponse>>?

    @GET(GET_TRENDING)
    suspend fun getTrendingSellers(
        @Query("lat") lat: Double?,
        @Query("lng") lng: Double?,
        @HeaderMap headers: Map<String, String>,
    ): Response<BaseResponse<TrendingSerllersResponse>>?

    @GET(GET_POPULAR)
    suspend fun getPopularSellers(
        @Query("lat") lat: Double?,
        @Query("lng") lng: Double?,
        @HeaderMap headers: Map<String, String>,
    ): Response<BaseResponse<PopularSellersResponse>>?
}