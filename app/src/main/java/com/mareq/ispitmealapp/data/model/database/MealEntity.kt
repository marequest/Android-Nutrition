package com.mareq.ispitmealapp.data.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class MealEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val userId : String,
    val mealTitle : String,
    val tags: String?,
    val ytLink : String,
    var mealThumbnail : String?,
    val category : String,
    var mealType : String,
    var day : String,
    var date : Long
){

}
