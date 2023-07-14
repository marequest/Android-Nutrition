package com.mareq.ispitmealapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mareq.ispitmealapp.presentation.activities.MealDetailsActivity
import com.mareq.ispitmealapp.R
import com.mareq.ispitmealapp.data.model.entities.Ingredient

class IngredientsRecyclerViewAdapter constructor(private val getActivity: MealDetailsActivity) :
    RecyclerView.Adapter<IngredientsRecyclerViewAdapter.IngredientsViewHolder>() {

    private val ingredientList : MutableList<Ingredient> = mutableListOf()

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    fun setData(newIngredientList: List<Ingredient>){
        val newList : List<Ingredient> = newIngredientList.filter{ ingredient -> ingredient.ingredientName.isNotBlank() && ingredient.measure.isNotBlank()}

        ingredientList.clear()
        ingredientList.addAll(newList)
        notifyDataSetChanged()
    }


    class IngredientsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val tvIngredient : TextView = itemView.findViewById(R.id.tvIngredient)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_step, parent, false)

        return IngredientsViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val ingredient = ingredientList[position]

        holder.tvIngredient.text = "${ingredient.ingredientName}: ${ingredient.measure}"
    }
}