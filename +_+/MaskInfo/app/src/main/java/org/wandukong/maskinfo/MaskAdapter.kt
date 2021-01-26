package org.wandukong.maskinfo

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.wandukong.maskinfo.databinding.ItemMaskBinding

class MaskAdapter : RecyclerView.Adapter<MaskAdapter.VHolder>() {

    var data: MutableList<ResponseStoreData.Store> = mutableListOf() // 뷰모델에 넣을 리스트 (리싸이클러뷰)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mask, parent, false)
        return VHolder(view)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun updateItems(newData: MutableList<ResponseStoreData.Store>){
        data = newData
        notifyDataSetChanged()
    }

    inner class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding : ItemMaskBinding = DataBindingUtil.bind(itemView)!!
        fun onBind(data: ResponseStoreData.Store){
            binding.data = data

            var remain = ""
            var count = ""
            var color = 0
            when(data.remain_stat){
                "plenty" -> {
                    remain = "충분"
                    count = "100개 이상"
                    color = Color.GREEN
                }
                "some" -> {
                    remain = "여유"
                    count = "30개 이상"
                    color = Color.YELLOW
                }
                "few" -> {
                    remain = "매진 임박"
                    count = "2개 이상"
                    color = Color.RED
                }
                "empty" -> {
                    remain = "재고 없음"
                    count = "1개 이하"
                    color = Color.GRAY

                }
            }
            binding.tvRemain.text = remain
            binding.tvRemain.setTextColor(color)
            binding.tvCount.text = count
            binding.tvCount.setTextColor(color)
        }
    }
}