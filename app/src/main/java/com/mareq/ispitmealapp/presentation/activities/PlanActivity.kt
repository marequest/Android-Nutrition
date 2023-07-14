package com.mareq.ispitmealapp.presentation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mareq.ispitmealapp.R
import com.mareq.ispitmealapp.adapter.MealCategoryRecyclerViewAdapter
import com.mareq.ispitmealapp.data.model.entities.PlanHolder
import com.mareq.ispitmealapp.presentation.contract.CategoryContract
import com.mareq.ispitmealapp.presentation.contract.MealContract
import com.mareq.ispitmealapp.presentation.viewmodel.CategoryViewModel
import com.mareq.ispitmealapp.presentation.viewmodel.MealViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlanActivity : AppCompatActivity() {

    private lateinit var dayDropdown : Spinner
    private lateinit var mealTypeDropdown : Spinner
    private lateinit var mealCategoryDropdown : Spinner
    private lateinit var receiverEmail : EditText
    private lateinit var sendPlanButton : Button

    private var mealsForPlanRecyclerView : RecyclerView? = null
    private var mealsForPlanRecyclerViewAdapter : MealCategoryRecyclerViewAdapter? = null
    private val mealViewModel : MealContract.MealViewModel by viewModel<MealViewModel>()
    private val categoryViewModel : CategoryContract.CategoryViewModel by viewModel<CategoryViewModel>()

    private val daysArray = arrayOf("Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday")
    private val mealTypeArray = arrayOf("Dorucak","Rucak","Uzina","Vecera")

    private var daySelected : String? = null
    private var mealTypeSelected : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan)
        setSupportActionBar(findViewById(R.id.my_toolbar))


        init()
    }

    private fun init() {
        initViews()
        initRecyclers()
        initListeners()
        initObserversCat()
        initObserversMeals()
        getCategories()
    }

    private fun initViews(){
        dayDropdown = findViewById(R.id.dayDropdown)
        mealTypeDropdown = findViewById(R.id.mealTypeSpinner)
        mealCategoryDropdown = findViewById(R.id.mealCategoryDropdown)
        receiverEmail = findViewById(R.id.emailEditText)
        sendPlanButton = findViewById(R.id.sendPlanButton)
        mealsForPlanRecyclerView = findViewById(R.id.planMealsRecyclerView)
    }

    private val weekMap : MutableMap<String, MutableList<PlanHolder>> = mutableMapOf(
        "Monday" to mutableListOf(), "Tuesday" to mutableListOf(), "Wednesday" to mutableListOf(),
        "Thursday" to mutableListOf(), "Friday" to mutableListOf(), "Saturday" to mutableListOf(), "Sunday" to mutableListOf(),
    )

    private fun initRecyclers() {
        mealsForPlanRecyclerViewAdapter = MealCategoryRecyclerViewAdapter(getActivity = PlanActivity())
        val layoutManager : RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 2)
        mealsForPlanRecyclerView!!.layoutManager = layoutManager
        mealsForPlanRecyclerView?.adapter = mealsForPlanRecyclerViewAdapter

        mealsForPlanRecyclerViewAdapter?.onItemClick = {
            val curr = PlanHolder(mealType = mealTypeSelected.toString(), day = daySelected.toString(), mealName = it.mealTitle)
            weekMap[daySelected]?.add(curr)

            Toast.makeText(this, "Meal added: ${daySelected} ${mealTypeSelected} ${it.mealTitle}", Toast.LENGTH_LONG).show()
        }

        val dayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, daysArray)
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dayDropdown.adapter = dayAdapter

        val mealTypeAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mealTypeArray)
        mealTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mealTypeDropdown.adapter = mealTypeAdapter
    }

    private fun initListeners(){
        mealCategoryDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selection = mealCategoryDropdown.adapter?.getItem(position).toString()
                getMeals(selection)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO
            }
        }
        mealTypeDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                mealTypeSelected = mealTypeDropdown.adapter?.getItem(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO
            }
        }
        dayDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                daySelected = dayDropdown.adapter?.getItem(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO
            }
        }

        sendPlanButton.setOnClickListener {
            sendEmail(receiverEmail.text.toString())
        }
    }


    private fun getCategories() = with(categoryViewModel) {
        getCategories()
    }

    private fun getMeals(cat : String) = with(mealViewModel) {
        getMealsByCategory(cat)
    }

    private fun initObserversCat() = with(categoryViewModel){
        categories.observe(this@PlanActivity) { newList ->
            val stringList : Array<String> = newList.map {it.categoryName}.toTypedArray()
            mealCategoryDropdown.adapter = ArrayAdapter<String>(this@PlanActivity, android.R.layout.simple_list_item_1, stringList)
        }
    }

    private fun initObserversMeals() = with(mealViewModel){
        mealsShort.observe(this@PlanActivity) { newList ->
            mealsForPlanRecyclerViewAdapter?.setData(newList)
        }
    }

    private fun sendEmail(recepient : String) {
        val subject = "Weekly meal plan sent from IspitMealApp"
        val content = makeContent(weekMap)

        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recepient))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, content)
        }

        if(emailIntent.resolveActivity(packageManager) != null){
            startActivity(Intent.createChooser(emailIntent, "Choose email client:"))
        }
    }

    private fun makeContent(map : MutableMap<String, MutableList<PlanHolder>>) : String {
        val stringBuilder = StringBuilder()

        for((day, meals) in map){
            stringBuilder.append("Day: $day ${System.lineSeparator()}")
            for(meal in meals){
                val mealName = meal.mealName
                val mealType = meal.mealType

                stringBuilder.append("Meal: $mealName ${System.lineSeparator()}Meal type: $mealType ${System.lineSeparator()}")
            }
            stringBuilder.append(System.lineSeparator())
        }

        return stringBuilder.toString()
    }
}