
<img src="https://user-images.githubusercontent.com/47289479/97892017-da3f7080-1d72-11eb-9b18-0734802ed420.png" width="600" height="700"/>

# ⏰액티비티 생명주기(Activity Life Cycle)

## ✨onCreate()

이 callback은 **시스템이 먼저 Activity를 생성할 때 실행되는 것으로, 필수적으로 구현**해야 한다.  
Activity가 생성되면 *Created* 상태가 된다.  
onCreate() 메서드에서는 Activity의 전체 생명 주기 동안 **한 번만 실행해야 하는 기본 애플리케이션 시작 로직**을 수행한다.   
예를 들어, onCreate()를 구현함은 데이터를 목록에 바인딩하고, Activity를 ViewModel과 연결하고, 일부 class-scope 변수를 인스턴스화할 수도 있다.   
이 메서드는 **savedInstanceState 매개변수를 수신하는데, 이는 Activity의 이전 저장 상태가 포함된 Bundle 객체**이다.   
이번에 **처음 생성된 Activity인 경우 Bundle 객체의 값은 null**이다.  

Activity의 생명 주기와 연결된 생명 주기 인식 구성요소가 있다면 이 구성요소는 *ON_CREATE* 이벤트를 수신한다.    
따라서 @OnLifecycleEvent라는 주석이 있는 메서드가 호출되고, 생명 주기 인식 구성요소는 *Created* 상태에 필요한 모든 설정 코드를 실행할 수 있게 된다.   

Activity는 *Created* 상태에 머무르지 않습니다. 메서드가 실행을 완료하면 *Created* 상태가 되고,   
시스템이 연달아 **onStart()** 와 **onResume()** 메서드를 호출한다.  

```kotlin
lateinit var textView: TextView

// 액티비티 객체의 상태
var gameState: String? = null

override fun onCreate(savedInstanceState: Bundle?) {
    // 뷰 계층과 같은 액티비티 생성을 완료하기 위해 super class의 oncreate()를 호출
    super.onCreate(savedInstanceState)

	// 인스턴트 객체를 복구
    gameState = savedInstanceState?.getString(GAME_STATE_KEY)

    // UI 레이아웃을 코드 상으로 동작시키기 위해 호출한다.
    setContentView(R.layout.main_activity)

    // textView를 초기화하여, 조작할 수 있게 한다.
    textView = findViewById(R.id.text_view)
}
```

```kotlin
// 이 callback 함수는 이전에 onSaveInstanceState()를 사용하여 저장된 인스턴스가 존재하는 경우에만 호출된다. 
// onCreate()에서 일부 상태를 복원하지만, 여기서 선택적으로 다른 상태를 복원할 수 있으며, 
// onStart()가 완료된 후 사용 가능할 수 있다.
// savedInstanceState는 onCreate에사 사용되는 것과 동일한 savedInstanceState Bundle 객체이다.
override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
    textView.text = savedInstanceState?.getString(TEXT_VIEW_KEY)
}
```

```kotlin
// 액티비티가 일시적으로 중지되면 인스턴스 상태를 해당 메소드에 저장한다.
override fun onSaveInstanceState(outState: Bundle?) {
    outState?.run {
        putString(GAME_STATE_KEY, gameState)
        putString(TEXT_VIEW_KEY, textView.text.toString())
    }
	// 뷰 계층을 저장하기 위해 super class를 호출한다.
    super.onSaveInstanceState(outState)
}
```

## 🔫onStart()

Activity가 *Started* 상태에 들어가면 시스템은 이 callback 을 호출한다.  
**onStart()** 가 호출되면 **Activity가 사용자에게 보이고**, 앱은 Activity를 foreground에 보내 상호작용할 수 있도록 **준비**한다. 
예를 들어, **이 메서드에서 앱이 UI를 관리하는 코드를 초기화**한다.  

Activity가 *Started* 상태로 전환하면 이 Activity의 생명 주기와 연결된 모든 생명 주기 인식 구성요소는 *ON_START* 이벤트를 수신한다.  

