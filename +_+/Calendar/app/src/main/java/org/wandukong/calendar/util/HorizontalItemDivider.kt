package org.wandukong.calendar.util

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class HorizontalItemDivider(private val divWidth: Int): RecyclerView.ItemDecoration(){
    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        outRect.right = divWidth
        outRect.left = divWidth
    }
}