
# 📷 ActivityResultContracts API 활용

## 💪준비 사항
**build.gradle(app)**
```xml
implementation "androidx.fragment:fragment-ktx:1.3.0-rc02"
```
 
## 👉StartActivityForResult()
기존에 액티비티간 이동할 때, 사용하던 방식인 startActivityForResult() 와 onActivityResult() 가 **deprecated**가 되었다.  이러한 이유로 **ActivityResultContracts API의 StartActivityForResult()** 를 사용한다.

**MainActivity**
```kotlin
private lateinit var getStartActivityForResult: ActivityResultLauncher<Intent>
```
```kotlin
getStartActivityForResult = registerForActivityResult( // API 정의
	ActivityResultContracts.StartActivityForResult()
){ activityResult ->
	when(activityResult.resultCode){
		201 -> {
			activityResult.data?.let{ intent ->
				intent.extras?.let { bundle ->
					Toast.makeText(this, 
						"${bundle.getString("data","X")}", 
						Toast.LENGTH_SHORT).show()
				}
			}
		}
	}
}
```
```kotlin
fun moveActivity(){  
  val intent = Intent(baseContext, MoveActivity::class.java)  
  intent.putExtra("name", "seungwan")  
  getStartActivityForResult.launch(intent)  // 정의한 API 실행
}
```

**MoveActivity**
```kotlin
val intent = Intent()
intent.putExtra("data","IU")
setResult(201, intent)
finish()
```

## 👉StartActivityForResult()
기존에 권한 요청을 할 때, 사용하던 방식인 requestPermissions() 와 onRequestPermissionsResult() 가 **deprecated**가 되었다.  이러한 이유로 **ActivityResultContracts API의 RequestPermission()** 를 사용한다.

```kotlin
private lateinit var requestPermission: ActivityResultLauncher<String>  
  
companion object{  
  val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)  
}
```
```kotlin
requestPermission = registerForActivityResult(   // API 정의
	ActivityResultContracts.RequestPermission()  
){  
	if(it){  
		Toast.makeText(baseContext,"성공", Toast.LENGTH_SHORT).show()  
	}else{  
		Toast.makeText(baseContext,"실패", Toast.LENGTH_SHORT).show()  
	}
}
```

```kotlin
fun requestPermissionForCamera(){
	if(allPermissionGranted()){
		Toast.makeText(baseContext,"성공", Toast.LENGTH_SHORT).show()
		return
	}
	Toast.makeText(baseContext,"권한요청", Toast.LENGTH_SHORT).show()
	requestPermission.launch(android.Manifest.permission.CAMERA) // API 실행
}
```

```kotlin
private fun allPermissionGranted() = REQUIRED_PERMISSIONS.all {
	ContextCompat.checkSelfPermission(
		applicationContext,
		it
	) == PackageManager.PERMISSION_GRANTED
}
```	