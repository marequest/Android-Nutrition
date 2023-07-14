package com.mareq.ispitmealapp.presentation.contract

import androidx.lifecycle.LiveData
import com.mareq.ispitmealapp.data.model.entities.Area

interface AreaContract {
    interface AreaViewModel {
        val areas : LiveData<List<Area>>

        fun getAreas()
    }
}