package com.mareq.ispitmealapp.data.repositories

import com.mareq.ispitmealapp.data.model.database.UserEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface UserRepository {

    fun insert(userEntity: UserEntity) : Completable
    fun insertAll(userEntities : List<UserEntity>) : Single<List<Long>>
    fun getAll() : Observable<List<UserEntity>>
    fun deleteAll() : Completable
    fun getById(id: String, pass: String) : Observable<UserEntity>
}