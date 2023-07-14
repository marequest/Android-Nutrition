package com.mareq.ispitmealapp.data.repositories.implementation

import com.mareq.ispitmealapp.data.model.datasources.daos.UserDao
import com.mareq.ispitmealapp.data.model.database.UserEntity
import com.mareq.ispitmealapp.data.repositories.UserRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    override fun insert(userEntity: UserEntity): Completable {
        return userDao.insertUser(userEntity)
    }

    override fun insertAll(userEntities: List<UserEntity>): Single<List<Long>> {
        return userDao.insertUsers(userEntities)
    }

    override fun getAll(): Observable<List<UserEntity>> {
        return userDao.getAll()
    }

    override fun deleteAll(): Completable {
        return userDao.deleteAll()
    }

    override fun getById(id: String, pass : String): Observable<UserEntity> {
        return userDao.getById(id, pass);
    }
}