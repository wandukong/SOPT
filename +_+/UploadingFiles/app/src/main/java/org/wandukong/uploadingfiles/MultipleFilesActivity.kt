package org.wandukong.uploadingfiles

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_multiple_files.*

class MultipleFilesActivity : AppCompatActivity() {

    companion object {
        const val IMAGE_PICK_CODE = 1000
        const val PERMISSION_CODE_IMAGE = 1001
        const val CAMERA_CAPTURE_CODE = 1002
        const val PERMISSION_CODE_CAMERA = 1003
    }
    private lateinit var filesAdapter : FilesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiple_files)

        picturesFromCamera()
        picturesFromGallery()

        filesAdapter = FilesAdapter(this)
        rcv_pictures.adapter = filesAdapter
    }

    private fun picturesFromCamera() {
        btn_camera.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
                    // permission denied
                    val permissions = arrayOf(Manifest.permission.CAMERA)
                    // show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE_CAMERA)
                }else{
                    takeImage()
                }
            }else{
                // OS is < Marshmallow
                takeImage()
            }
        }
    }

    private fun picturesFromGallery() {
        btn_add_photo.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    // permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    // show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE_IMAGE)
                }else{
                    selectImageFromGallery()
                }
            }else{
                // OS is < Marshmallow
                selectImageFromGallery()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_CODE_IMAGE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImageFromGallery()
                }else{ // permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
            PERMISSION_CODE_CAMERA -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takeImage()
                }else{ // permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun takeImage() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_CAPTURE_CODE)
    }

    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.type = "image/*"
        val chooserIntent = Intent.createChooser(intent, "Select Pictures")
        startActivityForResult(chooserIntent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {
            filesAdapter.bmpData.clear()
            filesAdapter.uriData.clear()
            when(requestCode){
                CAMERA_CAPTURE_CODE -> {
                    val bmp = data?.extras!!.get("data") as Bitmap
                    filesAdapter.bmpData.add(bmp)
                }
                IMAGE_PICK_CODE -> {
                    if (data?.clipData != null) {
                        val filesSize = data?.clipData!!.itemCount
                        if(filesSize > 10){
                            Toast.makeText(this, "최대 10개까지만 가능합니다.", Toast.LENGTH_SHORT).show()
                            for (i in 0 until 10) filesAdapter.uriData.add(data?.clipData!!.getItemAt(i).uri)
                        }
                        else{
                            for (i in 0 until filesSize) filesAdapter.uriData.add(data?.clipData!!.getItemAt(i).uri)
                        }
                    }
                    else {
                        return
                    }
                }
            }
            filesAdapter.notifyDataSetChanged()
        }
    }
}