package com.mareq.ispitmealapp.modules

import com.mareq.ispitmealapp.data.database.MealDatabase
import com.mareq.ispitmealapp.data.repositories.UserRepository
import com.mareq.ispitmealapp.data.repositories.implementation.UserRepositoryImpl
import com.mareq.ispitmealapp.presentation.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val user_module = module {
    viewModel { UserViewModel(userRepository = get()) }

    single<UserRepository> {UserRepositoryImpl(get())}

    single {get<MealDatabase>().getUserDao()}
}