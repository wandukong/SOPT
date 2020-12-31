package org.wandukong.app.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wandukong.app.R
import org.wandukong.app.profile.data.ProfileInfoData

class ProfileInfoAdapter (private val context: Context) : RecyclerView.Adapter<ProfileInfoViewHolder>() {

    var data: MutableList<ProfileInfoData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileInfoViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.profile_info_item_list, parent, false)
        return ProfileInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileInfoViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount(): Int = data.size
}