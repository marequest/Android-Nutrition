package com.mareq.ispitmealapp.data.model.api.helper

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryJson(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)