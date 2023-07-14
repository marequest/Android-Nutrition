package com.mareq.ispitmealapp.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mareq.ispitmealapp.R
import com.mareq.ispitmealapp.data.model.entities.MealShort

class MealCategoryRecyclerViewAdapter constructor(private val getActivity: Activity) :
    RecyclerView.Adapter<MealCategoryRecyclerViewAdapter.MealViewHolder>() {

    private val mealList: MutableList<MealShort> = mutableListOf()

    lateinit var onItemClick : ((MealShort) -> Unit)

    override fun getItemCount(): Int {
        return mealList.size
    }

    fun getData() : List<MealShort> {
        return mealList
    }

    fun setData(newMealList : List<MealShort>){
        mealList.clear()
        mealList.addAll(newMealList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.meal_category_item, parent, false)

        return MealViewHolder(view)
    }

    class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivMealByCatImage : ImageView = itemView.findViewById(R.id.mealByCatImage)
        val tvMealTitle : TextView = itemView.findViewById(R.id.mealByCatTitle)
    }

    override fun onBindViewHolder(holder : MealViewHolder, position: Int){
        val meal = mealList[position]

        holder.tvMealTitle.text = meal.mealTitle

        holder.itemView.setOnClickListener {
            onItemClick.invoke(meal)
        }

        Glide
            .with(holder.itemView.context)
            .load(meal.mealThumbnail)
            .into(holder.ivMealByCatImage)
    }
}