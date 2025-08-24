package com.appsv.fashionmall.common

import com.appsv.fashionmall.domain.models.BannerDataModels
import com.appsv.fashionmall.domain.models.CategoryDataModels
import com.appsv.fashionmall.domain.models.ProductDataModels

data class HomeScreenState(

    val isLoading: Boolean = true,
    val errorMessages: String? = null,
    val categories: List<CategoryDataModels>? = null,
    val products: List<ProductDataModels>? = null,
    val banner: List<BannerDataModels>? = null

    )