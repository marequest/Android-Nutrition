package com.mareq.ispitmealapp.modules

import com.mareq.ispitmealapp.data.database.MealDatabase
import com.mareq.ispitmealapp.data.model.datasources.IngredientDataSource
import com.mareq.ispitmealapp.data.repositories.IngredientRepository
import com.mareq.ispitmealapp.data.repositories.implementation.IngredientRepositoryImpl
import com.mareq.ispitmealapp.presentation.viewmodel.IngredientViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ingredientModule = module {
    viewModel {IngredientViewModel(ingredientRepository = get())}

    single<IngredientRepository> {IngredientRepositoryImpl(ingredientDao = get(), ingredientDataSource = get())}

    single<IngredientDataSource> {create(get())}

    single {get<MealDatabase>().getIngredientDao()}
}