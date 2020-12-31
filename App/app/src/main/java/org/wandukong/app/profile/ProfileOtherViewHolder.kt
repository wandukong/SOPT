package org.wandukong.app.profile
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.wandukong.app.R

class ProfileOtherViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
    var image : ImageView = itemView.findViewById(R.id.iv_image_profileOther)
    var title : TextView = itemView.findViewById(R.id.tv_title_profileOther)

    fun onBind(data : ProfileOtherData){
        title.text = data.title
        when(data.title){
            "instargram"->{
                image.setBackgroundResource(R.drawable.ic_instagram)
            }
            "facebook" ->{
                image.setBackgroundResource(R.drawable.ic_facebook)
            }
            "github"->{
                image.setBackgroundResource(R.drawable.ic_github)
            }
            "blog"->{
                image.setBackgroundResource(R.drawable.ic_blog)
            }
        }

    }

}