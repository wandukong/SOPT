# 🎨ToolBar, StatusBar, Style

## 🥇ToolBar

### ToolBar 구현하기 
액티비티의 제목과 여러 종류의 위젯으로 구성된 기본 도구 모음을 **Action Bar**(=App Bar)라고 한다.  
안드로이드의 새로운 버전이 추가 되면서 버전에 따라 액션바가 다르게 작동하는 현상이 나타난다. 
구글에서 새로운 보완책으로 ‘파편화’와 ‘하위 호환성’을 제공하기 위해 Support Library를 사용하기 시작한다.
이 지원 라이브러리에 포함되어 있는 것이 바로 **ToolBar**이다. 

```xml
<!-- theme.xml -->
<!-- 기존 스타일을 제거하기 위해 NoActionBar로 설정 -->
<style name="Theme.Toolbar" parent="Theme.AppCompat.Light.NoActionBar">
```
```xml
<!-- Toolbar를 넣을 layout xml 파일 -->
<com.google.android.material.appbar.AppBarLayout  
	android:id="@+id/appbar_layout"  
	android:layout_width="match_parent"  
	android:layout_height="wrap_content"  
	android:elevation="0dp"  
	android:background="@null"  
	app:layout_constraintStart_toStartOf="parent"  
	app:layout_constraintTop_toTopOf="parent">  

	<androidx.appcompat.widget.Toolbar  
		android:id="@+id/draw_toolbar"  
		android:layout_width="match_parent"  
		android:layout_height="match_parent">  

	<!-- Toolbar 태그 안에 뷰 그룹 or 뷰를 넣을 수 있습니다. -->

	</androidx.appcompat.widget.Toolbar>  
  
</com.google.android.material.appbar.AppBarLayout>
```
```xml
<!-- menu.xml -->
<menu xmlns:android="http://schemas.android.com/apk/res/android"  
	xmlns:app="http://schemas.android.com/apk/res-auto">  
	<item  
		android:id="@+id/menu_search"  
		android:title="검색"  
		android:icon="@drawable/ic_baseline_search_24"  
		android:orderInCategory="100"  
		app:showAsAction="always"                   
	/>  
	<item  
		android:id="@+id/menu_share"  
		android:title="공유"  
		app:showAsAction="ifRoom"  
		android:icon="@drawable/ic_baseline_share_24"  
	/>  
	<item  
		android:id="@+id/menu_delete"  
		android:title="삭제"  
		android:icon="@drawable/ic_baseline_delete_24"  
		app:showAsAction="ifRoom"   
	/>  
</menu>

<!-- app:showAsAction 속성
	always : 메뉴를 항상 표시
	never : 오버플로우 메뉴로 표시
	ifRoom : ToolBar에 공간이 있으면 always,없으면 never 효과
	withText : 텍스트와 같이 표시. 단, 공간이 있을 경우
-->
```
 
```kotlin
setSupportActionBar(draw_toolbar) // toolbar id값을 넣어서 actionBar로 설정한다.
```

```kotlin
// 액티비티에서 onCreateOptionsMenu()를 오버라이드 해준다.
override fun onCreateOptionsMenu(menu: Menu?): Boolean {  
	// menuInflater를 이용해서 앞에서 만든 menu.xml을 ActionBar의 메뉴로 설정한다.
	menuInflater.inflate(R.menu.main_menu, menu)  
	return super.onCreateOptionsMenu(menu)  
}  
```

```kotlin
// onOptionsItemSelected()를 오버라이드하여 각 메뉴에 클릭 리스너를 설정 해줍니다.
override fun onOptionsItemSelected(item: MenuItem): Boolean {  
	when(item.itemId){  
		R.id.menu_search ->{  
			Toast.makeText(this, "search!!", Toast.LENGTH_SHORT).show()  
		}  
		R.id.menu_share ->{  
			Toast.makeText(this, "share!!", Toast.LENGTH_SHORT).show()  
		}  
		R.id.menu_delete ->{  
			Toast.makeText(this, "delete!!", Toast.LENGTH_SHORT).show()  
		} 
	}  
	return super.onOptionsItemSelected(item)  
}
```

#### AppBar투명하게 하기
background 속성을 @null로 설정해주면 된다.
```xml
<com.google.android.material.appbar.AppBarLayout  
	...
	android:background="@null"  
	...
/>
```

## 🥈Status Bar

#### Status Bar 하얀색 배경일 경우, 어두운 글자 색깔로 변경
```xml
<!-- theme.xml -->
<item name="android:windowLightStatusBar" tools:targetApi="m">true</item>
```
<img src="https://user-images.githubusercontent.com/47289479/102898638-25414e80-44ad-11eb-9ef5-a7b683a82564.JPG" width=400 height=100>

#### Status Bar 반투명
```xml
<!-- theme.xml -->
<item name="android:windowTranslucentStatus">true</item>
<item name="android:fitsSystemWindows">true</item> 			<!-- ToolBar와 StatusBar가 겹치지 않음 -->
``` 
<img src="https://user-images.githubusercontent.com/47289479/102898766-51f56600-44ad-11eb-802e-19da525201c8.JPG" width=400 height=100>

#### Status Bar 안보이게 하기
```kotlin
window.setFlags(  
	WindowManager.LayoutParams.FLAG_FULLSCREEN,  
	WindowManager.LayoutParams.FLAG_FULLSCREEN
)
``` 
<img src="https://user-images.githubusercontent.com/47289479/102898766-51f56600-44ad-11eb-802e-19da525201c8.JPG" width=400 height=100>


## 🥉Style

#### 전체 Activity 배경 지정하기
```xml
<!-- theme.xml 수정 -->
<!-- 이미지 혹은 색깔 -->
<item name="android:windowBackground">@drawable/daisy</item>
```