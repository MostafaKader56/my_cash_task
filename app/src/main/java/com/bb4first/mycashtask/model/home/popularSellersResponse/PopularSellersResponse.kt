package com.bb4first.mycashtask.model.home.popularSellersResponse

data class PopularSellersResponse(
    val `data`: List<PopularItem>,
    val pagination: Pagination
)