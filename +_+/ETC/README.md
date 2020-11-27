# 🗡⚔🏹💣🛡🔨🪓🔧

## 🖼ShapeableImageView
<img src="https://user-images.githubusercontent.com/47289479/99341474-4c649900-28cd-11eb-8601-208e69a6493b.JPG" width="300" height="400"/>

Library 사용없이, Google에서 제공하는 material 컴포넌트이다.  
ImageView를 원하는 모양으로 만들 수 있다.  
```xml
<com.google.android.material.imageview.ShapeableImageView  
  android:id="@+id/imvCircularWithStroke"  
  android:layout_width="120dp"  
  android:layout_height="120dp"  
  android:layout_marginTop="8dp"  
  android:src="@drawable/charlie"  
  app:layout_constraintEnd_toEndOf="parent"  
  app:layout_constraintHorizontal_bias="0.5"  
  app:layout_constraintStart_toEndOf="@+id/imvCircular"  
  app:layout_constraintTop_toBottomOf="@id/txvCircularShape"  
  app:shapeAppearanceOverlay="@style/iv_circle"　// imageView에 적용할 모양 xml
  app:strokeColor="#00BCD4"　　　　　　　　　　　　　// 테두리 색깔
  app:strokeWidth="5dp"　　　　　　　　　　　　　　　// 테두리 두께
  android:padding="5dp"　　　　// 테두리 두께 만큼 padding 값을 넣어야 image가 깨지지 않는다. 
  />						
```
**custom_style.xml**
```xml
<?xml version="1.0" encoding="utf-8"?>  
<resources>  
	<style name="iv_circle">  
		<item name="cornerSize">50%</item>  
	 </style>  
	 <style name="iv_rounded_square">  
		 <item name="cornerSize">10%</item>  
	 </style>  
	<style name="iv_corner_cut">  
		<item name="cornerSize">15dp</item>  
		<item name="cornerFamily">cut</item>  
	</style>  
	<style name="iv_diamond_cut">  
		<item name="cornerSize">75dp</item>  
		<item name="cornerFamily">cut</item>  
	</style>  
	<style name="iv_specific_cut">  
		<item name="cornerSizeTopRight">75dp</item>  
		<item name="cornerFamilyTopRight">cut</item>  
		<item name="cornerSizeBottomLeft">75dp</item>  
		<item name="cornerFamilyBottomLeft">cut</item>  
	</style>  
	<style name="iv_specific_rounded">  
		<item name="cornerSizeTopRight">75dp</item>  
		<item name="cornerFamilyTopRight">rounded</item>  
		<item name="cornerSizeBottomLeft">75dp</item>  
		<item name="cornerFamilyBottomLeft">rounded</item>  
	</style>
</resources>
 ```

## ⌨입력창에서 focus를 잃으면, 키보드 숨기기
editText에다가 입력 중, 화면 다른 곳을 클릭 할 때 키보드를 자동으로 숨겨준다.  
dispatchTouchEvent 메소드를 아래와 같이 override를 해준다.
```kotlin
override fun dispatchTouchEvent(ev: MotionEvent?): Boolean { //  
  if (currentFocus != null) {  
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager  
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)  
    }  
    return super.dispatchTouchEvent(ev)  
}
```

## ✨클릭 시 Button 속성 변경하기
Button을 눌렀을 때 Button의 Background, text 색깔 혹은 font를 바꾸고 싶은 경우,    
아래와 같이 코드를 작성하면 된다.   
```kotlin
//변수에 font 불러오기 -> ResourcesCompat.getFont(context, resource)
val applesdgothicBold = ResourcesCompat.getFont(view.context, R.font.applesdgothic_bold)   
val applesdgothicRegular = ResourcesCompat.getFont(view.context, R.font.applesdgothic_regular)  

button.setOnClickListener {  
	if(buttonCheck){  
	          button.setBackgroundResource(R.drawable.pick_unclick)　// background drawable 변경
	          button.setTextColor(Color.parseColor("#929292"))　　　　// text color 변경
	          button.typeface = applesdgothicRegular　　　　　　　　　　// font 변경
	          buttonCheck = false  // 전역 변수

	}else{  
	          button.setBackgroundResource(R.drawable.pick_onclick)  
	          button.setTextColor(Color.parseColor("#ffffff"))  
	          button.typeface = applesdgothicBold  
	          buttonCheck = true  
	}  
}
```

