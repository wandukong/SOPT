# 📣Seminar04
작성일자 : 2020.11.17

## 🎨Drawable
### Shape Drawable
**간단한 도형**들은 소스코드를 통해 **직접 만드는 것**이 앱이 가벼워질 수 있는 장점이 있다.   
`<shape>` 태그를 사용하여 만든다.     
```xml
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="#3f3f3f"/>
    <corners android:radius="6dp"/>
</shape>
```
해당 shape drawble을 만들어서 button에 적용하면, 모서리가 둥근 직사각형 버튼을 만들 수 있다.    
<img src="https://user-images.githubusercontent.com/47289479/99347745-2cd46d00-28db-11eb-9197-2bfa104c2c80.JPG" width="150" height="70"/>
```xml
<Button
	android:id="@+id/btn_signup_login"
	android:layout_width="0dp"
	android:layout_height="wrap_content"
	android:background="@drawable/button"
	android:text="회원가입 하러가기"
	android:textColor="#ffffff"
	android:textSize="12sp"
	app:layout_constraintEnd_toEndOf="parent"
	app:layout_constraintStart_toStartOf="parent"
	app:layout_constraintTop_toBottomOf="@+id/btn_login_login" />
```

### StateList Drawble
**특정 상태**에 따른 drawable를 변경 할  수 있다.  
`<selector>` 태그를 사용하고, 그 안에 `<item>`를 사용하여 각 상태에 따른 drawable 속성을 지정해준다.   
**button_selector.xml**
```xml
<selector xmlns:android="http://schemas.android.com/apk/res/android">  
	<item android:drawable="@drawable/btn_blue" android:state_pressed="false"/>  
	<item android:drawable="@drawable/btn_red" android:state_pressed="true"/>  
</selector>
```
<p float="left">
	<img src="https://user-images.githubusercontent.com/47289479/99348471-01528200-28dd-11eb-9890-00ea8cc9411f.JPG" width="300" height="70"/>
	<img src="https://user-images.githubusercontent.com/47289479/99348522-22b36e00-28dd-11eb-921c-486ba5f54064.JPG" width="300" height="70"/>
</p>
① 클릭 전　　　　　　　　　　　　　②클릭 중    

```xml
<Button  
  android:id="@+id/btn_login_login"  
  android:layout_width="0dp"  
  android:layout_height="wrap_content"  
  android:layout_marginTop="80dp"  
  android:background="@drawable/button_selector"  
  android:text="Login"  
  android:textColor="#ffd500"  
  android:textSize="18sp"  
  app:layout_constraintEnd_toEndOf="@+id/et_pw_login"  
  app:layout_constraintStart_toStartOf="@+id/et_pw_login"  
  app:layout_constraintTop_toBottomOf="@+id/et_pw_login" />
```