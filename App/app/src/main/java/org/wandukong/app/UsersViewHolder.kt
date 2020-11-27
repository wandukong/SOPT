package org.wandukong.app

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.jetbrains.annotations.NotNull
import org.wandukong.app.model.UserData

class UsersViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
    private val name : TextView =  itemView.findViewById(R.id.tv_name_userList)
    private val email: TextView =  itemView.findViewById(R.id.tv_email_userList)
    private val image : ImageView = itemView.findViewById(R.id.iv_image_userList)
    val dragIcon : ImageView = itemView.findViewById(R.id.iv_drag_userList)

    fun onBind(@NotNull data: UserData) {
        name.text = data.name
        email.text = data.email
        Glide.with(itemView).load(data.imageSrc).placeholder(R.drawable.charlie).error(R.drawable.charlie).into(image)

        itemView.setOnClickListener {
            val intent = Intent(itemView.context, UserDetailActivity::class.java)
            intent.putExtra("user", data)
            itemView.context.startActivity(intent)
        }
    }
}