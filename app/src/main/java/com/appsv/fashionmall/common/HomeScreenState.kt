package com.appsv.fashionmall.common

import com.appsv.fashionmall.domain.models.BannerDataModels
import com.appsv.fashionmall.domain.models.CategoryDataModels

data class HomeScreenState (

    val isLoading : Boolean = true,
    val errorMessages : String? = null,
    val categories : List<CategoryDataModels>? = null,
    val products : List<CategoryDataModels>? = null,
    val banner : List<BannerDataModels>? = null

    )