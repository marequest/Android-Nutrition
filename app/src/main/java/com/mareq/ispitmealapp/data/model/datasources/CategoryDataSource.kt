package com.mareq.ispitmealapp.data.model.datasources

import com.mareq.ispitmealapp.data.model.api.responses.CategoryResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface CategoryDataSource {
    @GET("categories.php")
    fun getCategories() : Observable<CategoryResponse>
}