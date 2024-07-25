package com.example.instagramclone.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.instagramclone.fragment.ProfileFragment

class ViewPagerAdapter(fa: ProfileFragment) : FragmentStateAdapter(fa) {
    private val fragmentList = mutableListOf<Fragment>()
    private val titleList = mutableListOf<String>()

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
    fun addFragments(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        titleList.add(title)
    }

     fun getPageTitle(position: Int): String {
        return titleList[position]
    }





}