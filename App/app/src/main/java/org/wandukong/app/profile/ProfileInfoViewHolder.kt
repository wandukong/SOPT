package org.wandukong.app.profile

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.wandukong.app.R
import org.wandukong.app.profile.data.ProfileInfoData

class ProfileInfoViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
    var title : TextView = itemView.findViewById(R.id.tv_title_profileInfo)
    var content : TextView = itemView.findViewById(R.id.tv_content_profileInfo)
    var detail : TextView = itemView.findViewById(R.id.tv_detail_profileInfo)

    fun onBind(data : ProfileInfoData){
        title.text = data.title
        content.text = data.content
        detail.text = data.detail
    }

}