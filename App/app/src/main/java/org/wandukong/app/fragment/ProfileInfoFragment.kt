package org.wandukong.app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_profile_info.*
import kotlinx.android.synthetic.main.fragment_team.*
import org.wandukong.app.ItemTouchCallback
import org.wandukong.app.R
import org.wandukong.app.adapter.ProfileInfoAdapter
import org.wandukong.app.adapter.TeamAdapter
import org.wandukong.app.model.ProfileInfoData
import org.wandukong.app.model.TeamData

class ProfileInfoFragment : Fragment() {

    private var infoList = mutableListOf<ProfileInfoData>()
    private lateinit var profileInfoAdapter : ProfileInfoAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_profile_info, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createRecyclerView(view)
    }

    private fun createRecyclerView(view : View){
        infoList.add(ProfileInfoData("Age", "27", "숫자에 불과해"))
        infoList.add(ProfileInfoData("Birthday", "6, July", "축하해주세요"))
        infoList.add(ProfileInfoData("Residence", "Ilsan", "23년째 사는 중이에요"))
        infoList.add(ProfileInfoData("Instagram", "_sxxngwxn", "팔로우 해주세요"))
        infoList.add(ProfileInfoData("Github", "wandukong", "팔로우 해주세요"))
        infoList.add(ProfileInfoData("Part", "Android", "안드로이드 파트 짱"))
        infoList.add(ProfileInfoData("Group", "E","E조 모이자"))

        profileInfoAdapter = ProfileInfoAdapter(view.context)
        profileInfoAdapter.data = infoList

        rcv_info_profile.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = profileInfoAdapter
        }
        profileInfoAdapter.notifyDataSetChanged()
    }
}