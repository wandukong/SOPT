package org.wandukong.app

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TeamAdapter (private val context : Context) : RecyclerView.Adapter<TeamViewHolder>(){

    var data = mutableListOf<TeamData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.project_item_list, parent, false)
        return TeamViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) { // 데이터를 넣어준다.
        holder.onBind(data[position])
        holder.itemView.setOnClickListener {

            var intent = Intent(context, TeamDetailActivity::class.java)

            intent.putExtra("title", data[position].title)
            intent.putExtra("subTitle", data[position].subTitle)
            intent.putExtra("date", data[position].date)
            intent.putExtra("detail", data[position].detail)

            context.startActivity(intent)
        }
    }
}