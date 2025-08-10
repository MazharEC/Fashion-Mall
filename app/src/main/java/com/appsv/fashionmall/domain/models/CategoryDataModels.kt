package com.appsv.fashionmall.domain.models

data class CategoryDataModels(

    var name : String = "",
    var categoryImage : String = "",
    var createBy : String = "",
    var date : Long = System.currentTimeMillis()

)
