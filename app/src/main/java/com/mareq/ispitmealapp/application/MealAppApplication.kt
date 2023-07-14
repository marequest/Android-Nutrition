package com.mareq.ispitmealapp.application

import android.app.Application
import com.mareq.ispitmealapp.modules.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MealAppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        initKoin()
    }

    private fun initKoin() {
        val modules = listOf(
            coreModule,
            categoryModule,
            mealModule,
            areaModule,
            ingredientModule,
            user_module
        )

        startKoin {
            androidLogger(Level.ERROR)

            androidContext(this@MealAppApplication)

            androidFileProperties()

            fragmentFactory()

            modules(modules)
        }
    }
}