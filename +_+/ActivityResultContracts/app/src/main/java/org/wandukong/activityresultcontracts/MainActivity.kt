package org.wandukong.activityresultcontracts

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import org.wandukong.activityresultcontracts.databinding.ActivityMainBinding
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var getContent : ActivityResultLauncher<String>
    private lateinit var getStartActivityForResult: ActivityResultLauncher<Intent>
    private lateinit var requestPermission: ActivityResultLauncher<String>

    companion object{
        val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this
        initRegisterForActivityResult()
    }

    fun selectImage(){
        getContent.launch("image/*")
    }

    fun moveActivity(){
        val intent = Intent(baseContext, MoveActivity::class.java)
        intent.putExtra("name", "seungwan")
        getStartActivityForResult.launch(intent)
    }

    fun requestPermissionForCamera(){
        if(allPermissionGranted()){
            Toast.makeText(baseContext,"성공", Toast.LENGTH_SHORT).show()
            return
        }
        Toast.makeText(baseContext,"권한요청", Toast.LENGTH_SHORT).show()
        requestPermission.launch(android.Manifest.permission.CAMERA)
    }

    private fun allPermissionGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            applicationContext,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun initRegisterForActivityResult(){
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

        requestPermission = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ){
            if(it){
                Toast.makeText(baseContext,"성공", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(baseContext,"실패", Toast.LENGTH_SHORT).show()
            }
        }
    }
}