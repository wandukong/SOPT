package org.wandukong.etc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_floating_button.*

class FloatingButton : AppCompatActivity() {

    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_floating_button)

        val rotateAnimOpen = AnimationUtils.loadAnimation(this, R.anim.floating_button_rotate_open)
        val rotateAnimClose = AnimationUtils.loadAnimation(this, R.anim.floating_button_rotate_close)
        val openAnim = AnimationUtils.loadAnimation(this, R.anim.floating_button_open)
        val closeAnim = AnimationUtils.loadAnimation(this, R.anim.floating_button_close)

        btn_floatingButton.setOnClickListener {
           if(clicked){
               btn_like.visibility = View.GONE
               btn_thumbUp.visibility = View.GONE
               tv_like.visibility = View.GONE
               tv_thumbUp.visibility = View.GONE
               btn_like.startAnimation(closeAnim)
               btn_thumbUp.startAnimation(closeAnim)
               tv_like.startAnimation(closeAnim)
               tv_thumbUp.startAnimation(closeAnim)
               btn_floatingButton.startAnimation(rotateAnimClose)
               clicked = false
           }else{
               btn_like.visibility = View.VISIBLE
               btn_thumbUp.visibility = View.VISIBLE
               tv_like.visibility = View.VISIBLE
               tv_thumbUp.visibility = View.VISIBLE
               btn_like.startAnimation(openAnim)
               btn_thumbUp.startAnimation(openAnim)
               tv_like.startAnimation(openAnim)
               tv_thumbUp.startAnimation(openAnim)
               btn_floatingButton.startAnimation(rotateAnimOpen)
               clicked = true
            }
        }
    }
}