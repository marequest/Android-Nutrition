package com.mareq.ispitmealapp.data.model.api.responses

import com.mareq.ispitmealapp.data.model.api.helper.AreaJson
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AreaResponse(
    val meals : List<AreaJson>
)
