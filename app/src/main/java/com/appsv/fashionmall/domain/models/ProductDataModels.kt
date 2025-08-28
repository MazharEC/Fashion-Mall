package com.appsv.fashionmall.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class ProductDataModels(

    val name : String = "",
    val image : String = "",
    val price : String = "",
    val finalPrice : String = "",
    val availableUnits : Int = 0,
    var productId : String = "",
    val size : String = "",
    val description : String = "",
    val category : String = "",
    val date : Long = System.currentTimeMillis(),
    val createBy : String = "",


)
