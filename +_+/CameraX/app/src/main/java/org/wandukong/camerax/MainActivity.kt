package org.wandukong.camerax

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    private var preview: Preview? =null
    private var imageCapture: ImageCapture? = null
    private var camera: Camera? = null

    private lateinit var outputDirectory: File

    companion object{
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA,)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_camera.setOnClickListener {
            takePhoto()
        }
        
        if(allPermissionsGranted()){
            startCamera()
        }else{
            ActivityCompat.requestPermissions(
                this,  REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
        outputDirectory = getOutputDirectory()
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == REQUEST_CODE_PERMISSIONS){
            if(allPermissionsGranted()){
                startCamera()
            }else{
                Toast.makeText(baseContext, "No Permission granted!!!",Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all{
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            preview = Preview.Builder()
                .build()

            imageCapture = ImageCapture.Builder()
                .build()

            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()

            try{
                cameraProvider.unbindAll()
                camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
                preview?.setSurfaceProvider(prv_camera.createSurfaceProvider())
            } catch (e: Exception){
                Log.e("MAINACTIVITY", "Use case binding failed", e)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture?:return
        val file = File(outputDirectory, System.currentTimeMillis().toString() + ".jpg")

        val outputOptions: ImageCapture.OutputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()

        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(baseContext), object: ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val savedUri = Uri.fromFile(file)
                val msg = "Photo capture succeeded: \n\n $savedUri"
                Log.e("MAINACTIVITY",msg)

                Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
                    mediaScanIntent.data = savedUri
                    sendBroadcast(mediaScanIntent)
                }
            }
            override fun onError(exception: ImageCaptureException) {
                Log.e("MAINACTIVITY", "Photo Capture failed ${exception.message}", exception)
            }
        })
    }
}