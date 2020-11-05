package org.wandukong.app

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.wandukong.app.model.ProfileInfoData

class ProfileInfoViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
    var title : TextView = itemView.findViewById(R.id.tv_title_profileInfo)
    var content : TextView = itemView.findViewById(R.id.tv_content_profileInfo)

    fun onBind(data : ProfileInfoData){
        title.text = data.title
        content.text = data.content
    }

}