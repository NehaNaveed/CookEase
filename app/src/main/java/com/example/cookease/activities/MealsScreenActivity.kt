package com.example.cookease.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookease.R
import com.example.cookease.adapter.CategoryToMealsAdapter
import com.example.cookease.databinding.ActivityMealsScreenBinding
import com.example.cookease.setInProgress
import com.example.cookease.viewmodel.MealViewModel
import kotlinx.coroutines.launch

class MealsScreenActivity : AppCompatActivity() {
    companion object{
        const val KEY_CATEGORY = "key_category"         //apply on whole proj
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryToMealsAdapter
    private val mealsViewModel by lazy {
        ViewModelProvider(this)[MealViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMealsScreenBinding.inflate(layoutInflater)
        val bundle = intent.extras
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val catStr = bundle?.getString(KEY_CATEGORY)
        binding.screenHeader.text = catStr
        recyclerView = binding.mealRecycler
        recyclerView.layoutManager = GridLayoutManager(this,2)
        adapter = CategoryToMealsAdapter(
            mealList = emptyList(), onItemClick = { meal ->

                val mealId = meal.idMeal
                val bundleId = Bundle()
                bundleId.putString(KEY_CATEGORY, mealId)            //sending bundle with activity


                startActivity(
                    Intent(
                        this@MealsScreenActivity,
                        RecipeDetailsActivity::class.java
                    ).putExtras(bundleId)
                )
            })
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            mealsViewModel.getMeals(catStr.toString())
        }
        mealsViewModel.mealList.observe(this) { meal ->
            meal.let { adapter.updateMeals(it) }
        }
        mealsViewModel.inProgress.observe(this){
                inProgress ->
            binding.progressBar.setInProgress(inProgress)
        }
        binding.backToHome.setOnClickListener(){
            finish()
        }
    }
}