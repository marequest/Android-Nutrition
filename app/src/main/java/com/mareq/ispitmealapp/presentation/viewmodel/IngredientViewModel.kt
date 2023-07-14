package com.mareq.ispitmealapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mareq.ispitmealapp.data.model.database.IngredientEntity
import com.mareq.ispitmealapp.data.model.entities.Ingredient
import com.mareq.ispitmealapp.data.repositories.IngredientRepository
import com.mareq.ispitmealapp.presentation.contract.IngredientContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class IngredientViewModel(private val ingredientRepository: IngredientRepository) : ViewModel(), IngredientContract.IngredientViewModel{

    private val subscriptions = CompositeDisposable()
    override val ingredients: MutableLiveData<List<Ingredient>> = MutableLiveData()

    override fun getIngredients() {
        val subscription =
            ingredientRepository.getIngredients().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        ingredients.value = it
                    },
                    {
                        println(it)
                    },
                    {
                        println("COMPLETED")
                    }
                )
        subscriptions.add(subscription)
    }

    override fun insert(ingredientEntity: IngredientEntity) {
        val subscription =
            ingredientRepository.insert(ingredientEntity).subscribeOn(Schedulers.io())
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

    override fun insertAll(ingredientEntities: List<IngredientEntity>) {
        val subscription =
            ingredientRepository.insertAll(ingredientEntities).subscribeOn(Schedulers.io())
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
        val subscription =
            ingredientRepository.getAll().subscribeOn(Schedulers.io())
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

    override fun getById(id: Long) {
        val subscription =
            ingredientRepository.getById(id).subscribeOn(Schedulers.io())
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

    override fun getByName(name: String) {
        val subscription =
            ingredientRepository.getByName(name).subscribeOn(Schedulers.io())
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

    override fun deleteAll() {
        val subscription =
            ingredientRepository.deleteAll().subscribeOn(Schedulers.io())
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


    override fun onCleared() {
        subscriptions.dispose()
        super.onCleared()
    }
}