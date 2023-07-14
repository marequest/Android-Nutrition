package com.mareq.ispitmealapp.data.model.api.responses

import com.mareq.ispitmealapp.data.model.api.helper.CategoryJson
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryResponse(
    val categories : List<CategoryJson>
)