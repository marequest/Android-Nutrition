package com.mareq.ispitmealapp.presentation.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mareq.ispitmealapp.R
import com.mareq.ispitmealapp.adapter.MealCategoryRecyclerViewAdapter
import com.mareq.ispitmealapp.data.model.entities.MealShort
import com.mareq.ispitmealapp.presentation.activities.MealDetailsActivity
import com.mareq.ispitmealapp.presentation.contract.AreaContract
import com.mareq.ispitmealapp.presentation.contract.MealContract
import com.mareq.ispitmealapp.presentation.viewmodel.AreaViewModel
import com.mareq.ispitmealapp.presentation.viewmodel.MealViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class FilterAreasFragment : Fragment() {

    private val mealViewModel : MealContract.MealViewModel by viewModel<MealViewModel>()
    private val areaViewModel : AreaContract.AreaViewModel by viewModel<AreaViewModel>()

    private lateinit var mealByAreaRecyclerView : RecyclerView
    private lateinit var mealByAreaRecyclerViewAdapter : MealCategoryRecyclerViewAdapter

    private lateinit var areaDropdown : Spinner
    private lateinit var searchEditText : EditText
    private lateinit var sortButton : ImageView

    private var sortFlag : Boolean = true
    private lateinit var currList: List<MealShort>

    private lateinit var loadingDialog : AlertDialog.Builder
    private lateinit var myDialog : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAreaObservers()
        initMealObservers()
        getAreas()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_areas_filter, container, false)

        searchEditText = view.findViewById(R.id.searchListedMealsEditText)
        sortButton = view.findViewById(R.id.sortAlphabeticallyButton)
        areaDropdown = view.findViewById(R.id.areasDropdown)
        mealByAreaRecyclerView = view.findViewById(R.id.areasFilterRecyclerView)

        loadingDialog = AlertDialog.Builder(requireActivity())
            .setMessage("Please wait.")
        myDialog = loadingDialog.create()

        initListeners()
        initRecyclerView()

        return view
    }

    private fun initRecyclerView() {
        mealByAreaRecyclerViewAdapter = MealCategoryRecyclerViewAdapter(requireActivity())
        val layoutManager : RecyclerView.LayoutManager = GridLayoutManager(requireContext(),2)
        mealByAreaRecyclerView.layoutManager = layoutManager
        mealByAreaRecyclerView.adapter = mealByAreaRecyclerViewAdapter

        mealByAreaRecyclerViewAdapter.onItemClick = {
            Toast.makeText(requireContext(), it.mealTitle, Toast.LENGTH_LONG).show()
            val mealIntent = Intent(requireActivity(), MealDetailsActivity::class.java)
            mealIntent.putExtra("id", it.id)
            startActivity(mealIntent)
        }
    }

    private fun initListeners() {
        areaDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selection = areaDropdown.adapter?.getItem(position).toString()
                getMealsByArea(selection)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // TODO
            }
        }

        searchEditText.setOnEditorActionListener(OnEditorActionListener { v, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || keyEvent.action == KeyEvent.ACTION_DOWN || keyEvent.action == KeyEvent.KEYCODE_ENTER) {
                val search = searchEditText.text.toString()
                searchEditText.setText("")
                val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(searchEditText.windowToken, 0)

                val newList = currList.filter {it.mealTitle.contains(search, ignoreCase = true) }
                mealByAreaRecyclerViewAdapter.setData(newList)
            }
            false
        })

        sortButton.setOnClickListener {
            if(mealByAreaRecyclerViewAdapter.getData().isNotEmpty()){
                if(sortFlag){
                    val sortedList = mealByAreaRecyclerViewAdapter.getData().sortedByDescending { it.mealTitle }
                    mealByAreaRecyclerViewAdapter.setData(sortedList)
                }else{
                    val sortedList = mealByAreaRecyclerViewAdapter.getData().sortedBy {it.mealTitle}
                    mealByAreaRecyclerViewAdapter.setData(sortedList)
                }
            }
            sortFlag = !sortFlag
        }
    }

    private fun getAreas() = with(areaViewModel){
        getAreas()
    }

    private fun getMealsByArea(area : String) = with(mealViewModel){
        myDialog.show()
        getMealsByArea(area)
    }

    private fun initAreaObservers() = with(areaViewModel) {
        areas.observe(requireActivity()) {newList ->
            val stringList : Array<String> = newList.map{it.areaName}.toTypedArray()
            areaDropdown.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, stringList)
        }
    }

    private fun initMealObservers() = with(mealViewModel){
        mealsShort.observe(requireActivity()) { newList ->
            myDialog.hide()
            mealByAreaRecyclerViewAdapter.setData(newList)
            currList = newList
        }
    }
}