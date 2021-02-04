package org.wandukong.activityresultcontracts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import org.wandukong.activityresultcontracts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var getContent : ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.activity = this
        getContent  = registerForActivityResult(ActivityResultContracts.GetContent()) {
            binding.imageView.setImageURI(it)
        }
    }

    fun selectImage(){
        getContent.launch("image/*")
    }
}