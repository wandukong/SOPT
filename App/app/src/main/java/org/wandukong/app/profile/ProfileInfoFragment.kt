package org.wandukong.app.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_profile_info.*
import org.wandukong.app.R
import org.wandukong.app.databinding.FragmentProfileInfoBinding

class ProfileInfoFragment : Fragment() {

    private lateinit var profileInfoAdapter : ProfileInfoAdapter
    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding : FragmentProfileInfoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_info, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ProfileViewModel() as T
            }
        }).get(ProfileViewModel::class.java)

        createRecyclerView()
    }

    private fun createRecyclerView(){
        profileInfoAdapter = ProfileInfoAdapter()
        rcv_info_profile.adapter = profileInfoAdapter

        viewModel.infoList.observe(requireActivity(), Observer {
            profileInfoAdapter.data = it
            profileInfoAdapter.notifyDataSetChanged()
        })
    }
}