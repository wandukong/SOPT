package org.wandukong.app.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wandukong.app.profile.data.ProfileInfoData

class ProfileViewModel : ViewModel() {

    private val _infoList = MutableLiveData<ArrayList<ProfileInfoData>>()
    val infoList: LiveData<ArrayList<ProfileInfoData>>
        get() = _infoList

    fun setInfoList(infoList : ArrayList<ProfileInfoData>){
        _infoList.value = infoList
    }

    init{
        var profileInfoData = arrayListOf<ProfileInfoData>()
        profileInfoData.add(ProfileInfoData("Age", "27", "숫자에 불과해"))
        profileInfoData.add(ProfileInfoData("Birthday", "6, July", "축하해주세요"))
        profileInfoData.add(ProfileInfoData("Residence", "Ilsan", "23년째 사는 중이에요"))
        profileInfoData.add(ProfileInfoData("Instagram", "_sxxngwxn", "팔로우 해주세요"))
        profileInfoData.add(ProfileInfoData("Github", "wandukong", "팔로우 해주세요"))
        profileInfoData.add(ProfileInfoData("Part", "Android", "안드로이드 파트 짱"))
        profileInfoData.add(ProfileInfoData("Group", "E","E조 모이자"))
        setInfoList(profileInfoData)
    }
}