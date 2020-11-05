package org.wandukong.app.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import org.wandukong.app.ItemTouchListener
import org.wandukong.app.R
import org.wandukong.app.TeamViewHolder
import org.wandukong.app.model.TeamData

class TeamAdapter (private val context : Context) : RecyclerView.Adapter<TeamViewHolder>(), ItemTouchListener {

    var data: MutableList<TeamData> = mutableListOf()
    lateinit var touchHelper : ItemTouchHelper
    var viewType = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        var view =  when(viewType){
            1 -> {
                LayoutInflater.from(context).inflate(R.layout.team_item_list, parent, false)
            }
            2 -> {
                LayoutInflater.from(context).inflate(R.layout.team_item_list2, parent, false)
            }
            else -> {
                LayoutInflater.from(context).inflate(R.layout.team_item_list, parent, false)
            }
        }
        return TeamViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun getItemCount(): Int = data.size

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.onBind(data[position])

        holder.dragIcon.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                touchHelper.startDrag(holder)
            }
            false
        }
    }

    override fun onDragDrop(fromPosition: Int, toPosition: Int) : Boolean{
        val moveData = data[fromPosition]
        data.removeAt(fromPosition)
        data.add(toPosition, moveData)

        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onSwipe(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }
}