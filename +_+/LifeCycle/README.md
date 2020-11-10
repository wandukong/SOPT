
# Lifecycle (Handling Lifecycles with Lifecycle-Aware Components)

Lifecycle-Aware Components는 Activity와 Fragment와 같은 다른 구성요소의 **생명 주기 상태 변경에 따른 액션을 수행**한다.    
이러한 구성요소를 사용하면 잘 구성된 가벼운 코드를 만들어 더욱 쉽게 유지할 수 있다.  
**androidx.lifecycle** 패키지는 Activity와 Fragment의 현재 생명 주기 상태를 기반으로 동작을 정의할 수 있는 components를 빌드할 수 있는 class 및 interface를 제공한다.  

## Lifecycle
**Lifecycle**은 Activity와 Fragment와 같은 구성요소의 수명 주기 상태 관련 정보를 포함하며, 다른 객체가 이 상태를 확인할 수 있게 하는 클래스이다.  

<img src="https://user-images.githubusercontent.com/47289479/98667878-6153a080-2392-11eb-9039-5d55bb82db65.png" width="800" hegiht="300"/>


### Lifecycle.Event
```
- ON_ANY  
- ON_CREATE  
- ON_DESTROY  
- ON_PAUSE  
- ON_RESUME  
- ON_START  
- ON_STOP  
```
### Lifecycle.State
```
- CREATED   
- DESTROYED    
- INITIALIZED   
- RESUMED  
- STARTED   
```

## LifecycleOwner
**Lifecycle Owner**는 클래스에 Lifecycle이 있음을 나타내는 단일 메서드 인터페이스이다.  
즉, 자신의 생명주기를 담은 Lifecycle 객체가 LifecycleOnwer이다.    
LifecycleOwner는 단일 method인 getLifecycle()을 가진다. (kotlin에서 **lifecycle**로 사용)    
이 인터페이스는 Fragment 및 AppCompatActivity와 같은 개별 클래스에서 Lifecycle의 정보를 추출하고, 함께 작동하는 구성요소를 작성할 수 있게 한다.   
모든 애플리케이션 클래스는 LifecycleOwner 인터페이스를 구현할 수 있다.  
```kotlin
class MainActivity : AppCompatActivity() {  
    private lateinit var myObserver: MyObserver  
  
    override fun onCreate(savedInstanceState: Bundle?) {  
        super.onCreate(savedInstanceState)  
        setContentView(R.layout.activity_main)  
  
        myObserver = MyObserver(this, lifecycle)  // Lifecycle Obsever 객체 생성
        lifecycle.addObserver(myObserver) 
  
        buttonPressed()  
    }  
  
    private fun buttonPressed(){  
        download.setOnClickListener {  
		    if(lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)){ 
		    // 현재 상태가 STARTED 상태 이후 인지 확인.  
				Toast.makeText(applicationContext,"You can now download your music...",
								Toast.LENGTH_SHORT).show()  
            }  
        }  
  }
``` 
**addObserver()** 메소드는 Lifecycle Owner가 생명 주기 상태 변화 수신할 Lifecycle Observer를 추가한다.  
**getCurrentState()** 메소드는 현재 생명 주기 상태를 반환한다.  
**isAtLeast()** 메소드는 현재 상태가 매개변수로 들어오는 상태 이후 인지 확인하여 true/false를 반환한다.  
 

## Lifecycle Observer
실제 Activity나 Fragment의 생명 주기 **event를 수신**한다.   
수신 받은 생명 주기 **event에 따라 정의한 함수를 실행**시킬 수 있다.  
Activity나 Fragment의 코드를 건드리지 않고 추가적인 작업을 수행할 수 있다.   
모든 Activity나 Fragment에 공통되게 적용할 작업 등을 정의할 수도 있고, 관심사에 따라 코드를 Observer가 처리할 부분과 Activity나 Fragment가 처리할 부분으로 분리하여 관리할 수도 있다.  
```kotlin
class MyObserver (private val context: Context, private val lifecycle : Lifecycle) : LifecycleObserver{  
    private val TAG = "MyObserver"  
    
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)  
    fun onResume(){   // ON_RESUME 상태 일 때, 해당 Toast 메시지와 Log를 띄운다.
        Toast.makeText(context, "Retrieving data...", Toast.LENGTH_SHORT).show()   
        Log.i(TAG, "Lifecycle.Event.ON_RESUME")  
    }  
  
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)  
    fun onStop(){  // ON_STOP 상태 일 때, 해당 Toast 메시지와 Log를 띄운다.
        Toast.makeText(context, "Preparing to exit app...", Toast.LENGTH_SHORT).show()  
        Log.i(TAG, "Lifecycle.Event.ON_STOP")  
    }  
}
```

## 실행화면
<p float="left">
	<img src="https://user-images.githubusercontent.com/47289479/98671193-3a4b9d80-2397-11eb-8211-aa82dfd25a33.png" width="300" height="495"/>
	<img src="https://user-images.githubusercontent.com/47289479/98671144-2607a080-2397-11eb-9132-a68423867009.png" width="300" height="495"/>
	<img src="https://user-images.githubusercontent.com/47289479/98671261-551e1200-2397-11eb-9173-f561e0fc86c6.png" width="300" height="495"/>
</p>   
①ON_RESUME　　　　　　　　　　②ON_STOP　　　　　　　　　　　③button click