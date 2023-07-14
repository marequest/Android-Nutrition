package com.mareq.ispitmealapp.presentation.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mareq.ispitmealapp.presentation.activities.MealDetailsActivity
import com.mareq.ispitmealapp.R
import com.mareq.ispitmealapp.adapter.MealCategoryRecyclerViewAdapter
import com.mareq.ispitmealapp.data.model.entities.MealShort
import com.mareq.ispitmealapp.presentation.contract.IngredientContract
import com.mareq.ispitmealapp.presentation.contract.MealContract
import com.mareq.ispitmealapp.presentation.viewmodel.IngredientViewModel
import com.mareq.ispitmealapp.presentation.viewmodel.MealViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilterIngredientsFragment : Fragment() {

    private val ingredientViewModel : IngredientContract.IngredientViewModel by viewModel<IngredientViewModel>()
    private val mealViewModel : MealContract.MealViewModel by viewModel<MealViewModel>()

    private lateinit var mealByIngredientRecyclerView : RecyclerView
    private lateinit var mealByIngredientRecyclerViewAdapter : MealCategoryRecyclerViewAdapter

    private lateinit var ingredientDropdown : Spinner
    private lateinit var searchEditText : EditText
    private lateinit var sortButton : ImageView

    private var sortFlag : Boolean = true
    private lateinit var currList: List<MealShort>


    private lateinit var loadingDialog : AlertDialog.Builder
    private lateinit var myDialog : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initIngredientsObservers()
        initMealObservers()
        getIngredients()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ingredients_filter, container, false)

        searchEditText = view.findViewById(R.id.searchListedMealsEditText)
        ingredientDropdown = view.findViewById(R.id.ingredientsDropdown)
        sortButton = view.findViewById(R.id.sortAlphabeticallyButton)
        mealByIngredientRecyclerView = view.findViewById(R.id.ingredientsFilterRecyclerView)

        loadingDialog = AlertDialog.Builder(requireActivity())
            .setMessage("Please wait.")
        myDialog = loadingDialog.create()

        initListeners()
        initIngredientsObservers()
        initRecyclerView()

        return view
    }

    private fun initRecyclerView() {
        mealByIngredientRecyclerViewAdapter = MealCategoryRecyclerViewAdapter(requireActivity())
        val layoutManager : RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 2)
        mealByIngredientRecyclerView.layoutManager = layoutManager
        mealByIngredientRecyclerView.adapter = mealByIngredientRecyclerViewAdapter

        mealByIngredientRecyclerViewAdapter.onItemClick = {
            Toast.makeText(requireContext(), it.mealTitle, Toast.LENGTH_LONG).show()
            val mealIntent = Intent(requireActivity(), MealDetailsActivity::class.java)
            mealIntent.putExtra("id", it.id)
            startActivity(mealIntent)
        }
    }

    private fun initListeners() {
        ingredientDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selection = ingredientDropdown.adapter?.getItem(position).toString()
                getMealsByIngredient(selection)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // TODO
            }
        }

        searchEditText.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || keyEvent.action == KeyEvent.ACTION_DOWN || keyEvent.action == KeyEvent.KEYCODE_ENTER) {
                val search = searchEditText.text.toString()
                searchEditText.setText("")
                val inputMethodManager =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(searchEditText.windowToken, 0)

                val newList = currList.filter { it.mealTitle.contains(search, ignoreCase = true) }
                mealByIngredientRecyclerViewAdapter.setData(newList)

            }
            false
        })

        sortButton.setOnClickListener {
            if(mealByIngredientRecyclerViewAdapter.getData().isNotEmpty()){
                if(sortFlag){
                    val sortedList = mealByIngredientRecyclerViewAdapter.getData().sortedByDescending { it.mealTitle }
                    mealByIngredientRecyclerViewAdapter.setData(sortedList)
                }else{
                    val sortedList = mealByIngredientRecyclerViewAdapter.getData().sortedBy {it.mealTitle}
                    mealByIngredientRecyclerViewAdapter.setData(sortedList)
                }
            }

            sortFlag = !sortFlag
        }
    }

    private fun getMealsByIngredient(ingredient : String) = with(mealViewModel){
        myDialog.show()
        getMealsByIngredient(ingredient)
    }

    private fun getIngredients() = with(ingredientViewModel){
        getIngredients()
    }

    private fun initIngredientsObservers() = with(ingredientViewModel){
        ingredients.observe(requireActivity()) {newList ->
            val stringList : Array<String> = newList.map{it.ingredientName}.toTypedArray()
            ingredientDropdown.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, stringList)
        }
    }

    private fun initMealObservers() = with(mealViewModel) {
        mealsShort.observe(requireActivity()){ newList ->
            myDialog.hide()
            mealByIngredientRecyclerViewAdapter.setData(newList)
            currList = newList
        }
    }
}