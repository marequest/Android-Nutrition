package com.mareq.ispitmealapp.data.repositories

import com.mareq.ispitmealapp.data.model.database.MealEntity
import com.mareq.ispitmealapp.data.model.database.MealForMenu
import com.mareq.ispitmealapp.data.model.entities.Meal
import com.mareq.ispitmealapp.data.model.entities.MealShort
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.Query

interface MealRepository {
    fun getMealsByCategory(c : String) : Observable<List<MealShort>>
    fun getMealById(@Query("i") id : Long) : Observable<List<Meal>>
    fun getMealsByIngredient(@Query("i") ingredient : String) : Observable<List<MealShort>>
    fun getMealsByName(@Query("s") name : String) : Observable<List<MealShort>>
    fun getMealsByArea(@Query("a") area : String) : Observable<List<MealShort>>

    fun insert(mealEntity: MealEntity) : Completable
    fun getAll() : Observable<List<MealEntity>>
    fun getById(id : Long) : Observable<MealEntity>
    fun deleteAll() : Completable
    fun deleteMealById(id : Long) : Completable
    fun getMealsForUser(user : String) : Observable<List<MealForMenu>>
    fun getByTitle(mTitle : String) : Observable<MealEntity>
    fun update(meal : MealEntity) : Completable
    fun getMealWithIngredients(mID : Long) : Observable<MealForMenu>
    fun getFullMealsForUser(userID : String) : Observable<List<MealForMenu>>
}