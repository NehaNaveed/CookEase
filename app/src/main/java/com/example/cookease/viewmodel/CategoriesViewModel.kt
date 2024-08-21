package com.example.cookease.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cookease.RetrofitInstance
import com.example.cookease.model.Categories
import com.example.cookease.model.toCategory

class CategoriesViewModel(application: Application) : AndroidViewModel(application) {
    private val _categoryList = MutableLiveData<List<Categories.Category>>()
    val categoryList: LiveData<List<Categories.Category>> = _categoryList
    private var originalCategoryList: List<Categories.Category> = emptyList()

    private val _inProgress = MutableLiveData<Boolean>()
    val inProgress: LiveData<Boolean> = _inProgress

    suspend fun getData() {
        val response = RetrofitInstance.mealsInterface.getCategories().body().toCategory()
        _inProgress.value = true
        if (response.categories.isNotEmpty()) {
            originalCategoryList = response.categories
            _categoryList.value =  originalCategoryList
            Log.d("Category View Model", response.toString())
            _inProgress.value = false
        } else {
            Log.d("Category View Model", response.toString())

        }
    }

    fun filterCategories(query: String) {
        val filteredList = if (query.isEmpty()) {
            originalCategoryList
        } else {
            originalCategoryList.filter {
                it.strCategory.contains(query, ignoreCase = true)
            }
        }
        _categoryList.value = filteredList

    }

}