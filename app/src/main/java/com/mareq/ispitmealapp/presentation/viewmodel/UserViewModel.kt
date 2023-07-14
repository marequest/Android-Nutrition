package com.mareq.ispitmealapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mareq.ispitmealapp.data.model.database.UserEntity
import com.mareq.ispitmealapp.data.repositories.UserRepository
import com.mareq.ispitmealapp.presentation.contract.UserContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UserViewModel(private val userRepository: UserRepository) : ViewModel(), UserContract.UserViewModel{

    private val subscriptions = CompositeDisposable()
    override val fetchedUser: MutableLiveData<UserEntity> = MutableLiveData<UserEntity>()

    override fun insert(userEntity: UserEntity) {
        val subscription = userRepository
            .insert(userEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    println("COMPLETED")
                },
                {
                    println(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun insertAll(userEntities: List<UserEntity>) {
        val subscription = userRepository
            .insertAll(userEntities)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    println("COMPLETED")
                },
                {
                    println(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAll() {
        val subscription = userRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    println("COMPLETED")
                },
                {
                    println(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getById(id: String, pass : String) {
        val subscription = userRepository
            .getById(id, pass)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    println("COMPLETED")
                    fetchedUser.value = it
                },
                {
                    println(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun deleteAll() {
        val subscription = userRepository
            .deleteAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    println("COMPLETED")
                },
                {
                    println(it)
                }
            )
        subscriptions.add(subscription)
    }

}