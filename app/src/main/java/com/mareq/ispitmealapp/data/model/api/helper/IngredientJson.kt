package com.mareq.ispitmealapp.data.model.api.helper

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IngredientJson(
    val idIngredient : String?,
    val strIngredient: String?,
    val strDescription : String?,
    val strType : String?
)
