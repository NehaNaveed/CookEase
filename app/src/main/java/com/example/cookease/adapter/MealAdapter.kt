package com.example.cookease.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cookease.databinding.RecipeRecyclerBinding
import com.example.cookease.model.Meals

class MealAdapter (private var recipeList : List<Meals.Meal>, private var onItemClick:(Meals.Meal) -> Unit) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    inner class MealViewHolder(private val binding: RecipeRecyclerBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(recipe: Meals.Meal){
            binding.ingredientName.text = recipe.ingList.joinToString (separator = " , ")
            onItemClick.invoke(recipe)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = RecipeRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return recipeList.count()
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(recipeList[position])

    }
    fun updateIngredients(recipe: List<Meals.Meal>) {
        recipeList = recipe
        notifyDataSetChanged()
    }



}