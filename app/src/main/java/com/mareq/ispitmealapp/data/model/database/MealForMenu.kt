package com.mareq.ispitmealapp.data.model.database

import androidx.room.Embedded
import androidx.room.Relation

data class MealForMenu(
    @Embedded val meal : MealEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "mealId"
    )
    val  ingredients : List<IngredientEntity>
)
