package com.mareq.ispitmealapp.presentation.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fa : FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> FilterCategoryFragment()
            1 -> FilterIngredientsFragment()
            2 -> FilterAreasFragment()
            else -> FilterCategoryFragment()
        }
    }
}