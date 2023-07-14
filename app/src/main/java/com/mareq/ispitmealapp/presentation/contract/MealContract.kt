package com.mareq.ispitmealapp.presentation.contract

import androidx.lifecycle.LiveData
import com.mareq.ispitmealapp.data.model.database.MealEntity
import com.mareq.ispitmealapp.data.model.database.MealForMenu
import com.mareq.ispitmealapp.data.model.entities.Meal
import com.mareq.ispitmealapp.data.model.entities.MealShort

interface MealContract {
    interface MealViewModel {
        val mealsShort : LiveData<List<MealShort>>
        val meal : LiveData<List<Meal>>
        val fetchDbMeal : LiveData<MealEntity>
        val mealId : LiveData<Long>
        val savedMeals : LiveData<List<MealShort>>
        val mealForMenu : LiveData<MealForMenu>
        val mealsForMenu : LiveData<List<MealForMenu>>

        fun getMealById(id : Long)
        fun getMealsByCategory(c : String)
        fun getMealsByIngredient(ingredient : String)
        fun getMealsByName(name : String)
        fun getMealsByArea(area : String)

        fun insert(mealEntity: MealEntity)
        fun getAll()
        fun getById(id : Long)
        fun deleteAll()
        fun deleteMealById(id : Long)
        fun getMealsForUser(user : String)
        fun getMealByTitle(title : String)
        fun update(meal : MealEntity)
        fun getMealWithIngredients(mID : Long)
        fun getFullMealsForUser(userID : String)
    }
}