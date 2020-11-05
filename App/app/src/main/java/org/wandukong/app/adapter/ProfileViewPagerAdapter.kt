package org.wandukong.app.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import org.wandukong.app.fragment.ProfileInfoFragment
import org.wandukong.app.fragment.ProfileOtherFragment

class ProfileViewPagerAdapter (fm : FragmentManager)
    : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment = when(position){
        0 -> ProfileInfoFragment()
        1 -> ProfileOtherFragment()
        else -> throw IllegalStateException("Unexpected $position")
    }

}