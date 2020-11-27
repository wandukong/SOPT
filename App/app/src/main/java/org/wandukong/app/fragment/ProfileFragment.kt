package org.wandukong.app.fragment

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_signup.*
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

        btn_logout_profile.setOnClickListener {
            val member = view.context.getSharedPreferences("memberDB", AppCompatActivity.MODE_PRIVATE)

            val intent = Intent()
            intent.putExtra("name", member.getString("*LATEST*", ""))

            activity?.setResult(Activity.RESULT_OK, intent)

            val preferencesEditor: SharedPreferences.Editor = member.edit()
            preferencesEditor.remove("*LATEST*")
            preferencesEditor.commit()

            activity?.finish()
        }

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