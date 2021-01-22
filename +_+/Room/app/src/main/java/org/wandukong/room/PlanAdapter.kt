package org.wandukong.room

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PlanAdapter(private val context: Context, private val planViewModel: MainViewModel) : RecyclerView.Adapter<PlanViewHolder>() {

    var data = mutableListOf<Plan>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.list_plan, parent, false)
        return PlanViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        holder.onBind(data[position])

        holder.btnDelete.setOnClickListener {
            planViewModel.deletePlan(data[position])
        }
    }

    override fun getItemCount() = data.size
}