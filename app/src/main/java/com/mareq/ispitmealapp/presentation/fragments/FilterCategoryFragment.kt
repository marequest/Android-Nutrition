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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mareq.ispitmealapp.presentation.activities.MealDetailsActivity
import com.mareq.ispitmealapp.R
import com.mareq.ispitmealapp.adapter.MealCategoryRecyclerViewAdapter
import com.mareq.ispitmealapp.data.model.entities.MealShort
import com.mareq.ispitmealapp.presentation.contract.CategoryContract
import com.mareq.ispitmealapp.presentation.contract.MealContract
import com.mareq.ispitmealapp.presentation.viewmodel.CategoryViewModel
import com.mareq.ispitmealapp.presentation.viewmodel.MealViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilterCategoryFragment : Fragment() {

    private val mealViewModel : MealContract.MealViewModel by viewModel<MealViewModel>()
    private val categoryViewModel : CategoryContract.CategoryViewModel by viewModel<CategoryViewModel>()

    private lateinit var mealByCatRecyclerView : RecyclerView
    private lateinit var mealByCatRecyclerViewAdapter : MealCategoryRecyclerViewAdapter

    private lateinit var categoryDropdown : Spinner
    private lateinit var searchEditText : EditText
    private lateinit var sortButton : ImageView

    private var sortFlag : Boolean = true
    private lateinit var currList: List<MealShort>


    private lateinit var loadingDialog : AlertDialog.Builder
    private lateinit var myDialog : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initCategoriesObservers()
        initMealObservers()
        makeCalls()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_category_filter, container, false)

        searchEditText = view.findViewById(R.id.searchListedMealsEditText)
        sortButton = view.findViewById(R.id.sortAlphabeticallyButton)
        categoryDropdown = view.findViewById(R.id.categoryDropdown)
        mealByCatRecyclerView = view.findViewById(R.id.categoryFilterRecyclerView)

        loadingDialog = AlertDialog.Builder(requireActivity())
            .setMessage("Please wait.")
        myDialog = loadingDialog.create()

        initListeners()
        initRecyclerView()

        return view
    }

    private fun initRecyclerView() {
        mealByCatRecyclerViewAdapter = MealCategoryRecyclerViewAdapter(requireActivity())
        val layoutManager : RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 2)
        mealByCatRecyclerView.layoutManager = layoutManager
        mealByCatRecyclerView.adapter = mealByCatRecyclerViewAdapter

        mealByCatRecyclerViewAdapter.onItemClick = {
            Toast.makeText(requireContext(), it.mealTitle, Toast.LENGTH_LONG).show()
            val mealIntent = Intent(requireActivity(), MealDetailsActivity::class.java)
            mealIntent.putExtra("id", it.id)
            startActivity(mealIntent)
        }
    }

    private fun initListeners(){
        categoryDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selection = categoryDropdown.adapter?.getItem(position).toString()
                myDialog.show()
                getByCategory(selection)
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
                mealByCatRecyclerViewAdapter.setData(newList)

            }
            false
        })

        sortButton.setOnClickListener {
            if(mealByCatRecyclerViewAdapter.getData().isNotEmpty()){
                if(sortFlag){
                   val sortedList = mealByCatRecyclerViewAdapter.getData().sortedByDescending { it.mealTitle }
                    mealByCatRecyclerViewAdapter.setData(sortedList)
                }else{
                    val sortedList = mealByCatRecyclerViewAdapter.getData().sortedBy {it.mealTitle}
                    mealByCatRecyclerViewAdapter.setData(sortedList)
                }
            }

            sortFlag = !sortFlag
        }
    }

    private fun makeCalls() = with(categoryViewModel) {
        getCategories()
    }

    private fun getByCategory(category : String) = with(mealViewModel){
        getMealsByCategory(category)
    }

    private fun initCategoriesObservers() = with(categoryViewModel) {
        categories.observe(requireActivity()) { newList ->
            val stringList : Array<String> = newList.map {it.categoryName}.toTypedArray()
            categoryDropdown.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, stringList)
        }
    }

    private fun initMealObservers() = with(mealViewModel) {
        mealsShort.observe(requireActivity()) { newList ->
            myDialog.hide()
            mealByCatRecyclerViewAdapter.setData(newList)
            currList = newList
        }
    }
}