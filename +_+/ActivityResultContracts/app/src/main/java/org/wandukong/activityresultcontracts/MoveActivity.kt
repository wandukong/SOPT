package org.wandukong.activityresultcontracts

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import org.wandukong.activityresultcontracts.databinding.ActivityMoveBinding

class MoveActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMoveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_move)
        binding.activity = this

        Toast.makeText(this, "${intent.getStringExtra("name")}", Toast.LENGTH_SHORT).show()
    }

    fun returnActivity(){
        val intent = Intent()
        intent.putExtra("data","IU")
        setResult(201, intent)
        finish()
    }
}