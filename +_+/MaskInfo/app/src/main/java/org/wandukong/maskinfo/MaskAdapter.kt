package org.wandukong.maskinfo

import android.graphics.MaskFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.wandukong.maskinfo.databinding.ItemMaskBinding

class MaskAdapter : RecyclerView.Adapter<MaskAdapter.VHolder>() {

    var data: MutableList<MaskData> = mutableListOf() // 뷰모델에 넣을 리스트 (리싸이클러뷰)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mask, parent, false)
        return VHolder(view)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun updateItems(newData: MutableList<MaskData>){
        data = newData
        notifyDataSetChanged()
    }

    inner class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding : ItemMaskBinding = DataBindingUtil.bind(itemView)!!
        fun onBind(data: MaskData){
            binding.data = data
        }
    }
}