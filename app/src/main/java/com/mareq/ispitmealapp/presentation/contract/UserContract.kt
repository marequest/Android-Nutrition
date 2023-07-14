package com.mareq.ispitmealapp.presentation.contract

import androidx.lifecycle.LiveData
import com.mareq.ispitmealapp.data.model.database.UserEntity

interface UserContract {
    interface UserViewModel {

        val fetchedUser : LiveData<UserEntity>

        fun insert(userEntity: UserEntity)
        fun insertAll(userEntities : List<UserEntity>)
        fun getAll()
        fun getById(id : String, pass : String)
        fun deleteAll()
    }
}