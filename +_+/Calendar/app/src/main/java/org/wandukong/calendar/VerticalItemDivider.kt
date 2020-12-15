package org.wandukong.calendar

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class VerticalItemDivider(private val divHeight: Int): RecyclerView.ItemDecoration(){
    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        outRect.bottom = divHeight
        outRect.top = divHeight
    }
}