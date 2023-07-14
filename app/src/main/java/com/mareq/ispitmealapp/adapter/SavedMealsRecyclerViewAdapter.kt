package com.mareq.ispitmealapp.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mareq.ispitmealapp.R
import com.mareq.ispitmealapp.data.model.entities.MealShort

class SavedMealsRecyclerViewAdapter constructor(private val getActivity: Activity) :
    RecyclerView.Adapter<SavedMealsRecyclerViewAdapter.MealViewHolder>() {

    lateinit var onItemClick : ((MealShort) -> Unit)

    private val mealList: MutableList<MealShort> = mutableListOf()

    override fun getItemCount(): Int {
        return mealList.size
    }

    fun setData(newMealList : List<MealShort>){
        mealList.clear()
        mealList.addAll(newMealList)
        notifyDataSetChanged()
    }


    class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivMealByCatImage : ImageView = itemView.findViewById(R.id.mealByCatImage)
        val tvMealTitle : TextView = itemView.findViewById(R.id.mealByCatTitle)
        val mealCardView : CardView = itemView.findViewById(R.id.mealByCategoryCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.meal_category_item, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder : MealViewHolder, position: Int){
        val meal = mealList[position]

        holder.tvMealTitle.text = meal.mealTitle

        holder.itemView.setOnClickListener {
            onItemClick.invoke(meal)
        }

        holder.ivMealByCatImage.setImageBitmap(base64ToBitmap(meal.mealThumbnail))
    }


    private fun base64ToBitmap(base64String : String) : Bitmap {
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }
}