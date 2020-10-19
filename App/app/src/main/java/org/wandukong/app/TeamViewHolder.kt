package org.wandukong.app

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TeamViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
    private val title : TextView = itemView.findViewById(R.id.tv_title)
    private val subTitle : TextView = itemView.findViewById(R.id.tv_subTitle)

    fun onBind(data : TeamData) {
        title.text = data.title
        subTitle.text = data.subTitle
    }
}