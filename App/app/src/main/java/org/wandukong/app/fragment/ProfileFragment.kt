package org.wandukong.app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_profile.*
import org.wandukong.app.*
import org.wandukong.app.adapter.ProfileViewPagerAdapter

class ProfileFragment : Fragment() {
    private lateinit var viewPagerAdapter: ProfileViewPagerAdapter
    lateinit var name : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPagerAdapter = ProfileViewPagerAdapter(childFragmentManager)
        vp_profile.adapter = viewPagerAdapter

        tl_profile.setupWithViewPager(vp_profile)
        tl_profile.apply{
            getTabAt(0)?.text = "INFO"
            getTabAt(1)?.text = "OTHER"
        }
        name = arguments?.getString("name").toString()
        tv_name_profile.text = name
    }
}