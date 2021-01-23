
# 📷 기본 카메라로 사진 업로드하기
 
## 📱결과 화면
<img src="https://user-images.githubusercontent.com/47289479/105569177-db38da80-5d82-11eb-9ea4-32c3923329c0.gif" width="300" height="495"/>  

## 👨‍🔧준비사항
**AndroidManifest.xml**
```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-feature android:name="android.hardware.camera.any" android:required="true" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

<provider
	android:name="androidx.core.content.FileProvider"
	android:authorities="org.wandukong.basiccamera"
	android:exported="false"
	android:grantUriPermissions="true">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/file_paths" />
</provider>

<queries>
	<intent>
		<action android:name="android.media.action.IMAGE_CAPTURE" />
	</intent>
	<intent>
		<action android:name="android.intent.action.GET_CONTENT" />
	</intent>
	<intent>
		<action android:name="android.intent.action.MEDIA_SCANNER_SCAN_FILE"/>
	</intent>
</queries>
```
**file_paths.xml**
```xml
<?xml version="1.0" encoding="utf-8"?>
<paths xmlns:android="http://schemas.android.com/apk/res/android">
    <external-path name="storage/emulated/0" path="."/>
</paths>
```

## 👨‍✈️권한 확인
```kotlin
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
```

```kotlin
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
```

## 👨‍🎨사진 찍기
```kotlin
private fun takePictureIntent() {  
	Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->  
		takePictureIntent.resolveActivity(packageManager)?.also {  
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
```
```kotlin
@Throws(IOException::class)  
private fun createImageFile(): File {  
	val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())  
	val storageDir: File? = getOutputDirectory()  
	return File.createTempFile(  
		timeStamp, // 파일명
		".jpg",    // 확장자
		storageDir // 저장경로
	).apply {  
		currentPhotoPath = absolutePath  // 사진 절대 경로 변수에 저장
	}  
}
```
```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {  
	super.onActivityResult(requestCode, resultCode, data)  
	if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {  
		iv_camera.setImageURI(currentPhotoPath.toUri())  
		galleryAddPic()  // 기기에 사진 저장
	}
}
```

## 👨‍💻사진 저장
```kotlin
private fun galleryAddPic() {  
	Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->  
		val file = File(currentPhotoPath)  
		mediaScanIntent.data = Uri.fromFile(file)  
		sendBroadcast(mediaScanIntent)  
	}  
}  
```
```kotlin
private fun getOutputDirectory(): File {  
	val mediaDir = externalMediaDirs.firstOrNull()?.let {  
		File(it, resources.getString(R.string.app_name)).apply {mkdirs()}  
	}  
	return if(mediaDir != null && mediaDir.exists())  
		mediaDir  
	else  
		filesDir  
}
```