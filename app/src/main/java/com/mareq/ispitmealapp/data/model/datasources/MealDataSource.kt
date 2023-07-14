package com.mareq.ispitmealapp.data.model.datasources

import com.mareq.ispitmealapp.data.model.api.responses.FoodByCategoryResponse
import com.mareq.ispitmealapp.data.model.api.responses.MealResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MealDataSource {
    @GET("filter.php")
    fun getMealsByCategory(@Query("c") c : String) : Observable<FoodByCategoryResponse>

    @GET("lookup.php")
    fun getMealById(@Query("i") id : Long) : Observable<MealResponse>

    @GET("filter.php")
    fun getMealsByIngredient(@Query("i") ingredient : String) : Observable<FoodByCategoryResponse>

    @GET("search.php")
    fun getMealsByName(@Query("s") mealName : String) : Observable<MealResponse>

    @GET("filter.php")
    fun getMealsByArea(@Query("a") area : String) : Observable<FoodByCategoryResponse>
}