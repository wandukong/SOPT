package org.wandukong.app

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TeamViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
    val title : TextView = itemView.findViewById(R.id.tv_title_teamList)
    val subTitle : TextView = itemView.findViewById(R.id.tv_subTitle_teamList)
    val move: ImageView = itemView.findViewById(R.id.iv_drag_teamList)

    fun onBind(data : TeamData) {
        title.text = data.title
        subTitle.text = data.subTitle

        itemView.setOnClickListener {
            var intent = Intent(itemView.context, TeamDetailActivity::class.java)

            intent.putExtra("title", data.title)
            intent.putExtra("subTitle", data.subTitle)
            intent.putExtra("date", data.date)
            intent.putExtra("detail", data.detail)

            itemView.context.startActivity(intent)
        }
    }
}