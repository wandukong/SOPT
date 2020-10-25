package org.wandukong.app

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class TeamAdapter (private val context : Context, private val touchHelper : ItemTouchHelper) : RecyclerView.Adapter<TeamViewHolder>() {

    var data: MutableList<TeamData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.project_item_list2, parent, false)
        return TeamViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.onBind(data[position])
        holder.move.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                touchHelper.startDrag(holder)
            }
            false
        }
    }
}