package org.wandukong.app.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wandukong.app.profile.ProfileOtherViewHolder
import org.wandukong.app.R
import org.wandukong.app.profile.ProfileOtherData

class ProfileOtherAdapter (private val context: Context) :RecyclerView.Adapter<ProfileOtherViewHolder>(){
    var data : MutableList<ProfileOtherData> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileOtherViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.profile_other_item_list, parent, false)
        return ProfileOtherViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileOtherViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount(): Int = data.size
}