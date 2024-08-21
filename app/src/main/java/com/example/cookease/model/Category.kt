package com.example.cookease.model

data class CategoriesDTO(
    val categories: List<Category?>?
) {
    data class Category(
        val idCategory: String?,
        val strCategory: String?,
        val strCategoryDescription: String?,
        val strCategoryThumb: String?
    )
}


data class Categories(
    val categories: List<Category>
) {
    data class Category(
        val idCategory: String,
        val strCategory: String,
        val strCategoryDescription: String,
        val strCategoryThumb: String
    )
}


fun CategoriesDTO?.toCategory(): Categories {
    return Categories(
        categories =
        this?.categories?.map { dto ->
            dto.let {
                Categories.Category(
                    idCategory = it?.idCategory.orEmpty(),
                    strCategory = it?.strCategory.orEmpty(),
                    strCategoryDescription = it?.strCategoryDescription.orEmpty(),
                    strCategoryThumb = it?.strCategoryThumb.orEmpty()
                )
            }
        } ?: emptyList()

    )
}