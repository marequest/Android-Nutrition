package com.mareq.ispitmealapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mareq.ispitmealapp.data.model.entities.Area
import com.mareq.ispitmealapp.data.repositories.AreaRepository
import com.mareq.ispitmealapp.presentation.contract.AreaContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AreaViewModel(private val areaRepository: AreaRepository) : ViewModel(), AreaContract.AreaViewModel{
    override val areas: MutableLiveData<List<Area>> = MutableLiveData()

    private val subscriptions = CompositeDisposable()

    override fun getAreas() {
        val subscription =
            areaRepository.getAreas().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    areas.value = it
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

    override fun onCleared(){
        subscriptions.dispose()
        super.onCleared()
    }
}