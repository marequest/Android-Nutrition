package com.mareq.ispitmealapp.modules

import com.mareq.ispitmealapp.data.database.MealDatabase
import com.mareq.ispitmealapp.data.model.datasources.MealDataSource
import com.mareq.ispitmealapp.data.repositories.MealRepository
import com.mareq.ispitmealapp.data.repositories.implementation.MealRepositoryImpl
import com.mareq.ispitmealapp.presentation.viewmodel.MealViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val mealModule = module {
    viewModel { MealViewModel(mealRepository = get()) }

    single<MealRepository> { MealRepositoryImpl(mealDao = get(),mealDataSource = get()) }

    single<MealDataSource> { create(get()) }

    single {get<MealDatabase>().getMealDao()}
}