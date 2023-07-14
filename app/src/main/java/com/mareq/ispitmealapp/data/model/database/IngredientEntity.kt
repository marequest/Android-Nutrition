package com.mareq.ispitmealapp.data.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val mealId : Long,
    val ingredientName : String,
    val measure : String
)
