package org.wandukong.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import org.wandukong.app.profile.ProfileFragment
import org.wandukong.app.users.UsersFragment
import org.wandukong.app.search.WebSearchFragment
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
                val userFragment = UsersFragment()
                return userFragment
            }
            2 -> {
                val tempFragment = WebSearchFragment()
                return tempFragment
            }
            else -> throw IllegalStateException("Unexpected $position")
        }
    }
}