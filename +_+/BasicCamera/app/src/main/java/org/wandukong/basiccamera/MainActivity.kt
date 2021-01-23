package org.wandukong.basiccamera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var currentPhotoPath: String
    companion object{
        const val REQUEST_TAKE_PHOTO = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_camera.setOnClickListener {
            picturesFromCamera()
        }
    }

    private fun picturesFromCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(
                    baseContext,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_DENIED){
                val permissions = arrayOf(android.Manifest.permission.CAMERA)
                // 권한 허가 요청
                requestPermissions(permissions, REQUEST_TAKE_PHOTO)
            }else{
                takePictureIntent()
            }
        }else{
            // OS is < Marshmallow
            takePictureIntent()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_TAKE_PHOTO -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePictureIntent()
                } else { // 권한 요청 거절
                    Toast.makeText(baseContext, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun takePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    Log.e("Error", ex.toString())
                    null
                }
                photoFile?.also {  // PhotoFile이 성공적으로 만들어진 경우
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "org.wandukong.basiccamera",  // provider의 authorities
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getOutputDirectory()
        return File.createTempFile(
            timeStamp,    // 파일명
            ".jpg", // 확장자
            storageDir // 저장경로
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            iv_camera.setImageURI(currentPhotoPath.toUri())
            galleryAddPic() // 기기에 사진 저장
        }
    }

    private fun galleryAddPic() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val file = File(currentPhotoPath)
            mediaScanIntent.data = Uri.fromFile(file)
            sendBroadcast(mediaScanIntent)
        }
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply {mkdirs()}
        }
        return if(mediaDir != null && mediaDir.exists())
            mediaDir
        else
            filesDir
    }
}