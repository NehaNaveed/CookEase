package com.example.cookease.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookease.activities.MealsScreenActivity.Companion.KEY_CATEGORY
import com.example.cookease.adapter.CategoryToMealsAdapter
import com.example.cookease.adapter.MealAdapter
import com.example.cookease.databinding.ActivityRecipeDetailsBinding
import com.example.cookease.model.Meals
import com.example.cookease.setInProgress
import com.example.cookease.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

class RecipeDetailsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MealAdapter
    private val recViewModel by lazy {
        ViewModelProvider(this)[RecipeViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityRecipeDetailsBinding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        val bundle = intent.extras
        setContentView(binding.root)
        recyclerView = binding.ingredientsRecycler
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MealAdapter(
            recipeList = emptyList(), onItemClick =
            { recipe ->
                Glide.with(this).load(recipe.strMealThumb).into(binding.recImage)
                binding.recName.text = recipe.strMeal
                binding.instructionsText.text = recipe.strInstructions
            }
        )
        recyclerView.adapter = adapter
        val mealId = bundle?.getString(KEY_CATEGORY)
        lifecycleScope.launch {
            recViewModel.getRecipes(mealId.toString())
        }

        recViewModel.recipeList.observe(this) { meal ->
            meal.let { adapter.updateIngredients(it) }
        }
        recViewModel.inProgress.observe(this){
                inProgress ->
            binding.progressBar.setInProgress(inProgress)
        }
        binding.backToMeals.setOnClickListener(){
            finish()
        }
    }

}