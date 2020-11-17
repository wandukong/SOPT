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