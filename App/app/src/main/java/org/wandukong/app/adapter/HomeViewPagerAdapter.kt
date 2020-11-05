package org.wandukong.app.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import org.wandukong.app.fragment.ProfileFragment
import org.wandukong.app.fragment.TeamFragment
import org.wandukong.app.fragment.TempFragment
import java.lang.IllegalStateException

class HomeViewPagerAdapter (fm : FragmentManager, val bundle: Bundle)
    : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    override fun getCount(): Int = 3

    override fun getItem(position: Int) : Fragment {
        when (position) {
            0 -> {
                val profileFragment = ProfileFragment()
                profileFragment.arguments = bundle
                return profileFragment
            }
            1 -> {
                val teamFragment = TeamFragment()
                return teamFragment
            }
            2 -> {
                val tempFragment = TempFragment()
                return tempFragment
            }
            else -> throw IllegalStateException("Unexpected $position")
        }
    }
}