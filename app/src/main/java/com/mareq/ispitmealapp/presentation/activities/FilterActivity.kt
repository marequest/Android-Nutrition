package com.mareq.ispitmealapp.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.mareq.ispitmealapp.presentation.fragments.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mareq.ispitmealapp.R

class FilterActivity : AppCompatActivity() {

    private lateinit var tabLayout : TabLayout
    private lateinit var filterViewPager : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.filtering_tab_page)
        setSupportActionBar(findViewById(R.id.my_toolbar))


        init()

        filterViewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(tabLayout, filterViewPager) {tab, pos ->
            when(pos){
                0 -> tab.text = "Categories"
                1 -> tab.text = "Ingredients"
                2 -> tab.text = "Areas"
            }
        }.attach()
    }

    private fun init(){
        filterViewPager = findViewById(R.id.filterViewPager)
        tabLayout = findViewById(R.id.tabLayoutFilter)
    }


}