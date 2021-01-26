package org.wandukong.navigation

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private lateinit var firstFragment: FirstFragment
    private lateinit var secondFragment: SecondFragment
    private lateinit var thirdFragment: ThirdFragment

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 ->{
                if(firstFragment == null){
                    firstFragment = FirstFragment()
                }
                return firstFragment
            }
            1 ->{
                if(secondFragment == null){
                    secondFragment = SecondFragment()
                }
                return secondFragment
            }
            2 ->{
                if(thirdFragment == null){
                    thirdFragment = ThirdFragment()
                }
                return thirdFragment
            }
            else -> throw IllegalStateException("Unexpected $position")
        }
    }
}