**onStart()** 메서드는 매우 빠르게 완료되고, *Created* 상태와 마찬가지로 Activity는 *Started* 상태에 머무르지 않는다.  
이 callback이 완료되면 Activity가 *Resumed* 상태에 들어가고, 시스템이  **onResume()** 메서드를 호출한다.  

## ▶onResume()

Activity가 *Resumed* 상태에 들어가면 foreground에 표시되고 시스템이 **onResume()** callback을 호출한다.   
이 상태에 들어가면, **앱이 사용자와 상호작용**할 수 있다.   
어떤 이벤트가 발생하여 **앱에서 focus가 떠날 때까지** 앱이 이 상태에 머무른다.     
예를 들어 전화가 오거나, 사용자가 다른 Activity로 이동하거나, 기기 화면이 꺼지는 이벤트가 이에 해당한다.  

Activity가 *Resumed* 상태로 전환되면 이 Activity의 생명 주기와 연결된 모든 생명 주기 인식 구성요소는 *ON_RESUME* 이벤트를 수신한다.   
이 상태에서 생명 주기 구성요소가 foreground에서 **사용자에게 보이는 동안 실행해야 하는 모든 기능을 활성화**할 수 있다.   

**방해되는 이벤트가 발생**하면 Activity는 *Paused* 상태에 들어가고, 시스템이 **onPause()** callback을 호출한다.   

Activity가 *Paused* 상태에서 *Resumed* 상태로 돌아오면 시스템이 **onResume()** 메서드를 다시 한번 호출한다.   
따라서 **onResume()** 을 구현하여 **onPause()** 중에 해제하는 **구성요소를 초기화**하고, Activity가 *Resumed* 상태로 전환될 때마다 필요한 **다른 초기화 작업도 수행**해야 한다.  

초기화 작업을 수행하도록 빌드업 이벤트에 상응하는 생명 주기 이벤트를 사용하여 리소스를 해제해야 한다.  
ON_START 이벤트 이후에 무언가를 초기화하는 경우, ON_STOP 이벤트 이후에 이를 해제하거나 종료해야 한다.  
ON_RESUME 이벤트 이후에 초기화하는 경우, ON_PAUSE 이벤트 이후에 해제해야 한다.  

## ⏸onPause()

시스템은 **사용자가 Activity를 떠나는 것을 나타내는 첫 번째 신호**로 이 메서드를 호출한다.  
하지만 해당 **Activity가 항상 소멸되는 것은 아니다.** **Activity가 foreground에 있지 않게 되었다**는 것을 나타낸다.  
다만, 사용자가 멀티 윈도우 모드에 있을 경우에는 여전히 표시 될 수도 있다.  
**onPause()** 메서드를 사용하여  Activity가 *Paused* 상태일 때 계속 실행(또는 적절히 계속 실행)되어서는 안 되지만 잠시 후 다시 시작할 작업을 일시중지하거나 조정한다.  

Activity가 이 상태에 들어가는 이유는 여러 가지가 있다. 
```
-   일부 이벤트가 앱 실행을 방해한다. 이것이 가장 일반적인 사례이다.  
-   Android 7.0(API 수준 24) 이상에서는 여러 앱이 멀티 윈도우 모드에서 실행된다. 
    언제든지 그중 하나의 앱(창)만 포커스를 가질 수 있기 때문에 시스템이 그 외에 모든 다른 앱을 일시중지 시킨다.  
-   새로운 반투명 Activity(예: 대화상자)이 열린다. 
    Activity가 여전히 부분적으로 보이지만 포커스 상태가 아닌 경우에는 Paused 상태로 유지된다.  
```

Activity가 *Paused* 상태로 전환하면 이 Activity의 생명 주기와 연결된 모든 생명 주기 인식 구성요소는 *ON_PAUSE* 이벤트를 수신한다. 
여기에서 생명 주기 구성요소는 **구성요소가 foreground에 있지 않을 때 실행할 필요가 없는 기능을 모두 정지**할 수 있다(예: 카메라 미리보기 정지).  

