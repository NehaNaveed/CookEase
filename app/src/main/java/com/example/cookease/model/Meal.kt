package com.example.cookease.model

data class MealDTO(
    val meals: List<Meal?>?
) {
    data class Meal(
        val dateModified: String?,
        val idMeal: String?,
        val strArea: String?,
        val strCategory: String?,
        val strCreativeCommonsConfirmed: String?,
        val strDrinkAlternate: String?,
        val strImageSource: String?,
        val strIngredient1: String?,
        val strIngredient10: String?,
        val strIngredient11: String?,
        val strIngredient12: String?,
        val strIngredient13: String?,
        val strIngredient14: String?,
        val strIngredient15: String?,
        val strIngredient16: String?,
        val strIngredient17: String?,
        val strIngredient18: String?,
        val strIngredient19: String?,
        val strIngredient2: String?,
        val strIngredient20: String?,
        val strIngredient3: String?,
        val strIngredient4: String?,
        val strIngredient5: String?,
        val strIngredient6: String?,
        val strIngredient7: String?,
        val strIngredient8: String?,
        val strIngredient9: String?,
        val strInstructions: String?,
        val strMeal: String?,
        val strMealThumb: String?,
        val strMeasure1: String?,
        val strMeasure10: String?,
        val strMeasure11: String?,
        val strMeasure12: String?,
        val strMeasure13: String?,
        val strMeasure14: String?,
        val strMeasure15: String?,
        val strMeasure16: String?,
        val strMeasure17: String?,
        val strMeasure18: String?,
        val strMeasure19: String?,
        val strMeasure2: String?,
        val strMeasure20: String?,
        val strMeasure3: String?,
        val strMeasure4: String?,
        val strMeasure5: String?,
        val strMeasure6: String?,
        val strMeasure7: String?,
        val strMeasure8: String?,
        val strMeasure9: String?,
        val strSource: String?,
        val strTags: String?,
        val strYoutube: String?
    )
}

data class Meals(
    val meals: List<Meal>
) {
    data class Meal(
        val idMeal: String,
        val strMeal: String,
        val strMealThumb: String,
        val strCategory: String,
        val ingList: List<String> = listOf(""),
        val measureList : List<String> = listOf(""),
        val strInstructions: String,
        val strYoutube: String
    )
}

fun MealDTO?.toRecipe(): Meals {
    return Meals(
        meals =
        this?.meals?.map { dto ->
            dto.let {
                Meals.Meal(
                    idMeal = it?.idMeal.orEmpty(),
                    strMeal = it?.strMeal.orEmpty(),
                    strMealThumb = it?.strMealThumb.orEmpty(),
                    strCategory = it?.strCategory.orEmpty(),
                    ingList = listOf(
                        it?.strIngredient1.orEmpty(),
                        it?.strIngredient2.orEmpty(),
                        it?.strIngredient3.orEmpty(),
                        it?.strIngredient4.orEmpty(),
                        it?.strIngredient5.orEmpty(),
                        it?.strIngredient6.orEmpty(),
                        it?.strIngredient7.orEmpty(),
                        it?.strIngredient8.orEmpty(),
                        it?.strIngredient9.orEmpty(),
                        it?.strIngredient10.orEmpty(),
                        it?.strIngredient11.orEmpty(),
                        it?.strIngredient12.orEmpty(),
                        it?.strIngredient13.orEmpty(),
                        it?.strIngredient14.orEmpty(),
                        it?.strIngredient15.orEmpty(),
                        it?.strIngredient16.orEmpty(),
                        it?.strIngredient17.orEmpty(),
                        it?.strIngredient18.orEmpty(),
                        it?.strIngredient19.orEmpty(),
                        it?.strIngredient20.orEmpty(),
                    ),
                    measureList = listOf(
                        it?.strMeasure1.orEmpty(),
                        it?.strMeasure2.orEmpty(),
                        it?.strMeasure3.orEmpty(),
                        it?.strMeasure4.orEmpty(),
                        it?.strMeasure5.orEmpty(),
                        it?.strMeasure6.orEmpty(),
                        it?.strMeasure7.orEmpty(),
                        it?.strMeasure8.orEmpty(),
                        it?.strMeasure9.orEmpty(),
                        it?.strMeasure10.orEmpty(),
                        it?.strMeasure11.orEmpty(),
                        it?.strMeasure12.orEmpty(),
                        it?.strMeasure13.orEmpty(),
                        it?.strMeasure14.orEmpty(),
                        it?.strMeasure15.orEmpty(),
                        it?.strMeasure16.orEmpty(),
                        it?.strMeasure17.orEmpty(),
                        it?.strMeasure18.orEmpty(),
                        it?.strMeasure19.orEmpty(),
                        it?.strMeasure20.orEmpty(),
                    ),
                    strInstructions = it?.strInstructions.orEmpty(),
                    strYoutube = it?.strYoutube.orEmpty()
                )
            }
        } ?: emptyList())
}