package org.wandukong.seminar02

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SampleViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
    private val title : TextView = itemView.findViewById(R.id.tv_title)
    private val subTitle : TextView = itemView.findViewById(R.id.tv_subTitle)

    fun onBind(data : SampleData) {
        title.text = data.title
        subTitle.text = data.subTitle
    }
}