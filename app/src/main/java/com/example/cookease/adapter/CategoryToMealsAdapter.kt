package com.example.cookease.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookease.model.CategoryToMeal
import com.example.cookease.databinding.ItemsRecyclerBinding
import com.example.cookease.model.Categories


class CategoryToMealsAdapter(private var mealList: List<CategoryToMeal.Meal>, private var onItemClick :(CategoryToMeal.Meal) -> Unit): RecyclerView.Adapter<CategoryToMealsAdapter.CatMealsViewHolder>() {

    inner class CatMealsViewHolder(private val binding: ItemsRecyclerBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(meal: CategoryToMeal.Meal){
            Glide.with(itemView.context).load(meal.strMealThumb).into(binding.foodImage)
            binding.foodName.text = meal.strMeal
            binding.foodImage.setOnClickListener(){
                onItemClick.invoke(meal)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatMealsViewHolder {
        val binding = ItemsRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatMealsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mealList.count()
    }

    override fun onBindViewHolder(holder: CatMealsViewHolder, position: Int) {
        holder.bind(mealList[position])

    }
    fun updateMeals(meal: List<CategoryToMeal.Meal>) {
        mealList = meal
        notifyDataSetChanged()
    }

}





