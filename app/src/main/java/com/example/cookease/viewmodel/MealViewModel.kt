package com.example.cookease.viewmodel

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cookease.model.Categories
import com.example.cookease.model.CategoryToMeal
import com.example.cookease.RetrofitInstance
import com.example.cookease.model.toCategory
import com.example.cookease.model.toMeals
import kotlinx.coroutines.launch


class MealViewModel(application: Application) : AndroidViewModel(application) {

    private val _mealList = MutableLiveData<List<CategoryToMeal.Meal>>()
    val mealList: LiveData<List<CategoryToMeal.Meal>> = _mealList

    private val _inProgress = MutableLiveData<Boolean>()
    val inProgress: LiveData<Boolean> = _inProgress


    suspend fun getMeals(catStr: String) {
        val response = RetrofitInstance.mealsInterface.getMeal(catStr = catStr).body().toMeals()
        _inProgress.value = true
        if (response.meals.isNotEmpty()) {
            _mealList.value = response.meals
            _inProgress.value = false

        } else {
            Log.d("Meal View Model", response.toString())

        }
    }
}



