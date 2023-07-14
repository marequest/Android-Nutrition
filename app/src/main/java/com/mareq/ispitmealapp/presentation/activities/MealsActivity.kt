package com.mareq.ispitmealapp.presentation.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mareq.ispitmealapp.R
import com.mareq.ispitmealapp.adapter.MealCategoryRecyclerViewAdapter
import com.mareq.ispitmealapp.data.model.entities.MealShort
import com.mareq.ispitmealapp.presentation.contract.MealContract
import com.mareq.ispitmealapp.presentation.viewmodel.MealViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MealsActivity : AppCompatActivity() {

    private val mealViewModel : MealContract.MealViewModel by viewModel<MealViewModel>()

    private lateinit var mealByCatRecyclerView : RecyclerView
    private lateinit var mealByCatRecyclerViewAdapter : MealCategoryRecyclerViewAdapter

    private lateinit var searchEditText : EditText

    private var mealList = mutableListOf<MealShort>()
    private lateinit var searchKey : String
    private lateinit var category : String
    private lateinit var currList: List<MealShort>

    private lateinit var loadingDialog : AlertDialog.Builder
    private lateinit var lDialog : AlertDialog

    private fun initViews(){
        searchEditText = findViewById(R.id.searchBarMealsPage)
        mealByCatRecyclerView = findViewById(R.id.mealsRecyclerView)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meals)

        initViews()
        initListeners()
        initObservers()

        loadingDialog = AlertDialog.Builder(this@MealsActivity)
            .setMessage("Please wait.")
        lDialog = loadingDialog.create()

        searchKey = intent.getStringExtra("searchKey") ?: ""
        category = intent.getStringExtra("category") ?: ""
        if(searchKey.isNotEmpty()){
            search(searchKey)
        }
        if(category.isNotEmpty()){
            getByCategory(category)
        }

        mealList = ArrayList()

        mealByCatRecyclerViewAdapter = MealCategoryRecyclerViewAdapter(getActivity = HomeActivity())
        mealByCatRecyclerViewAdapter.onItemClick = {
            val intent = Intent(this@MealsActivity, MealDetailsActivity::class.java)
            intent.putExtra("id", it.id)
            startActivity(intent)
        }

        val layoutManager : RecyclerView.LayoutManager = GridLayoutManager(this,2)
        mealByCatRecyclerView.layoutManager = layoutManager
        mealByCatRecyclerView.adapter = mealByCatRecyclerViewAdapter
    }

    private fun search(searchKey : String) {
        lDialog.show()

        getByIngredient(searchKey)
        getByName(searchKey)
    }

    private fun getByCategory(category : String) = with(mealViewModel){
        getMealsByCategory(category)
    }

    private fun getByIngredient(ingredient : String) = with(mealViewModel){
        getMealsByIngredient(ingredient)
    }

    private fun getByName(name : String) = with(mealViewModel){
        getMealsByName(name)
    }

    private fun initObservers() = with(mealViewModel){
        mealsShort.observe(this@MealsActivity) { newList ->
            mealByCatRecyclerViewAdapter.setData(newList)
            currList = newList
            lDialog.hide()
        }
    }

    private fun initListeners() {
        searchEditText.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || keyEvent.action == KeyEvent.ACTION_DOWN || keyEvent.action == KeyEvent.KEYCODE_ENTER) {
                val search = searchEditText.text.toString()
                searchEditText.setText("")

                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(searchEditText.windowToken, 0)

//                search(search)

                val newList = currList.filter { it.mealTitle.contains(search, ignoreCase = true) }
                mealByCatRecyclerViewAdapter.setData(newList)
            }
            false
        })
    }

}

