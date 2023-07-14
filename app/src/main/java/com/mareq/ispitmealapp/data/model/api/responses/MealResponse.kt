package com.mareq.ispitmealapp.data.model.api.responses

import com.mareq.ispitmealapp.data.model.api.helper.MealJson
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MealResponse(
    val meals : List<MealJson>
)
