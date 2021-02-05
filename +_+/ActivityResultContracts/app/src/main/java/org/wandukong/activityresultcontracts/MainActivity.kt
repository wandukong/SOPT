package org.wandukong.activityresultcontracts

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import org.wandukong.activityresultcontracts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var getContent : ActivityResultLauncher<String>
    private lateinit var getStartActivityForResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.activity = this
        getContent  = registerForActivityResult(ActivityResultContracts.GetContent()) {
            binding.imageView.setImageURI(it)
        }
        getStartActivityForResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){ activityResult ->
            when(activityResult.resultCode){
                201 -> {
                    activityResult.data?.let{ intent ->
                        intent.extras?.let { bundle ->
                            Toast.makeText(this, "${bundle.getString("data","X")}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    fun selectImage(){
        getContent.launch("image/*")
    }

    fun moveActivity(){
        val intent = Intent(this, MoveActivity::class.java)
        intent.putExtra("name", "seungwan")
        getStartActivityForResult.launch(intent)
    }
}