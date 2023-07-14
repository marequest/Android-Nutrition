package com.mareq.ispitmealapp.data.repositories.implementation

import com.mareq.ispitmealapp.data.model.datasources.CategoryDataSource
import com.mareq.ispitmealapp.data.repositories.CategoryRepository
import com.mareq.ispitmealapp.data.model.entities.Category
import io.reactivex.Observable

class CategoryRepositoryImpl(private val categoryDataSource: CategoryDataSource) :
    CategoryRepository {
    override fun getCategories(): Observable<List<Category>> {
        return categoryDataSource.getCategories()
            .map {categoryListResponse ->
                categoryListResponse.categories.map { categoryTwo ->
                    Category (
                        id = categoryTwo.idCategory.toLong(),
                        categoryName = categoryTwo.strCategory,
                        categoryDescription = categoryTwo.strCategoryDescription,
                        categoryThumbnail = categoryTwo.strCategoryThumb
                    )
                }
            }
    }
}