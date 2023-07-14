package com.mareq.ispitmealapp.presentation.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mareq.ispitmealapp.adapter.SavedMealsRecyclerViewAdapter
import com.mareq.ispitmealapp.presentation.contract.MealContract
import com.mareq.ispitmealapp.presentation.viewmodel.MealViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.mareq.ispitmealapp.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class SavedMealsChartActivity : AppCompatActivity() {

    private val mealViewModel : MealContract.MealViewModel by viewModel<MealViewModel>()

    private lateinit var mealByCatRecyclerView : RecyclerView
    private lateinit var savedMealsRecyclerViewAdapter : SavedMealsRecyclerViewAdapter

    private lateinit var searchSavedEditText : EditText
    private lateinit var barChart : BarChart
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_meals_charts)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        initViews()
        initObservers()
        makeCall()

        savedMealsRecyclerViewAdapter = SavedMealsRecyclerViewAdapter(getActivity = HomeActivity())
        val layoutManager : RecyclerView.LayoutManager = GridLayoutManager(this,2)
        savedMealsRecyclerViewAdapter.onItemClick = {
            val intent = Intent(this@SavedMealsChartActivity, MealDetailsActivity::class.java)
            intent.putExtra("lID", it.id)
            startActivity(intent)
        }

        mealByCatRecyclerView.layoutManager = layoutManager
        mealByCatRecyclerView.adapter = savedMealsRecyclerViewAdapter
    }

    private fun initViews() {
        searchSavedEditText = findViewById(R.id.searchSavedEditText)
        mealByCatRecyclerView = findViewById(R.id.savedMealsRecyclerView)
        barChart = findViewById(R.id.barChartMeals)
    }

    private fun makeCall() = with(mealViewModel){
        getMealsForUser(sharedPreferences.getString("userID", "").toString())
        getFullMealsForUser(sharedPreferences.getString("userID", "").toString())
    }

    private fun initObservers() = with(mealViewModel){
        savedMeals.observe(this@SavedMealsChartActivity) { savedMeals ->
            savedMealsRecyclerViewAdapter.setData(savedMeals)
        }

        mealsForMenu.observe(this@SavedMealsChartActivity) { meals ->
            makeGraph()
        }
    }

    private fun makeGraph() {
        val days = arrayOf("Mon","Tue","Wed","Thu","Fri","Sat","Sun")
        val mealsCount = mutableMapOf<String, Int>()

        for(m in mealViewModel.mealsForMenu.value!!){
            val day = m.meal.day
            val count = mealsCount.getOrDefault(day, 0)
            mealsCount[day] = count + 1
        }

        val mealCountsList = days.map {mealsCount.getOrDefault(it, 0)}
        val entriesList = mealCountsList.mapIndexed {index, count -> BarEntry(index.toFloat(), count.toFloat())}

        val barDataSet = BarDataSet(entriesList, "Meals Saved In Week")
//        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS, this)
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 18f


        val data = BarData(barDataSet)
        barChart.data = data

        val xAxis = barChart.xAxis
        xAxis?.valueFormatter = IndexAxisValueFormatter(arrayOf("Mon","Tue","Wed","Thu","Fri","Sat","Sun"))
        xAxis?.setDrawGridLines(false)
        xAxis?.granularity = 2f

        val yAxis = barChart.axisLeft
        yAxis?.axisMinimum = 0f

        barChart.description?.isEnabled = false
        barChart.legend?.isEnabled = false

        barChart.invalidate()
    }
}