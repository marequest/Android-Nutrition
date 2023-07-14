package com.mareq.ispitmealapp.data.repositories

import com.mareq.ispitmealapp.data.model.entities.Category
import io.reactivex.Observable

interface CategoryRepository {

    fun getCategories() : Observable<List<Category>>
}