# 📣Seminar03
**작성일자 : 2020.11.05**   
**1. Fragment**      
**2. View Pager**  
**3. Bottom Navigation**  
**4. Tab Layout**  
 
## 📱결과 화면
<p float="left">
	<img src="https://user-images.githubusercontent.com/47289479/98323301-d9396800-202c-11eb-8d2b-580823236c0f.gif" width="300" height="495"/>
</p>
BottomNavigationView & TabLayout　　　　　　　

## 🍇Fragment
Fragment는 하나의 액티비티가 **여러 개의 화면**을 가지도록 만들기 위해 고안된 개념이다.  
액티비티를 분할하여 화면의 한 부분을 정의한다.   
액티비티와 같이 **레이아웃, 동작 처리, 생명주기를 가지는 독립적인 모듈**이다.   
다른 액티비티에서도 사용 할 수 있어 **재사용성**이 뛰어나다.   
액티비티 내에서 실행 중에 추가, 제거, 변경이 가능하다.  
Activity에서 Fragment를 관리하기 위해서 **Fragment Manager**가 필요하다.  

#### Fragment Transaction
Fragment Manager의 역할 중 하나이다.   
Activity에서 Fragment 를 추가, 변경, 삭제 작업을 수행 한다.   
수행한 transaction의 상태를 BackStack에 저장 가능하다.    
Fragment 전환 애니메이션 설정이 가능하다.    

```kotlin
// supportFragmentManager 클래스를 사용하여 새 Fragment를 추가한다. 
// beginTransaction() : Fragment의 편집 기능들을 시작한다.
supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment1).commit()  
  
// Fragment를 교체한다. 
val transaction = supportFragmentManager.beginTransaction()
transaction.replace(R.id.fragment_container,fragment2)  
```

#### Fragement에서 또 하나의 View Pager 만들기
Activity에서 View Pager 인스턴스를 만들 경우, Fragment Manager 클래스로 **supportFragmentManager**를 보냈다.    
```kotlin
viewPagerAdapter = HomeViewPagerAdapter(supportFragmentManager)
```
하지만, Fragment에서 View Pager 인스턴스를 만들 경우, Fragment Manager 클래스로 **childFragmentManager** 보내야 한다.  
```kotlin
viewPagerAdapter = ProfileViewPagerAdapter(childFragmentManager)
```

#### 특정 Fragment에서만 option menu 추가하기
특정 Fragment에서 option menu를 추가 하려면, oncreateView() callback 함수에서 **setHasOptionsMenu()** 메소드를 호출해야 한다.  
```kotlin
setHasOptionsMenu(true)
```
이후, **onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)를 override**를 해준다.
```kotlin
override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
	super.onCreateOptionsMenu(menu, inflater)

	var subMenu: Menu? = menu?.addSubMenu("SORT")
	subMenu?.add(Menu.NONE, Menu.FIRST + 1, Menu.NONE, "List")
	subMenu?.add(Menu.NONE, Menu.FIRST + 2, Menu.NONE, "Grid")
}
```

#### Activity에서 Fragment로 데이터 보내기
Activity의 데이터를 특정 Fragement에서 사용하려면, **Bundle 객체를 만들고 보낼 데이터들을 put한다.**   
이후, **Bundle 객체**를 **View Pager Adapter 인스턴스를 만들때** 보내준다.   
```kotlin
private var bundle = Bundle()
bundle.putString("name", name)      // ProfileFragment로 데이터 보내기
viewPagerAdapter = HomeViewPagerAdapter(supportFragmentManager, bundle)
```
전달받은 Bundle객체를, View Pager Adapter의 **getItem()** 메소드에서 사용할 Fragment의 인스턴스에 **arguments로 설정**해준다.  
```kotlin
class HomeViewPagerAdapter (fm : FragmentManager, val bundle: Bundle)
    : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    override fun getCount(): Int = 3

    override fun getItem(position: Int) : Fragment {
        when (position) {
            0 -> {
                val profileFragment = ProfileFragment()
                profileFragment.arguments = bundle
                return profileFragment
            }
        }
    }
}
```
해당 Fragement에서 전달 받은 **arguments**를 사용한다.  
```kotlin
name = arguments?.getString("name").toString()
tv_name_profile.text = name
```

## 🍓View Pager
**데이터를 페이지 단위로 표시**하고, 스와이프(Swipe)를 통해 페이지 전환을 할 수 있는 컨테이너이다.  
하나의 화면 안에서 **여러가지 화면을 슬라이드 형식**으로 보여줄 때 사용한다.   
주로 하단 탭(Bottom Navigation), 상단 탭(Tab Layout)과 연동하여 사용한다.   

#### Adapter 생성
ViewPagerAdapter의 역할을 하기 위해 **FragmentStatePagerAdapter를 상속**을 받는다.  
|FragmentStatePagerAdapter  | FragmentPagerAdapter |
|:--:|:--:|
| 양 옆 프래그먼트를 제외한 나머지 완전히 파괴 | 프래그먼트의 인스턴스를 완전히 파괴 X |
| 보여지는 화면 기준 | onDestroyView()만 호출|
| 메모리 누수 관리에 효과적| 프래그먼트 개수가 고정적일 때 효과적|  
	
