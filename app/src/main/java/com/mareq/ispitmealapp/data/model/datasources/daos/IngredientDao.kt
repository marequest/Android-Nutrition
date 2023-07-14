package com.mareq.ispitmealapp.data.model.datasources.daos

import androidx.room.*
import com.mareq.ispitmealapp.data.model.database.IngredientEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
abstract class IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertIngredient(ingredientEntity: IngredientEntity) : Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertIngredients(ingredientEntities : List<IngredientEntity>) : Single<List<Long>>

    @Query("SELECT * FROM ingredients")
    abstract fun getAll() : Observable<List<IngredientEntity>>

    @Query("SELECT * FROM ingredients WHERE mealId == :mealId")
    abstract fun getByMealId(mealId : Long) : Observable<List<IngredientEntity>>

    @Query("SELECT * FROM ingredients WHERE ingredientName LIKE :iName")
    abstract fun getByName(iName : String) : Observable<List<IngredientEntity>>

    @Query("SELECT * FROM ingredients WHERE id == :id")
    abstract fun getById(id : Long) : Observable<List<IngredientEntity>>

    @Update
    abstract fun update(ingredientEntity: IngredientEntity)

    @Delete
    abstract fun delete(ingredientEntity: IngredientEntity)

    @Query("DELETE FROM ingredients WHERE id == :id")
    abstract fun deleteById(id : Long)

    @Query("DELETE FROM ingredients")
    abstract fun deleteAll() : Completable


}