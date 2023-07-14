package com.mareq.ispitmealapp.data.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    var username : String,
    var password : String,
    var email : String,
    var firstName : String,
    var lastName : String,
    var profilePicture : String,
){
}