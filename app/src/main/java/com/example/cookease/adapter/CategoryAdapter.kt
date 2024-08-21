package com.example.cookease.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookease.model.Categories
import com.example.cookease.databinding.ItemsRecyclerBinding

class CategoryAdapter(private var catList: List<Categories.Category>, private var onItemClick :(Categories.Category) -> Unit) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    inner class CategoryViewHolder(private val binding: ItemsRecyclerBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(category: Categories.Category){
            Glide.with(itemView.context).load(category.strCategoryThumb).into(binding.foodImage)
            binding.foodName.text = category.strCategory
            binding.foodImage.setOnClickListener(){
                onItemClick.invoke(category)
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemsRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CategoryViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return catList.count()
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(catList[position])
    }
    fun updateCategories(newCategories: List<Categories.Category>) {
        catList = newCategories
        notifyDataSetChanged()  // Notify adapter about data change
    }

}