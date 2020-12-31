package org.wandukong.app.search

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.annotations.NotNull
import org.wandukong.app.R
import org.wandukong.app.search.data.SearchData

class SearchViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val title : TextView = itemView.findViewById(R.id.tv_title_web)
    private val date : TextView = itemView.findViewById(R.id.tv_date_web)
    private val contents : TextView = itemView.findViewById(R.id.tv_contents_web)

    @RequiresApi(Build.VERSION_CODES.O)
    fun onBind(@NotNull data: SearchData, context: Context){
        title.text = data.title
        contents.text = data.contents
        date.text = data.datetime

        itemView.setOnClickListener {
            var url = Intent(Intent.ACTION_VIEW, Uri.parse(data.url))
            context.startActivity(url)
        }
    }
}