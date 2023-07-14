package com.mareq.ispitmealapp.data.model.datasources

import com.mareq.ispitmealapp.data.model.api.responses.IngredientResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface IngredientDataSource {
    @GET("list.php?i=list")
    fun getIngredients() : Observable<IngredientResponse>
}