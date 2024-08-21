package com.example.cookease.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cookease.RetrofitInstance
import com.example.cookease.model.Meals
import com.example.cookease.model.toRecipe


class RecipeViewModel () : ViewModel() {
    private val _recipeList = MutableLiveData<List<Meals.Meal>>()
    val recipeList: LiveData<List<Meals.Meal>> = _recipeList

    private val _inProgress = MutableLiveData<Boolean>()
    val inProgress: LiveData<Boolean> = _inProgress

    suspend fun getRecipes(mealId: String) {
        val response = RetrofitInstance.mealsInterface.getRecipe(mealId = mealId).body().toRecipe()
        _inProgress.value = true
        if (response.meals.isNotEmpty()) {

            _recipeList.value = response.meals
            _inProgress.value = false

        } else {
            Log.d("Recipe View Model", response.toString())
        }
    }
}