package com.mareq.ispitmealapp.data.repositories.implementation

import com.mareq.ispitmealapp.data.model.datasources.IngredientDataSource
import com.mareq.ispitmealapp.data.model.datasources.daos.IngredientDao
import com.mareq.ispitmealapp.data.model.database.IngredientEntity
import com.mareq.ispitmealapp.data.model.entities.Ingredient
import com.mareq.ispitmealapp.data.repositories.IngredientRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class IngredientRepositoryImpl(private val ingredientDao: IngredientDao, private val ingredientDataSource: IngredientDataSource) : IngredientRepository{

    override fun getIngredients(): Observable<List<Ingredient>> {
        return ingredientDataSource.getIngredients().map {ingredientListResponse ->
            ingredientListResponse.meals.map {ingredient ->
                Ingredient(
                    ingredientName = ingredient.strIngredient.takeUnless { it.isNullOrBlank() } ?: "Title Not Available",
                    measure = ""
                )
            }
        }
    }

    override fun insert(ingredientEntity: IngredientEntity): Completable {
        return ingredientDao.insertIngredient(ingredientEntity)
    }

    override fun insertAll(ingredientEntities: List<IngredientEntity>): Single<List<Long>> {
        return ingredientDao.insertIngredients(ingredientEntities)
    }

    override fun getAll(): Observable<List<IngredientEntity>> {
        return ingredientDao.getAll()
    }

    override fun getById(id: Long): Observable<List<IngredientEntity>> {
        return ingredientDao.getById(id)
    }

    override fun getByName(name: String): Observable<List<IngredientEntity>> {
        return ingredientDao.getByName(name)
    }

    override fun deleteAll(): Completable {
        return ingredientDao.deleteAll()
    }

}