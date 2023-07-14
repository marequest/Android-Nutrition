package com.mareq.ispitmealapp.data.model.api.responses

import com.mareq.ispitmealapp.data.model.api.helper.FoodByCategoryJson
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodByCategoryResponse(
    val meals : List<FoodByCategoryJson>
)
