package org.wandukong.app

import androidx.recyclerview.widget.ItemTouchHelper

interface ItemTouchListener{
    fun onDragDrop(fromPosition : Int, toPosition : Int) : Boolean
    fun onSwipe(position : Int)
}