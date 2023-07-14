package com.mareq.ispitmealapp.data.model.api.responses

import com.mareq.ispitmealapp.data.model.api.helper.IngredientJson
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IngredientResponse(
    val meals : List<IngredientJson>
)