또한, **onPause()** 메서드를 사용하여 시스템 리소스, 센서 핸들(예: GPS) 또는 Activity가 일시중지 중이고 사용자가 필요로 하지 않을 때 배터리 생명에 영향을 미칠 수 있는 모든 리소스를 해제할 수도 있다.  
*Paused* Activity는 멀티 윈도우 모드에서 여전히 완전히 보이는 상태일 수 있다.  
그러므로 멀티 윈도우 모드를 더욱 잘 지원하기 위해 UI 관련 리소스와 작업을 완전히 해제하거나 조정할 때는 onPause() 대신 **onStop()** 을 사용하는 것이 좋다.  

**onPause()** 는 **아주 잠깐 실행되므로 저장 작업을 실행하기에는 시간이 부족**할 수 있다.  
그러므로 **onPause()** 를 사용하여 **애플리케이션 또는 사용자 데이터를 저장하거나, 네트워크 호출을 하거나, 데이터베이스 트랜잭션을 실행**해서는 **안 된다**. 이러한 작업은 **메서드 실행이 끝나기 전에 완료되지 못할 수도 있다.** 그 대신, **부하가 큰 종료 작업** **onStop()** 에서 **실행**해야 한다.   

**onPause()** **메서드의 실행이 완료되더라도 Activity가 *Paused* 상태로 남아 있을 수 있다.**  
오히려 **Activity는 다시 시작되거나 사용자에게 완전히 보이지 않게 될 때까지 이 상태에 머무른다.**    
**Activity가 다시 시작**되면 시스템은 다시 한번 **onResume()** callback을 호출한다.  
Activity가 *Paused* 상태에서 *Resumed* 상태로 돌아오면 시스템은 Activity 인스턴스를 메모리에 남겨두고, 시스템이 **onResume()** 을 호출할 때 인스턴스를 다시 호출한다. 이 시나리오에서는 최상위 상태가 *Resumed* 상태인 callback 메서드 중에 생성된 구성요소는 다시 초기화할 필요가 없다. 
**Activity가 완전히 보이지 않게 되면** 시스템은 **onStop()** 을 호출한다.  

## ⏹onStop()

Activity가 **사용자에게 더 이상 표시되지 않으면** *Stopped* 상태에 들어가고, 시스템은 **onStop()** callback을 호출한다.   
이는 예를 들어 **새로 시작된 Activity가 화면 전체를 차지할 경우*에 적용된다.   
시스템은 Activity의 실행이 완료되어 종료될 시점에  **onStop()** 을 호출할 수도 있다.  

Activity가 *Stopped* 상태로 전환하면 이 Activity의 생명 주기와 연결된 모든 생명 주기 인식 구성요소는 *ON_STOP* 이벤트를 수신한다.   
여기에서 생명 주기 구성요소는 **구성요소가 화면에 보이지 않을 때 실행할 필요가 없는 기능을 모두 정지**할 수 있다.  

**onStop()** 메서드에서는 **앱이 사용자에게 보이지 않는 동안 앱은 필요하지 않은 리소스를 해제하거나 조정**해야 한다.  
예를 들어 앱은 애니메이션을 일시중지하거나, 세밀한 위치 업데이트에서 대략적인 위치 업데이트로 전환할 수 있다.    
**onPause()** 대신 **onStop()** 을 사용하면 사용자가 멀티 윈도우 모드에서 Activity를 보고 있더라도 UI 관련 작업이 계속 진행된다.   

또한 **onStop()** 을 사용하여 **CPU를 비교적 많이 소모하는 종료 작업을 실행**해야 한다.   
예를 들어 정보를 **데이터베이스에 저장할 적절한 시기를 찾지 못했다면** **onStop()** 상태일 때 저장할 수 있다.  

