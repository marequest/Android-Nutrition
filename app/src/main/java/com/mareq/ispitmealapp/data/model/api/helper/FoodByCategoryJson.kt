package com.mareq.ispitmealapp.data.model.api.helper

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodByCategoryJson(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)
