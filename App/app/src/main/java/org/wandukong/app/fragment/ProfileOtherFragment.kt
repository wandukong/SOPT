package org.wandukong.app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_profile_info.*
import kotlinx.android.synthetic.main.fragment_profile_other.*
import org.wandukong.app.R
import org.wandukong.app.adapter.ProfileInfoAdapter
import org.wandukong.app.adapter.ProfileOtherAdapter
import org.wandukong.app.model.ProfileInfoData
import org.wandukong.app.model.ProfileOtherData

class ProfileOtherFragment : Fragment() {

    private var infoList = mutableListOf<ProfileOtherData>()
    private lateinit var profileOtherAdapter : ProfileOtherAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_other, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createRecyclerView(view)
    }

    private fun createRecyclerView(view : View){
        infoList.add(ProfileOtherData("instargram"))
        infoList.add(ProfileOtherData("facebook"))
        infoList.add(ProfileOtherData("github"))
        infoList.add(ProfileOtherData("blog"))


        profileOtherAdapter = ProfileOtherAdapter(view.context)
        profileOtherAdapter.data = infoList

        rcv_other_profile.apply {
            layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
            adapter = profileOtherAdapter
        }
        profileOtherAdapter.notifyDataSetChanged()
    }
}