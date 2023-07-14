package com.mareq.ispitmealapp.data.model.datasources

import com.mareq.ispitmealapp.data.model.api.responses.AreaResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface AreaDataSource {
    @GET("list.php?a=list")
    fun getAreas() : Observable<AreaResponse>
}