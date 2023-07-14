package com.mareq.ispitmealapp.modules

import com.mareq.ispitmealapp.data.model.datasources.AreaDataSource
import com.mareq.ispitmealapp.data.repositories.AreaRepository
import com.mareq.ispitmealapp.data.repositories.implementation.AreaRepositoryImpl
import com.mareq.ispitmealapp.presentation.viewmodel.AreaViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val areaModule = module {
    viewModel {AreaViewModel(areaRepository = get())}

    single<AreaRepository> {AreaRepositoryImpl(areaDataSource = get())}

    single<AreaDataSource> {create(get())}
}