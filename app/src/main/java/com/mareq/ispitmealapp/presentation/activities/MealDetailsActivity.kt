package com.mareq.ispitmealapp.presentation.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mareq.ispitmealapp.R
import com.mareq.ispitmealapp.adapter.IngredientsRecyclerViewAdapter
import com.mareq.ispitmealapp.data.model.database.IngredientEntity
import com.mareq.ispitmealapp.data.model.entities.Ingredient
import com.mareq.ispitmealapp.presentation.contract.MealContract
import com.mareq.ispitmealapp.presentation.viewmodel.MealViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MealDetailsActivity : AppCompatActivity() {

    private lateinit var ingredientsRecyclerView : RecyclerView
    private val mealViewModel : MealContract.MealViewModel by viewModel<MealViewModel>()

    private lateinit var ingredientsRecyclerViewAdapter : IngredientsRecyclerViewAdapter

    private var ingredientList = mutableListOf<Ingredient>()
    private var stepsList = mutableListOf<String>()

    private lateinit var mealItemImage : ImageView
    private lateinit var mealTitle : TextView
    private lateinit var mealTags : TextView
    private lateinit var mealYtLink : TextView
    private lateinit var saveButton : Button
    private lateinit var stepsTextView: TextView
    private lateinit var tvSteps: TextView

    private var mealId : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.meal_details_item)

        initViews()

        if(intent.getLongExtra("lID",0) != 0L){
            initObservers()
            val id = intent.getLongExtra("lID", 0)
            fetchMeal(id)

            saveButton.setOnClickListener {
                val intent = Intent(this@MealDetailsActivity, EditSavedMealActivity::class.java)
                intent.putExtra("savedID", id)
                startActivity(intent)
            }
        }else{
            init(intent.getLongExtra("id", 0 ))
        }

        ingredientList = ArrayList()
        stepsList = ArrayList()

        //Ingredients
        ingredientsRecyclerViewAdapter = IngredientsRecyclerViewAdapter(this)
        val layoutManager : RecyclerView.LayoutManager = GridLayoutManager(this, 1)
        ingredientsRecyclerView.layoutManager = layoutManager
        ingredientsRecyclerView.adapter = ingredientsRecyclerViewAdapter

    }

    private fun initViews(){
        mealItemImage = findViewById(R.id.ivMealItemImage)
        mealTitle = findViewById(R.id.tvMealTitle)
        mealTags = findViewById(R.id.tvMealTag)
        mealYtLink = findViewById(R.id.tvYtLink)
        saveButton = findViewById(R.id.saveMealButton)
        ingredientsRecyclerView = findViewById(R.id.ingredientsRecyclerView)
        stepsTextView = findViewById(R.id.stepsTextView)
        tvSteps = findViewById(R.id.tvSteps)
    }

    private fun init(params : Long){
        initObservers()
        initListeners()
        makeCalls(params)
        mealId = params
    }

    private fun initListeners(){
        saveButton.setOnClickListener {
            val intent = Intent(this@MealDetailsActivity, SaveMealActivity::class.java)
            intent.putExtra("id", mealId)
            startActivity(intent)
        }
    }

    private fun makeCalls(id : Long) = with(mealViewModel){
        getMealById(id)
    }

    private fun fetchMeal(id : Long) = with(mealViewModel){
        getMealWithIngredients(id)
    }

    private fun initObservers() = with(mealViewModel){
        meal.observe(this@MealDetailsActivity) {meal ->

            mealItemImage.let {
                Glide.with(this@MealDetailsActivity).load(meal[0].mealThumbnail)
                    .into(it)
            }

            mealTitle.text = meal[0].mealTitle
            mealTags.text = meal[0].tags
            mealYtLink.text = meal[0].ytLink

            stepsTextView.text = meal[0].instructions.joinToString(separator = System.lineSeparator() + System.lineSeparator())

            ingredientsRecyclerViewAdapter.setData(meal[0].ingredients)

            tvSteps.text = "Steps"
        }

        mealForMenu.observe(this@MealDetailsActivity) { mealI ->
            mealItemImage.setImageBitmap(base64ToBitmap(mealI.meal.mealThumbnail!!))
            mealTitle.text = mealI.meal.mealTitle
            mealTags.text = mealI.meal.tags
            mealYtLink.text = mealI.meal.ytLink
            ingredientsRecyclerViewAdapter.setData(convertToIngredients(mealI.ingredients))
            stepsTextView.text = ""
            tvSteps.text = ""
        }
    }

    private fun convertToIngredients(ingredientsEntity : List<IngredientEntity>) : List<Ingredient> {
        return ingredientsEntity.map {ingredientEntity ->
            Ingredient(
                ingredientName = ingredientEntity.ingredientName,
                measure = ingredientEntity.measure
            )
        }
    }

    private fun base64ToBitmap(base64String : String) : Bitmap {
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

}