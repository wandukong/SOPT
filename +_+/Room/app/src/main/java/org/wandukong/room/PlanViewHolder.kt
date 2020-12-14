package org.wandukong.room

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlanViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    private val tvTitle = itemView.findViewById<TextView>(R.id.tv_tiltle_plan)
    private val tvDate = itemView.findViewById<TextView>(R.id.tv_data_plan)
    val btnDelete = itemView.findViewById<ImageButton>(R.id.btn_delete_plan)

    fun onBind(data : Plan){
        tvTitle.text = data.title
        tvDate.text = data.date
    }
}