package org.wandukong.app.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wandukong.app.R
import org.wandukong.app.search.data.SearchData

class SearchAdapter (private val context: Context) : RecyclerView.Adapter<SearchViewHolder>(){
    var data: MutableList<SearchData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.search_item_list, parent, false)

        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.onBind(data[position], context)
    }

    override fun getItemCount() = data.size
}