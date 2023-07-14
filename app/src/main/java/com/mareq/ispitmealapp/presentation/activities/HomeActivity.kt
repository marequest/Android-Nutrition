package com.mareq.ispitmealapp.presentation.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mareq.ispitmealapp.R
import com.mareq.ispitmealapp.adapter.CategoryRecyclerViewAdapter
import com.mareq.ispitmealapp.data.model.entities.Category
import com.mareq.ispitmealapp.presentation.contract.CategoryContract
import com.mareq.ispitmealapp.presentation.contract.MealContract
import com.mareq.ispitmealapp.presentation.viewmodel.CategoryViewModel
import com.mareq.ispitmealapp.presentation.viewmodel.MealViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : AppCompatActivity() {

    private lateinit var catRecyclerView : RecyclerView
    private lateinit var categoryRecyclerViewAdapter : CategoryRecyclerViewAdapter
    private var categoryList = mutableListOf<Category>()

    private val categoryViewModel : CategoryContract.CategoryViewModel by viewModel<CategoryViewModel>()
    private val mealViewModel : MealContract.MealViewModel by viewModel<MealViewModel>()

    private lateinit var searchText : EditText
    private lateinit var loadingDialog : AlertDialog.Builder
    private lateinit var myDialog : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val myToolbar: Toolbar = findViewById<View>(R.id.my_toolbar) as Toolbar
        setSupportActionBar(myToolbar)

        loadingDialog = AlertDialog.Builder(this@HomeActivity)
            .setMessage("Please wait.")
        myDialog = loadingDialog.create()

        init()
    }

    private fun init(){
        initViews()
        initListeners()
        initObservers()
        makeCalls()
        initRecyclerView()
    }

    private fun initRecyclerView(){
        categoryList = ArrayList()
        categoryRecyclerViewAdapter = CategoryRecyclerViewAdapter(getActivity = MainActivity())
        val layoutManager : RecyclerView.LayoutManager = GridLayoutManager(this, 2)
        catRecyclerView.layoutManager = layoutManager

        categoryRecyclerViewAdapter.onItemClick = {
            val intent = Intent(this@HomeActivity, MealsActivity::class.java)
            intent.putExtra("category", it.categoryName)
            startActivity(intent)
        }

        catRecyclerView.adapter = categoryRecyclerViewAdapter
    }

    private fun initViews() {
        catRecyclerView = findViewById(R.id.catRecyclerView)
        searchText = findViewById(R.id.searchBarHomePage)
    }

    private fun initListeners(){
        searchText.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || keyEvent.action == KeyEvent.ACTION_DOWN || keyEvent.action == KeyEvent.KEYCODE_ENTER) {
                val search = searchText.text.toString()
                searchText.setText("")
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(searchText.windowToken, 0)

                mealViewModel.getMealsByIngredient(search)

                val mealsIntent = Intent(this@HomeActivity, MealsActivity::class.java)
                mealsIntent.putExtra("searchKey", search)
                startActivity(mealsIntent)
            }
            false
        })
    }

    private fun initObservers() = with(categoryViewModel){
        categories.observe(this@HomeActivity) { newList ->
            myDialog.hide()
            categoryRecyclerViewAdapter.setData(newList)
        }
    }

    private fun makeCalls() = with(categoryViewModel) {
        myDialog.show()
        getCategories()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.itemProfile -> {
                val savedMeals = Intent(this@HomeActivity, SavedMealsChartActivity::class.java)
                startActivity(savedMeals)
                true
            }
            R.id.itemFilter -> {
                val filters = Intent(this@HomeActivity, FilterActivity::class.java)
                startActivity(filters)
                true
            }
            R.id.itemPlan -> {
                val plan = Intent(this@HomeActivity, PlanActivity::class.java)
                startActivity(plan)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}