## Floating Button
<img src="https://user-images.githubusercontent.com/47289479/100472628-f7702080-311f-11eb-9781-b6193373a339.gif" width="300" height="495"/>
Layout 위에 붕 떠있는 것 같은 Button을 Floating Button이라고 합니다.  
```xml
<com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/btn_floatingButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
		app:fabSize="normal"　　　　　　　　　　　　　// 버튼 크기
        android:backgroundTint="#2A77B5"　　　　　　// 배경색
		android:elevation="10dp"　　　　　　　　　　　// 떠있는 높이
        app:pressedTranslationZ="10dp"　　　　　　　// 버튼 클릭시 그림자 크기
        android:clickable="true"								
        android:focusable="true"
        android:src="@drawable/ic_baseline_add_24"
 />
```
### 구현
floating button 구현 xml  
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
	xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".FloatingButton">

    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/btn_floatingButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginEnd="30dp"
        android:layout_marginBottom="40dp"
        android:backgroundTint="#2A77B5"
        android:elevation="10dp"
        app:pressedTranslationZ="10dp"
        android:src="@drawable/ic_baseline_add_24"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

    <TextView
        android:id="@+id/tv_like"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginEnd="5dp"
        android:text="좋아요"
        app:layout_constraintBottom_toBottomOf="@+id/btn_like"
        app:layout_constraintEnd_toStartOf="@+id/btn_like"
        app:layout_constraintTop_toTopOf="@+id/btn_like"
        android:visibility="gone"/>

    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/btn_like"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginBottom="25dp"
        android:src="@drawable/ic_baseline_favorite_24"
        app:backgroundTint="#FF0C91"
        app:layout_constraintBottom_toTopOf="@+id/btn_floatingButton"
        app:layout_constraintEnd_toEndOf="@+id/btn_floatingButton"
        android:visibility="gone"/>

    <TextView
        android:id="@+id/tv_thumbUp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginEnd="5dp"
        android:text="최고에요"
        app:layout_constraintBottom_toBottomOf="@+id/btn_thumbUp"
        app:layout_constraintEnd_toStartOf="@+id/btn_thumbUp"
        app:layout_constraintTop_toTopOf="@+id/btn_thumbUp"
        android:visibility="gone"/>

    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/btn_thumbUp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginBottom="25dp"
        android:backgroundTint="#FFEB3B"
        android:src="@drawable/ic_baseline_thumb_up_24"
        app:layout_constraintBottom_toTopOf="@+id/btn_like"
        app:layout_constraintStart_toStartOf="@+id/btn_like"
        android:visibility="gone"/>
</androidx.constraintlayout.widget.ConstraintLayout>
```
floating button 클릭 시, 새로운 하위 floating button 나타나는 애니메이션
```xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:fillAfter="true">
    <translate
        android:duration="300"
        android:fromYDelta="100%"
        android:toYDelta="0%"/>
    <scale
        android:pivotX="50%"
        android:pivotY="50%"
        android:toXScale="0.8"
        android:toYScale="0.8" />
    <alpha
        android:duration="800"
        android:fromAlpha="0"
        android:toAlpha="100" />
</set>
```
floating button 클릭 시, 하위 floating button 사라지는 애니메이션  
```xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:fillAfter="true">
    <translate
        android:duration="300"
        android:fromYDelta="0%"
        android:toYDelta="100%"/>
    <scale
        android:pivotX="50%"
        android:pivotY="50%"
        android:fromXScale="0.8"
        android:fromYScale="0.8"
        android:toXScale="0.8"
        android:toYScale="0.8" />
    <alpha
        android:duration="150"
        android:fromAlpha="1"
        android:toAlpha="0" />
</set>
```
하위 floating button 나타날때, 고정 floating button x 모양으로 돌리기
```xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:fillAfter="true">
    <rotate
        android:fromDegrees="0"
        android:pivotX="50%"
        android:pivotY="50%"
        android:toDegrees="45"
        android:duration="300"/>
</set>
```
하위 floating button 나타날때, 고정 floating button + 모양으로 되돌리기
```xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:fillAfter="true">
    <rotate
        android:fromDegrees="45"
        android:pivotX="50%"
        android:pivotY="50%"
        android:toDegrees="0"
        android:duration="300"/>
</set>
```
floating button에 애니메이션 설정 및 클릭 이벤트 설정  
```kotlin
private var clicked = false

// 애니메이션 불러오기
val rotateAnimOpen = AnimationUtils.loadAnimation(this, R.anim.floating_button_rotate_open)
val rotateAnimClose = AnimationUtils.loadAnimation(this, R.anim.floating_button_rotate_close)
val openAnim = AnimationUtils.loadAnimation(this, R.anim.floating_button_open)
val closeAnim = AnimationUtils.loadAnimation(this, R.anim.floating_button_close)

btn_floatingButton.setOnClickListener {
   if(clicked){
	   btn_like.visibility = View.GONE       // view 숨기기
	   btn_thumbUp.visibility = View.GONE
	   tv_like.visibility = View.GONE
	   tv_thumbUp.visibility = View.GONE
	   btn_like.startAnimation(closeAnim)    // animation 설정
	   btn_thumbUp.startAnimation(closeAnim)
	   tv_like.startAnimation(closeAnim)
	   tv_thumbUp.startAnimation(closeAnim)
	   btn_floatingButton.startAnimation(rotateAnimClose)
	   clicked = false
   }else{
	   btn_like.visibility = View.VISIBLE     // view 보이기
	   btn_thumbUp.visibility = View.VISIBLE
	   tv_like.visibility = View.VISIBLE
	   tv_thumbUp.visibility = View.VISIBLE
	   btn_like.startAnimation(openAnim)      // animation 설정
	   btn_thumbUp.startAnimation(openAnim)
	   tv_like.startAnimation(openAnim)
	   tv_thumbUp.startAnimation(openAnim)
	   btn_floatingButton.startAnimation(rotateAnimOpen)
	   clicked = true
	}
}
```