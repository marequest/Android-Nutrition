package com.mareq.ispitmealapp.data.repositories

import com.mareq.ispitmealapp.data.model.database.IngredientEntity
import com.mareq.ispitmealapp.data.model.entities.Ingredient
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IngredientRepository {

    fun getIngredients() : Observable<List<Ingredient>>

    fun insert(ingredientEntity: IngredientEntity) : Completable
    fun insertAll(ingredientEntities : List<IngredientEntity>) : Single<List<Long>>
    fun getAll() : Observable<List<IngredientEntity>>
    fun getById(id : Long) :  Observable<List<IngredientEntity>>
    fun getByName(name : String) : Observable<List<IngredientEntity>>
    fun deleteAll() : Completable
}