Activity가 *Stopped* 상태에 들어가면 Activity 객체는 메모리 안에 머무르게 된다.  
이 객체가 모든 상태 및 멤버 정보를 관리하지만 window manager와 연결되어 있지는 않다.   
Activity가 다시 시작되면 이 정보를 다시 호출한다.  
최상위 상태가 *Resumed* 상태인 callback 메서드 중에 생성된 구성요소는 다시 초기화할 필요가 없다.
   
또한 시스템은 레이아웃에 있는 **각 View 객체의 현재 상태도 기록**한다.   
따라서 사용자가 EditText 위젯에 텍스트를 입력하면 해당 내용이 저장되기 때문에 이를 **저장 및 복원할 필요가 없습니다.**  
Activity가 중단되면 시스템은 해당 Activity가 포함된 프로세스를 소멸시킬 수 있습니다(시스템이 메모리를 복구해야 하는 경우).  
Activity가 중단된 동안 시스템이 프로세스를 소멸시키더라도 *Bundle(키-값 쌍의 blob)* 에 있는 *View* 객체(예: EditText 위젯의 텍스트) 상태가 그대로 유지되고, 사용자가 이 Activity로 돌아오면 이를 복원한다.  

Activity는 *Stopped* 상태에서 **다시 시작**되면 시스템은 **onRestart()** 를 호출한다다.   
Activity가 *Stopped* 상태에서 **실행을 종료**하면 시스템은 **onDestroy()** 를 호출한다.   

## 🎊onDestroy()

**onDestroy()** 는 Activity가 **소멸되기 전에 호출**된다. 시스템은 다음 중 하나에 해당할 때 이 callback을 호출한다.
```
-  (사용자가 Activity를 완전히 닫거나 Activity에서  finish()가 호출되어) Activity가 종료되는 경우
- 구성 변경(예: 기기 회전 또는 멀티 윈도우 모드)으로 인해 시스템이 일시적으로 Activity를 소멸시키는 경우
```

Activity가 *Destroyed* 상태로 전환하면 이 활동의 수명 주기와 연결된 모든 생명 주기 인식 구성요소는 *ON_DESTROY* 이벤트를 수신한다. 여기서 수명 주기 구성요소는 필요했던 것을 Activity가 소멸되기 전에 정리할 수 있다.  

Activity에 소멸되는 이유를 결정하는 로직을 입력하는 대신 *ViewModel* 객체를 사용하여 Activity의 관련 뷰 데이터를 포함해야 한다. Activity가 **구성 변경으로 인해 다시 생성될 경우 ViewModel은 그대로 보존되어 다음 Activity 인스턴스에 전달되므로 추가 작업이 필요하지 않다.** **Activity가 다시 생성되지 않을 경우 ViewModel은**  **onCleared()** 메서드를 호출하여 **Activity가 소멸되기 전에 모든 데이터를 정리**해야 한다.  

이와 같은 두 가지 시나리오는 **isFinishing()** 메서드로 구분할 수 있습니다.  

Activity가 종료되는 경우 **onDestroy()** 는 **Activity가 수신하는 마지막 수명 주기** callback이 됩니다.   
구성 변경으로 인해 **onDestroy()** 가 호출되는 경우 시스템이 **즉시 새 Activity 인스턴스를 생성한 다음,  
새로운 구성에서 그 새로운 인스턴스에 관해**  **onCreate()** 를 **호출**합니다.  

**onDestroy()** callback은 이**전의 callback에서 아직 해제되지 않은 모든 리소스(예: onStop())를 해제해야 한다.**  

<br />
<hr />

# ⌚프래그먼트 생명주기(Fragment Life Cycle)

## 🔗onAttach()
**Fragment가 Activity에 연결될 때** 호출된다.  

## ✨onCreate()
**프래그먼트를 생성할 때** 시스템에서 호출한다.  
구현 내에서 프래그먼트의 기본 구성 요소 중 프래그먼트가 일시정지되거나 중단되었다가 재개되었을 때 유지하고자 하는 것을 초기화해야 한다.  
UI는 초기화할 수 없다.  

