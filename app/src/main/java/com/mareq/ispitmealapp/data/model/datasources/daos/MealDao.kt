package com.mareq.ispitmealapp.data.model.datasources.daos

import androidx.room.*
import com.mareq.ispitmealapp.data.model.database.MealEntity
import com.mareq.ispitmealapp.data.model.database.MealForMenu
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
abstract class MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMeal(mealEntity : MealEntity) : Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMeals(mealEntities : List<MealEntity>) : Single<List<Long>>

    @Query("SELECT * FROM meals")
    abstract fun getAll() : Observable<List<MealEntity>>

    @Query("SELECT * FROM meals WHERE id == :id")
    abstract fun getById(id : Long) : Observable<MealEntity>

    @Query("SELECT * FROM meals WHERE mealTitle LIKE :mTitle")
    abstract fun getByTitle(mTitle : String) : Observable<MealEntity>

    @Update
    abstract fun update(mealEntity: MealEntity) : Completable

    @Query("UPDATE meals SET mealThumbnail = :link WHERE id == :id")
    abstract fun updateMealThumbById(id: Long, link : String) : Completable

    @Delete
    abstract fun delete(mealEntity: MealEntity)

    @Query("DELETE FROM meals WHERE id == :id")
    abstract fun deleteMealById(id : Long) : Completable

    @Query("DELETE FROM meals")
    abstract fun deleteAllMeals() : Completable

    @Transaction
    @Query("SELECT * FROM meals")
    abstract fun getAllWithIngredients() : Observable<List<MealForMenu>>

    @Transaction
    @Query("SELECT * FROM meals WHERE id = :mID")
    abstract fun getMealWithIngredients(mID : Long) : Observable<MealForMenu>

    @Transaction
    @Query("SELECT * FROM meals WHERE userId = :userID")
    abstract fun getFullMealsForUser(userID : String) : Observable<List<MealForMenu>>

    @Transaction
    @Query("SELECT * FROM meals WHERE userId LIKE :user")
    abstract fun getMealsForUser(user : String) : Observable<List<MealForMenu>>
}