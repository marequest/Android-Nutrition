package com.mareq.ispitmealapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mareq.ispitmealapp.data.repositories.CategoryRepository
import com.mareq.ispitmealapp.data.model.entities.Category
import com.mareq.ispitmealapp.presentation.contract.CategoryContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CategoryViewModel(private val categoryRepository: CategoryRepository) : ViewModel(),
    CategoryContract.CategoryViewModel {

    override val categories: MutableLiveData<List<Category>> = MutableLiveData()
    override val category: MutableLiveData<Category> = MutableLiveData()

    private val subscriptions = CompositeDisposable()

    override fun getCategories() {
        val subscription =
            categoryRepository.getCategories().subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()
            ).subscribe(
                {
                    categories.value = it
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

    override fun onCleared() {
        subscriptions.dispose()
        super.onCleared()
    }
}