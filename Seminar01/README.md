
# 📣Seminar01
 
 **1. Layout 종류**    
 **2. ViewGroup & View**   
 **3. startActivity & startActivityForResult**    
 **4. SharedPreferences** 
 
## 📱결과 화면

<p float="left">
	<img src="https://user-images.githubusercontent.com/47289479/95879064-e6a65e00-0db0-11eb-9310-0af0c0fe6dbf.gif" width="300" height="495"/>
	<img src="https://user-images.githubusercontent.com/47289479/95878606-608a1780-0db0-11eb-9eaa-1e7f3ab470d5.gif" width="300" height="495"/>
</p>
①회원가입 및 로그인　　　　　　　　　　②자동 로그인 및 로그아웃  

## 🍭view에서 text 읽기 및 쓰기

**읽기**  
view.text.toString() : view로부터 text속성의 값을 불러올 수 있다.
```kotlin
et_id_login.text.toString()
```
**쓰기**  
view.setText() : view의 text속성의 값을 지정할 수 있다.
```kotlin
et_id_login.setText(data?.getStringExtra("email"))
```

## 🍜startActivity()
startActivity는 이동하려는 Activity를 호출한다.  
데이터를 이동하려는 Activity로 보낼수는 있지만, 이동하려는 Activity에서 데이터를 받을 수는 없다.  
즉, 단방향으로 데이터를 전송하고 싶을 때 사용한다.  
```kotlin
// MainActivity
var intent =  Intent(this, HomeActivity::class.java)
intent.putExtra("autoLogin",true) 
startActivity(intent)
```
```kotlin
// SignupActivity
if(getIntent().getBooleanExtra("autoLogin",false)){  
    Toast.makeText(this,"${member.getString("*LATEST*","")} 자동 로그인",Toast.LENGTH_SHORT).show() 
}
```

## 🍣startActivityForResult()
startActivity는 이동하려는 Activity를 호출하고, 이동한 Activity으로부터 result를 받는다.  
Activity 간 데이터를 주고 받을 수 있다.   
즉, 데이터를 양방향으로 전송하고 싶을 때 사용한다.  
```kotlin
// MainActivity
const val REQUEST_SIGNUP = 201;

var intent =  Intent(this, HomeActivity::class.java)
intent.putExtra("autoLogin",false)  
startActivityForResult(intent, REQUEST_LOGIN)
```
```kotlin
// SignupActivity
val intent = Intent()  
intent.putExtra("id", member.getString("*LATEST*",""))  
setResult(Activity.RESULT_OK, intent)
finish() // finish()로 return한다.
```
호출한 Activity로부터 받은 데이터는 onActivityResult()를 override해서 구현한다.  
```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {  
    super.onActivityResult(requestCode, resultCode, data)  
  
    if(resultCode == Activity.RESULT_OK){  
        when(requestCode){  
            REQUEST_LOGIN->{ // 로그아웃 - 홈에서 로그아웃할 때, MainActivity로 되돌아온다. 
  Toast.makeText(this,"${data?.getStringExtra("id")} 로그아웃 성공",Toast.LENGTH_SHORT).show()  
            }  
        }  
    }  
}
```

## 🍻SharedPreferences
간단한 Database를 만들고 싶을 때 사용한다.   
SharedPreferences는 App의 저장소에 xml파일을 만들어, 데이터를 저장할 수 있게 해준다.   
데이터는 Key/Value 형식으로 저장이 된다.  
https://developer.android.com/reference/android/content/SharedPreferences  

### 객체 생성
```kotlin
private lateinit var member: SharedPreferences 
member = getSharedPreferences("memberDB", MODE_PRIVATE) // "memberDB"라는 파일을 private모드로 SharedPreferences객체를 생성한다.
```
### 데이터 저장
putDATATYPE(key, value)     
```kotlin
val preferencesEditor: SharedPreferences.Editor = member.edit()  // Editor interface를 만들어 xml파일을 수정한다.
preferencesEditor.putString(et_id_signup.text.toString(),et_pw_signup.text.toString()) // value가 string인 넣기
preferencesEditor.commit() // commit을 해줘야 적용된다.
```
### 데이터 삭제
remove(key)  
```kotlin
val preferencesEditor: SharedPreferences.Editor = member.edit()  
preferencesEditor.remove("*LATEST*")  // *LATEST*라는 키를 가진 데이터를 삭제한다.
preferencesEditor.commit()
```
clear()  
```kotlin
preferencesEditor.clear() // 모든 데이터 삭제
```
### 데이터 검색
contains(key)  
```kotlin
if(member.contains("*LATEST*")){  // *LATEST*라는 키를 가진 데이터가 있는지 확인한다.
    loginIntent.putExtra("autoLogin",true)  
    startActivityForResult(loginIntent, REQUEST_LOGIN)  
}
```