## ✨onCreateView()
**시스템이 프래그먼트의 인터페이스를 처음으로 그릴 때** 호출한다.   
**프래그먼트와 연결된 뷰 계층을 생성하기 위해 호출**된다.  
프래그먼트에 맞는 UI를 그리려면 메서드에서 view를 반환해야 한다. 프래그먼트가 UI를 제공하지 않는 경우 null을 반환하면 된다.  
view 객체를 얻을 수 있으므로, view를 초기화 할 수 있다.   
그러나, 몇몇 뷰들이 초기화 되지 않을 수 있기 때문에 findViewById를 사용하여 초기화 하면 안된다.    

## ✨onViewCreated()
**view가 완전히 생성되었을 때** 호출된다.   
**findViewById를 사용하여 초기화 가능**하다.   
Activity의 onCreate()에서 해왔던 작업들을 Fragment는 해당 메서드에서 한다.    

## ✨onActivityCreated()
**Fragment에서의 onCreateView를 마치고, Activity에서 onCreate()가 호출되고** 나서 호출된다.  
Activity와 Fragment의 view가 모두 생성된 상태로, view를 변경하는 작업이 가능하다.   
 
## 🔫onStart()
**Fragment가 사용자에게 보여지기 바로 직전에 호출**된다.    
이 callback에서는 UI를 관리하는 코드를 초기화한다.    
Fragment에서 onStart()가 호출되고 난 후에, Activity의 onStart()가 호출된다.  
 
## ▶onResume()
**사용자에게 해당 Fragment가 보이고, 상호작용이 가능**하다.  
어떤 이벤트가 발생하여 포커스가 떠날 때까지 이 상태에 머무른다.  
프로그램이 일시정지되어 onPause()를 호출하고, 다시 재개되면 onResume() 메서드를 다시 호출한다.  
재개 상태로 전환될때마다 필요한 초기화 작업들을 수행해야 한다.  
Activity의 onResume()이 호출되고, Fragment의 onResume()이 실행된다.   

## ⏸onPause()
**해당 Fragment의 포커스를 잃고, 다른 Fragment가 보여지는 경우** 호출된다.    
Fragment가 backStack으로 들어간다.     
 
## ⏹onStop()
**Fragment의 호스트 Activity가 포커스를 잃고, 다른 Activity가 호스트 Activity를 완전히 가려서 보이지 않는 경우** 호출된다.    

## 🎊onDestroyView()
**Fragment와 관련된 View가 제거될 때 호출**된다.  
이 메소드가 호출된 이후 Fragment가 BackStack에서 돌아오면 onCreateView()가 호출된다.  
해당 callback이 호출된 후에, Activity의 onDestroy()가 호출된다.  
```
※ 백스택
액티비티가 아닌 프래그먼트로 구성할 때는 별도의 뒤로가기 처리를 해줘야 함. 
그렇지 않으면 직전 프래그먼트로 가지 않고 바로 종료될 수 있음.
```

## 🎊onDestroy()
**Activity를 완전히 닫거나 구성변경으로 인해(기기회전 또는 멀티 윈도우 모드) 시스템이 일시적으로 소멸시키는 경우**에도 호출 된다.   
여기에서는 이전 콜백 메서드에서 해제되지 않은 리소스들을 해제해야 합니다.   

## ✂onDetach()
**Fragment가 Activity로부터 해제될 때 호출**된다.  

<br />
<hr />

# ⏳실행순서 
```
Fragment onAttach() → Fragment onCreate() → Fragment onCreateView() → Fragment onViewCreated() →  
Activity onCreate() → Fragment onActivityCreated()  
```
→  
```
Fragment onStart() → Activity onStart()  
```
→  
```
Activity onResume() → Fragment onResume()  
```
→  
```
Fragment onPause() → Activity onPause()  
```
→  
```
Fragment onStop() → Activity onStop()  
```
→  
```
Fragment onDestroyView() → Fragment onDestroy() → Fragment onDetach() → Activity onDestroy()  
```


