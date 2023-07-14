package com.mareq.ispitmealapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mareq.ispitmealapp.data.database.converters.DateConverter
import com.mareq.ispitmealapp.data.model.datasources.daos.IngredientDao
import com.mareq.ispitmealapp.data.model.datasources.daos.MealDao
import com.mareq.ispitmealapp.data.model.datasources.daos.UserDao
import com.mareq.ispitmealapp.data.model.database.IngredientEntity
import com.mareq.ispitmealapp.data.model.database.MealEntity
import com.mareq.ispitmealapp.data.model.database.UserEntity

@Database(
    entities = [UserEntity::class, MealEntity::class, IngredientEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class MealDatabase : RoomDatabase() {
    abstract fun getUserDao() : UserDao
    abstract fun getMealDao() : MealDao
    abstract fun getIngredientDao() : IngredientDao
}