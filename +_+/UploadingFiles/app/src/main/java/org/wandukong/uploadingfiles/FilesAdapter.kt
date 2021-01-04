package org.wandukong.uploadingfiles

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FilesAdapter(private val context : Context) : RecyclerView.Adapter<FilesViewHolder>(){

    var bmpData = mutableListOf<Bitmap>()
    var uriData = mutableListOf<Uri>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_picture, parent, false)
        return FilesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilesViewHolder, position: Int) {
        if(bmpData.size != 0) {
            holder.onBindForBMP(bmpData[position])
            Log.e("c",bmpData[position].toString());
        }
        else{
            holder.onBindForURI(uriData[position])
        }

        holder.btnDelete.setOnClickListener {
            if(bmpData.size != 0){
                bmpData.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position,bmpData.size)
            }else{
                uriData.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position,uriData.size)
            }
        }

    }
    override fun getItemCount(): Int = uriData.size

}