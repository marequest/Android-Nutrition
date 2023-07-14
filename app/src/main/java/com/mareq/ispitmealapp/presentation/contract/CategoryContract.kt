package com.mareq.ispitmealapp.presentation.contract

import androidx.lifecycle.LiveData
import com.mareq.ispitmealapp.data.model.entities.Category

interface CategoryContract {

    interface CategoryViewModel {
        val categories: LiveData<List<Category>>
        val category: LiveData<Category>

        fun getCategories()
    }
}