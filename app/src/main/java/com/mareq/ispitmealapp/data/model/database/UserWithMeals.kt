package com.mareq.ispitmealapp.data.model.database

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithMeals(
    @Embedded val user : UserEntity,
    @Relation(
        parentColumn = "username",
        entityColumn = "userId",
        entity = MealEntity::class
    )

    val meals : List<MealForMenu>
)
