package org.wandukong.etc.room

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.wandukong.etc.R
class PlanViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    private val tvId = itemView.findViewById<TextView>(R.id.tv_id_plan)
    private val tvTitle = itemView.findViewById<TextView>(R.id.tv_tiltle_plan)
    private val tvDate = itemView.findViewById<TextView>(R.id.tv_data_plan)

    fun onBind(data : Plan){
        tvId.text = data.id?.plus(1).toString()
        tvTitle.text = data.title
        tvDate.text = data.date
    }
}