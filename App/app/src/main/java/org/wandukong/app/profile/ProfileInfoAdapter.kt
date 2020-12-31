package org.wandukong.app.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.wandukong.app.BR
import org.wandukong.app.R
import org.wandukong.app.databinding.ProfileInfoItemListBinding
import org.wandukong.app.profile.data.ProfileInfoData

class ProfileInfoAdapter : RecyclerView.Adapter<ProfileInfoAdapter.VHolder>() {

    var data: MutableList<ProfileInfoData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.profile_info_item_list, parent, false)
        return VHolder(view)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class VHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val binding : ProfileInfoItemListBinding = DataBindingUtil.bind(itemView)!!
        fun onBind(data : ProfileInfoData){
            binding.setVariable(BR.info, data)
        }
    }
}