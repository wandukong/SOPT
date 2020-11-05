package org.wandukong.app

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.annotations.NotNull
import org.wandukong.app.model.TeamData

class TeamViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
    private val title : TextView =  itemView.findViewById(R.id.tv_title_teamList)
    private val subTitle: TextView =  itemView.findViewById(R.id.tv_subTitle_teamList)
    val dragIcon : ImageView = itemView.findViewById(R.id.iv_drag_teamList)

    fun onBind(@NotNull data : TeamData) {
        title.text = data.title
        subTitle.text = data.subTitle

        itemView.setOnClickListener {
            val intent = Intent(itemView.context, TeamDetailActivity::class.java)
            intent.putExtra("team", data)
            itemView.context.startActivity(intent)
        }
    }
}