FragmentStatePagerAdapter를 상속받았으므로, **getCount()를 getItem() override** 해준다.     
ViewPagerAdapter는 **FragmentManager를 필요**로 한다.      

```kotlin
class HomeViewPagerAdapter (fm : FragmentManager, val bundle: Bundle)  
    : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){  
  
    override fun getCount(): Int = 3  // 페이지 수
  
	override fun getItem(position: Int) : Fragment {  // 페이지의 인스턴스를 생성하여 return한다.
        when (position) {  
            0 -> {  
                val profileFragment = ProfileFragment()
                // Activity에서 Fragment로 데이터를 보내주기 위해
                // Adapter 생성시 파라미터로 넘긴 Bundle 객체를 argumets로 설정해준다.  
                profileFragment.arguments = bundle    
                return profileFragment  	
            } 
            1 -> TeamFragment()
            2 -> TempFragment() 
            else -> throw IllegalStateException("Unexpected $position")  
        }  
    }  
}
```
#### Activity에 적용 
View Pager를 적용할 Activity에 viewPagerAdapter 인스턴스를 만든 후, **ViewPager view에 적용**시킨다.  
```kotlin
viewPagerAdapter = HomeViewPagerAdapter(supportFragmentManager, bundle)
vp_home.adapter = viewPagerAdapter
```

## 🍍Bottom Navigation
앱에서 **하단 탭**을 만들 때 사용한다.    
View Pager와 연동하여 화면(page)들을 전환한다.    
**화면이 3개 이상일 때** 사용하는 것을 권장한다.  

#### menu.xml 생성
이름이 menu인 폴더를 만들고, xml 파일을 만든다.   
```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/menu_profile"    
        android:icon="@drawable/ic_baseline_account_circle_24"
        android:checked="false"   
        android:title="Profile"/> 
    <item
        android:id="@+id/menu_team"
        android:icon="@drawable/ic_baseline_toc_24"
        android:checked="false"
        android:title="Team"/>
    <item
        android:id="@+id/menu_temp"
        android:icon="@drawable/ic_baseline_live_help_24"
        android:checked="false"
        android:title="---"/>
</menu>
```

#### selector.xml 생성
menu selected 또는 unselected 일 경우 메뉴 **아이콘 색깔 변경**을 위한 xml 파일을 만든다.  
```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:color="@color/yellow" android:state_checked="true"/>
    <item android:color="#9E9E9E" android:state_checked="false"/>
</selector>
```
#### BottomNavigationView 생성
BottomNavigationView를 적용할 Activity xml에 추가한다.   
```xml
<com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bnvg_home"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/colorPrimaryDark"
        app:itemIconTint="@color/bottom_navi_color"			// tab의 icon 색상
        app:itemRippleColor="#FFEB3B"						// tab 터치할 때 물결 효과 색상
        app:itemTextColor="@color/bottom_navi_color"  		// tab title 색상
        app:labelVisibilityMode="selected"					// title 보여주는 경우 설정
        app:layout_constraintBottom_toBottomOf="parent"		
        app:layout_constraintEnd_toEndOf="parent"		
        app:layout_constraintStart_toStartOf="parent"
        app:menu="@menu/menu" />							// menu item들의 tab으로 적용된다.
```

#### bottomNavigation view에 이벤트 Listener 설정
각 menu item을 터치했을 때에 대한 이벤트 Listener를 설정한다.   
```kotlin
bnvg_home.setOnNavigationItemSelectedListener {
	var index by Delegates.notNull<Int>()
	when(it.itemId) {
		R.id.menu_profile -> index = 0
		R.id.menu_team -> index= 1
		R.id.menu_temp -> index = 2
	}
	vp_home.currentItem = index
	true
}
```
view pager를 스와이프 했을 때에도, 그에 맞게 하단 탭도 변경되어야 한다.    
```kotlin
vp_home.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
	override fun onPageScrollStateChanged(state: Int) {}

	override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

	// ViewPager의 페이지 중 하나가 선택된 경우
	override fun onPageSelected(position: Int) {
		bnvg_home.menu.getItem(position).isChecked = true
		// 나머지 값들은 알아서 false로 바뀜.
	}
})
```

## 🍉Tab Layout
Tab menu를 만들고 싶은 경우 사용한다.    
Bottom Navigation에 비해 **위치 이동이 자유로워, 하단 탭을 제외한 탭을 만들고 싶은 경우** 사용한다.   

#### TabLayout 생성
```kotlin
<com.google.android.material.tabs.TabLayout
            android:id="@+id/tl_profile"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="#4A677E"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:tabIndicatorColor="@color/yellow"		  // 인디케이터의 색 변경
            app:tabSelectedTextColor="@color/yellow" />   // 탭이 선택됐을 때 글자 색 변경
```


#### TabLayout을 View Pager와 연동
```kotlin
tl_profile.setupWithViewPager(vp_profile)
```

#### TabLayout의 Tab title 설정
각 Tab의 title을 설정한다.    
반드시 **View Pager와 연동 후**, 설정해야 한다.    
```kotlin
tl_profile.apply{
	getTabAt(0)?.text = "INFO"
	getTabAt(1)?.text = "OTHER"
}
```