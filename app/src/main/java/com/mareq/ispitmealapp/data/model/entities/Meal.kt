package com.mareq.ispitmealapp.data.model.entities

class Meal(
    val id: Long,
    val mealTitle: String,
    val tags: String?,
    val ytLink: String,
    val mealThumbnail: String?,
    val instructions: List<String>,
    val category: String,
    val ingredients: List<Ingredient>
    ) {

}