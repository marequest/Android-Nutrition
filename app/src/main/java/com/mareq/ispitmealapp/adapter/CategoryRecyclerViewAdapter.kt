package com.mareq.ispitmealapp.adapter

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mareq.ispitmealapp.R
import com.mareq.ispitmealapp.data.model.entities.Category

class CategoryRecyclerViewAdapter constructor(private val getActivity: Activity) :
    RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryViewHolder>() {

    private val categoryList: MutableList<Category> = mutableListOf()

    lateinit var onItemClick : ((Category) -> Unit)

    override fun getItemCount(): Int {
        return categoryList.size
    }

    fun setData(newCategoryList: List<Category>){
        categoryList.clear()
        categoryList.addAll(newCategoryList)
        notifyDataSetChanged()
    }


    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCategoryTitle: TextView = itemView.findViewById(R.id.categoryTitle)
        val ivCategoryImage: ImageView = itemView.findViewById(R.id.categoryImage)
        val ivMoreDetails: ImageView = itemView.findViewById(R.id.categoryDetails)
        val catCardView: CardView = itemView.findViewById(R.id.categoryCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_category_item, parent, false)

        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]

        holder.tvCategoryTitle.text = category.categoryName

        Glide.with(holder.itemView.context).load(category.categoryThumbnail)
            .into(holder.ivCategoryImage)

        holder.catCardView.setOnClickListener {
            Toast.makeText(holder.itemView.context, category.categoryName, Toast.LENGTH_LONG).show()
        }

        holder.itemView.setOnClickListener {
            onItemClick.invoke(category)
        }

        holder.ivMoreDetails.setOnClickListener {
            val builder = AlertDialog.Builder(holder.itemView.context)
                .setMessage(category.categoryDescription)
                .setPositiveButton("OK") {
                        dialog, _ -> dialog.dismiss()
                }

            builder.create().show()
        }
    }

}