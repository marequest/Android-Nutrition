package com.mareq.ispitmealapp.data.model.datasources.daos

import androidx.room.*
import com.mareq.ispitmealapp.data.model.database.UserEntity
import com.mareq.ispitmealapp.data.model.database.UserWithMeals
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
abstract class UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUser(userEntity: UserEntity) : Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUsers(userEntities : List<UserEntity>) : Single<List<Long>>

    @Query("SELECT * FROM users")
    abstract fun getAll() : Observable<List<UserEntity>>

    @Query("SELECT * FROM users WHERE username LIKE :username")
    abstract fun getByUsername(username : String) : UserEntity

    @Query("SELECT * FROM users WHERE username LIKE :id AND password LIKE :pass")
    abstract fun getById(id : String, pass : String) : Observable<UserEntity>

    @Update
    abstract fun update(userEntity: UserEntity)

    @Delete
    abstract fun delete(userEntity: UserEntity)

    @Query("DELETE FROM users WHERE username LIKE :username")
    abstract fun deleteByUsername(username : String)

    @Query("DELETE FROM users")
    abstract fun deleteAll() : Completable

    @Transaction
    @Query("SELECT * FROM users WHERE username LIKE :username")
    abstract fun getAllWithMeals(username : String) : Observable<UserWithMeals>
}