package com.example.cookease.model

data class CategoryToMealDTO(
    val meals: List<Meal?>?
) {
    data class Meal(
        val idMeal: String?,
        val strMeal: String?,
        val strMealThumb: String?
    )
}


data class CategoryToMeal(
    val meals: List<Meal>
) {
    data class Meal(
        val idMeal: String,
        val strMeal: String,
        val strMealThumb: String
    )
}


fun CategoryToMealDTO?.toMeals(): CategoryToMeal {
    return CategoryToMeal(
        meals =
        this?.meals?.map { dto ->
            dto.let {
                CategoryToMeal.Meal(
                    idMeal = it?.idMeal.orEmpty(),
                    strMeal = it?.strMeal.orEmpty(),
                    strMealThumb = it?.strMealThumb.orEmpty(),
                )
            }
        } ?: emptyList())
}