package org.wandukong.etc.room

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wandukong.etc.R

class PlanAdapter(private val context : Context) : RecyclerView.Adapter<PlanViewHolder>() {

    var data = listOf<Plan>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.list_plan, parent, false)
        return PlanViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
       holder.onBind(data[position])
    }

    override fun getItemCount() = data.size
}