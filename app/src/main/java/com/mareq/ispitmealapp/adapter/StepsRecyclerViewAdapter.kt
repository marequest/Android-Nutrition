package com.mareq.ispitmealapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mareq.ispitmealapp.presentation.activities.MealDetailsActivity
import com.mareq.ispitmealapp.R

class StepsRecyclerViewAdapter constructor(private val getActivity: MealDetailsActivity) :
    RecyclerView.Adapter<StepsRecyclerViewAdapter.StepsViewHolder>() {

    private val stepsList : MutableList<String> = mutableListOf()

    override fun getItemCount(): Int {
        return stepsList.size
    }

    fun setData(newStepsList: List<String>){
        stepsList.clear()
        stepsList.addAll(newStepsList)
        notifyDataSetChanged()
    }


    class StepsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvStep: TextView = itemView.findViewById(R.id.tvStepDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.step_item, parent, false)

        return StepsViewHolder(view)
    }

    override fun onBindViewHolder(holder: StepsViewHolder, position: Int) {
        val step = stepsList[position]
        holder.tvStep.text = step
    }

}