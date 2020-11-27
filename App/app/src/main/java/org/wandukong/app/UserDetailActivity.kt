package org.wandukong.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_user_detail.*
import org.wandukong.app.model.UserData

class UserDetailActivity : AppCompatActivity() {

    private lateinit var user : UserData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        user = intent.getParcelableExtra("user")
        tv_name_userDetail.text = user.name
        tv_email_userDetail.text= user.email

        Glide.with(this).load(user.imageSrc).into(iv_image_userDetail)
    }
}