package com.mareq.ispitmealapp.presentation.contract

import androidx.lifecycle.LiveData
import com.mareq.ispitmealapp.data.model.database.IngredientEntity
import com.mareq.ispitmealapp.data.model.entities.Ingredient

interface IngredientContract {
    interface IngredientViewModel {
        val ingredients : LiveData<List<Ingredient>>

        fun getIngredients()
        fun insert(ingredientEntity: IngredientEntity)
        fun insertAll(ingredientEntities : List<IngredientEntity>)
        fun getAll()
        fun getById(id : Long)
        fun getByName(name : String)
        fun deleteAll()
    }
}