package com.mareq.ispitmealapp.data.repositories.implementation

import com.mareq.ispitmealapp.data.model.datasources.MealDataSource
import com.mareq.ispitmealapp.data.model.datasources.daos.MealDao
import com.mareq.ispitmealapp.data.model.database.MealEntity
import com.mareq.ispitmealapp.data.model.database.MealForMenu
import com.mareq.ispitmealapp.data.model.entities.Ingredient
import com.mareq.ispitmealapp.data.model.entities.Meal
import com.mareq.ispitmealapp.data.model.entities.MealShort
import com.mareq.ispitmealapp.data.repositories.MealRepository
import io.reactivex.Completable
import io.reactivex.Observable

class MealRepositoryImpl(private val mealDao : MealDao, private val mealDataSource: MealDataSource) : MealRepository {

    override fun getMealsByCategory(c: String): Observable<List<MealShort>> {
        return mealDataSource.getMealsByCategory(c).map { response ->
            response.meals.map { meal ->
                MealShort(
                    id = meal.idMeal.toLong(),
                    mealTitle = meal.strMeal.takeUnless { it.isBlank() } ?: "Not Available",
                    mealThumbnail = meal.strMealThumb.takeUnless { it.isBlank() } ?: "Not Available"
                )
            }
        }
    }

    override fun getMealById(id: Long): Observable<List<Meal>> {
        return mealDataSource.getMealById(id).map {response ->
            response.meals.map { meal ->
                val ingredients = listOf(
                    Ingredient(meal.strIngredient1 ?: "", meal.strMeasure1 ?: ""),
                    Ingredient(meal.strIngredient2 ?: "", meal.strMeasure2 ?: ""),
                    Ingredient(meal.strIngredient3 ?: "", meal.strMeasure3 ?: ""),
                    Ingredient(meal.strIngredient4 ?: "", meal.strMeasure4 ?: ""),
                    Ingredient(meal.strIngredient5 ?: "", meal.strMeasure5 ?: ""),
                    Ingredient(meal.strIngredient6 ?: "", meal.strMeasure6 ?: ""),
                    Ingredient(meal.strIngredient7 ?: "", meal.strMeasure7 ?: ""),
                    Ingredient(meal.strIngredient8 ?: "", meal.strMeasure8 ?: ""),
                    Ingredient(meal.strIngredient9 ?: "", meal.strMeasure9 ?: ""),
                    Ingredient(meal.strIngredient10 ?: "", meal.strMeasure10 ?: ""),
                    Ingredient(meal.strIngredient11 ?: "", meal.strMeasure11 ?: ""),
                    Ingredient(meal.strIngredient12 ?: "", meal.strMeasure12 ?: ""),
                    Ingredient(meal.strIngredient13 ?: "", meal.strMeasure13 ?: ""),
                    Ingredient(meal.strIngredient14 ?: "", meal.strMeasure14 ?: ""),
                    Ingredient(meal.strIngredient15 ?: "", meal.strMeasure15 ?: ""),
                    Ingredient(meal.strIngredient16 ?: "", meal.strMeasure16 ?: ""),
                    Ingredient(meal.strIngredient17 ?: "", meal.strMeasure17 ?: ""),
                    Ingredient(meal.strIngredient18 ?: "", meal.strMeasure18 ?: ""),
                    Ingredient(meal.strIngredient19 ?: "", meal.strMeasure19 ?: ""),
                    Ingredient(meal.strIngredient20 ?: "", meal.strMeasure20 ?: ""),
                )

                val steps = meal.strInstructions.split("\r\n").map {it.trim()}.toList()

                Meal(
                    id = meal.idMeal.toLong(),
                    mealTitle = meal.strMeal.takeUnless { it.isBlank() } ?: "Not Available",
                    mealThumbnail = meal.strMealThumb.takeUnless { it.isBlank() } ?: "Not Available",
                    tags = meal.strTags.takeUnless { it.isNullOrBlank() } ?: "Not Available",
                    ytLink = meal.strYoutube.takeUnless { it.isBlank() } ?: "Not Available",
                    ingredients = ingredients,
                    instructions = steps,
                    category = meal.strCategory.takeUnless { it.isBlank() } ?: "Not Available"
                )
            }
        }
    }

    override fun getMealsByIngredient(ingredient: String): Observable<List<MealShort>> {
        return mealDataSource.getMealsByIngredient(ingredient).map {response ->
            response.meals.map { meal ->
                MealShort(
                    id = meal.idMeal.toLong(),
                    mealTitle = meal.strMeal.takeUnless { it.isBlank() } ?: "Not Available",
                    mealThumbnail = meal.strMealThumb.takeUnless { it.isBlank() } ?: "Not Available"
                )
            }
        }
    }

    override fun getMealsByName(name: String): Observable<List<MealShort>> {
        return mealDataSource.getMealsByName(name).map { response ->
            response.meals.map { meal ->
                MealShort(
                    id = meal.idMeal.toLong(),
                    mealTitle = meal.strMeal.takeUnless { it.isBlank() } ?: "Not Available",
                    mealThumbnail = meal.strMealThumb.takeUnless { it.isBlank() } ?: "Not Available"
                )
            }
        }
    }

    override fun getMealsByArea(area: String): Observable<List<MealShort>> {
        return mealDataSource.getMealsByArea(area).map {response ->
            response.meals.map {meal ->
                MealShort(
                    id = meal.idMeal.toLong(),
                    mealTitle = meal.strMeal.takeUnless { it.isBlank() } ?: "Not Available",
                    mealThumbnail = meal.strMealThumb.takeUnless { it.isBlank() } ?: "Not Available"
                )
            }
        }
    }

    override fun insert(mealEntity: MealEntity): Completable {
       return mealDao.insertMeal(mealEntity)
    }

    override fun getAll(): Observable<List<MealEntity>> {
        return mealDao.getAll()
    }

    override fun getById(id: Long): Observable<MealEntity> {
        return mealDao.getById(id)
    }

    override fun deleteAll(): Completable {
        return mealDao.deleteAllMeals()
    }

    override fun deleteMealById(id: Long) : Completable {
        return mealDao.deleteMealById(id)
    }

    override fun getMealsForUser(user: String): Observable<List<MealForMenu>> {
        return mealDao.getMealsForUser(user)
    }

    override fun getByTitle(mTitle: String): Observable<MealEntity> {
        return mealDao.getByTitle(mTitle)
    }

    override fun update(meal: MealEntity): Completable {
        return mealDao.update(meal)
    }

    override fun getMealWithIngredients(mID: Long): Observable<MealForMenu> {
        return mealDao.getMealWithIngredients(mID)
    }

    override fun getFullMealsForUser(userID: String): Observable<List<MealForMenu>> {
        return mealDao.getFullMealsForUser(userID)
    }
}