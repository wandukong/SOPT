package org.wandukong.seminar02

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SampleAdapter (private val context : Context) : RecyclerView.Adapter<SampleViewHolder>(){

    var data = mutableListOf<SampleData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.sample_item_list, parent, false)
        return SampleViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        holder.onBind(data[position]) // 데이터를 넣어준다.
    }
}