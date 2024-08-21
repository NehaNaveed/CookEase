package com.example.cookease

import com.example.cookease.model.CategoriesDTO
import com.example.cookease.model.CategoryToMealDTO
import com.example.cookease.model.MealDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsInterface {
    @GET("categories.php")
    suspend fun getCategories() :Response<CategoriesDTO>
    @GET("filter.php")
    suspend fun getMeal(@Query("c") catStr: String): Response<CategoryToMealDTO>

    @GET("lookup.php")
    suspend fun getRecipe(@Query("i") mealId: String): Response<MealDTO>


}