package com.mareq.ispitmealapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mareq.ispitmealapp.data.model.database.MealEntity
import com.mareq.ispitmealapp.data.model.database.MealForMenu
import com.mareq.ispitmealapp.data.model.entities.Meal
import com.mareq.ispitmealapp.data.model.entities.MealShort
import com.mareq.ispitmealapp.data.repositories.MealRepository
import com.mareq.ispitmealapp.presentation.contract.MealContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MealViewModel(private val mealRepository: MealRepository) : ViewModel(),
    MealContract.MealViewModel {
    private val subscriptions = CompositeDisposable()
    override val mealsShort: MutableLiveData<List<MealShort>> = MutableLiveData()
    override val meal: MutableLiveData<List<Meal>> = MutableLiveData()
    override val fetchDbMeal : MutableLiveData<MealEntity> = MutableLiveData()
    override val mealId : MutableLiveData<Long> = MutableLiveData()
    override val savedMeals: MutableLiveData<List<MealShort>> = MutableLiveData()
    override val mealForMenu : MutableLiveData<MealForMenu> = MutableLiveData()
    override val mealsForMenu: MutableLiveData<List<MealForMenu>> = MutableLiveData()


    override fun getMealById(id: Long) {
        val subscription =
            mealRepository
                .getMealById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        meal.value = it
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

    override fun getMealsByCategory(c: String) {
        val subscription =
            mealRepository
                .getMealsByCategory(c)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        mealsShort.value = it
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

    override fun getMealsByIngredient(ingredient: String) {
        val subscription =
            mealRepository
                .getMealsByIngredient(ingredient)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        mealsShort.value = it
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

    override fun getMealsByName(name: String) {
        val subscription =
            mealRepository
                .getMealsByName(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        val temp = mealsShort.value?.toMutableList() ?: mutableListOf()
                        temp.addAll(it)

                        mealsShort.value = temp
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

    override fun getMealsByArea(area: String) {
        val subscription =
            mealRepository
                .getMealsByArea(area)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        mealsShort.value = it
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

    override fun insert(mealEntity: MealEntity) {
        val subscription =
            mealRepository
                .insert(mealEntity)
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
        val subscription = mealRepository
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

    override fun getById(id: Long) {
        val subscription = mealRepository
            .getById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    println("COMPLETED")
                    fetchDbMeal.value = it
                },
                {
                    println(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun deleteAll() {
        val subscription = mealRepository
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

    override fun deleteMealById(id: Long) {
        val subscription = mealRepository
            .deleteMealById(id)
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

    override fun getMealsForUser(user: String) {
        val subscription = mealRepository
            .getMealsForUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val res = convertToShortMeals(it)
                    savedMeals.value = res
                    println("COMPLETED")
                },
                {
                    println(it)
                }
            )
        subscriptions.add(subscription)
    }

    private fun convertToShortMeals(fetch : List<MealForMenu>) : List<MealShort> {
        val mealShort = mutableListOf<MealShort>()

        for(meal in fetch){
            val m = meal.meal
            val food = MealShort(
                id = m.id,
                mealTitle = m.mealTitle,
                mealThumbnail = m.mealThumbnail ?: ""
            )
            mealShort.add(food)
        }

        return mealShort
    }

    override fun getMealByTitle(title: String) {
        val subscription = mealRepository
            .getByTitle(title)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    println("COMPLETED")
                    fetchDbMeal.value = it
                },
                {
                    println(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun update(meal: MealEntity) {
        val subscription = mealRepository
            .update(meal)
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

    override fun getMealWithIngredients(mID: Long) {
        val subscription = mealRepository
            .getMealWithIngredients(mID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    println("COMPLETED")
                    mealForMenu.value = it
                },
                {
                    println(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getFullMealsForUser(userID: String) {
        val subscription = mealRepository
            .getFullMealsForUser(userID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    println("COMPLETED")
                    mealsForMenu.value = it
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