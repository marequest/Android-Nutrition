package com.mareq.ispitmealapp.modules

import com.mareq.ispitmealapp.data.model.datasources.CategoryDataSource
import com.mareq.ispitmealapp.data.repositories.CategoryRepository
import com.mareq.ispitmealapp.data.repositories.implementation.CategoryRepositoryImpl
import com.mareq.ispitmealapp.presentation.viewmodel.CategoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoryModule = module {
    viewModel { CategoryViewModel(categoryRepository = get()) }

    single<CategoryRepository> { CategoryRepositoryImpl(categoryDataSource = get()) }

    single<CategoryDataSource> { create(get()) }
}