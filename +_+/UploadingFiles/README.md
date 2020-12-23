# 📂Uploading my files.

## 📱결과 화면
<img src="https://user-images.githubusercontent.com/47289479/102919263-e242a380-44cb-11eb-9385-2c7b85deec96.gif" width=300 height=495/>

## 👶준비사항
```xml
<!-- AndroidManifest.xml -->
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />  
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />  
<uses-permission android:name="android.permission.CAMERA" />
```

## 👦 Build OS version & Permission Checking
```kotlin
btn_profile.setOnClickListener {  
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {  
		if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) 
		== PackageManager.PERMISSION_DENIED){  // permission 불허 
			 
			val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)  
			requestPermissions(permissions, PERMISSION_CODE)  // permission 허가 팝업창 띄우기
		}
		else{  // permission 허가
			selectImageFromGallery()  
		} 
	}
	else{  // OS is < Marshmallow  
		selectImageFromGallery()  
	}
 }
```

## 👱‍♂️onRequestPermissionsResult 
사용자에게 Permission 팝업창으로  요청을 보내고, 그에 대한 결과를  처리한다. 
```kotlin
override fun onRequestPermissionsResult(
		requestCode: Int,  
		permissions: Array<out String>,  
		grantResults: IntArray) 
{  
	when(requestCode){  
		PERMISSION_CODE -> {  
			if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) { 
				// 사용자가 permission 허가함
				selectImageFromGallery()  
			}else{  
				// 사용자가 permission 불허함
				Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()  
			}
		}
	}
}
 ```

## 👨 Intent
Intent 객체를 통해서 파일을 선택한다.
```kotlin
private fun selectImageFromGallery() {  
	val intent = Intent(Intent.ACTION_PICK)  
	intent.type = "image/*"  
	startActivityForResult(intent, IMAGE_PICK_CODE)  
}
```

## 👨‍🦳onActivityResult
파일 선택에 대한 결과를 처리한다.  
즉, 파일 선택 후, 이전 액티비티로 돌아와서 결과를 처리한다.  
```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {  
	super.onActivityResult(requestCode, resultCode, data)  
	if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {  
		iv_profile.setImageURI(data?.data)  
	}
}
 ```