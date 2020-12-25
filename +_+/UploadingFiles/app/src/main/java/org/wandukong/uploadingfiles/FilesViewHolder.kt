package org.wandukong.uploadingfiles

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class FilesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    private val ivPictures = itemView.findViewById<ImageView>(R.id.iv_picture)
    val btnDelete = itemView.findViewById<ImageButton>(R.id.btn_delete)

    fun onBindForBMP(bmp: Bitmap) {
        ivPictures.setImageBitmap(bmp)
    }
    fun onBindForURI(uri: Uri) {
        ivPictures.setImageURI(uri)
    }
}