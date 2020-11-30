# 📣Seminar01 Layout
 **작성일자 : 2020.10.15**
 
 **1. Layout 종류**    
 **2. ViewGroup & View**   
 **3. startActivity & startActivityForResult**    
 **4. SharedPreferences** 
 
## 📱결과 화면

<p float="left">
	<img src="https://user-images.githubusercontent.com/47289479/96073917-6bdd6000-0ee2-11eb-9143-c2442804757c.gif" width="300" height="495"/>
	<img src="https://user-images.githubusercontent.com/47289479/96068637-76462c80-0ed7-11eb-8b0a-c8ea386a8672.gif" width="300" height="495"/>
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

### ArrayList 저장 및 불러오기

#### 저장
```kotlin
val memberArray = JSONArray()
memberArray.put(et_namme_signup.text.toString())
memberArray.put(et_pw_signup.text.toString())

val preferencesEditor: SharedPreferences.Editor = member.edit()
preferencesEditor.putString(et_id_signup.text.toString(), memberArray.toString()) // key값을 ID로 지정
```

#### 불러오기
```kotlin 
var memberJson: String? = member.getString(et_id_login.text.toString(), null) // 아이디로 value를 가지고 온다. String 타입
	
var memberArray:JSONArray? = null // value를 담을 배열 ["이름", "비밀번호"]
if(memberJson != null){
	memberArray = JSONArray(memberJson) // JSONArray로 변환
}
// memberArray[0], memberArray[1]로 접근
```

<hr />

# 📣Seminar02 RecyclerView
**작성일자 : 2020.10.28**  

## 📱결과 화면
<p float="left">
	<img src="https://user-images.githubusercontent.com/47289479/97446628-f9ed2800-1971-11eb-8bde-31e34b87dfb0.gif" width="260" height="430"/>
	<img src="https://user-images.githubusercontent.com/47289479/97446705-17ba8d00-1972-11eb-80d9-884ad26e96b3.gif" width="260" height="430"/>
	<img src="https://user-images.githubusercontent.com/47289479/97446752-2739d600-1972-11eb-9096-679b3463345a.gif" width="260" height="430"/>
</p>
①아이템 이동　　　　　　　　　　　②아이템 삭제　　　　　　　　　　③레이아웃 변경　　　　　　　　　

## 🚗Data Class
RecyclervView의 각 리스트마다 필요한 데이터를 담을 **data class**를 만든다.  
```kotlin
data class SampleData(  
    val title : String, 
    val subTitle : String  
)
```

## 🚌View Holder
**View Holder**는 각 list의 view를 담고 있는다.  
각각의 item은 view로 만들어지며, item을 위한 view는 **View Holder**에 담아두게 된다.  
**View Holder**는 list의 view들을 연결해주고, data를 넣어주는 역할(**binding**)을 한다.   
```kotlin
// MainActivity
class SampleViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){  
    private val title : TextView = itemView.findViewById(R.id.tv_title)  
    private val subTitle : TextView = itemView.findViewById(R.id.tv_subTitle)  
  
    fun onBind(data : SampleData) {   // 바인딩
        title.text = data.title  
        subTitle.text = data.subTitle  
  }  
}
```
**RecyclerView.ViewHolder를 상속 받은 후, itemView를 생성자로 넣어준다**.  
이때, itemView는 각 리스트에 담아둘 아이템을 정의한  xml파일을 가리킨다.  

## ✈Adapter	
**Adapter**는 각 View Holder를 생성 및 관리한다.  
Context 객체가 필요하므로, Context 객체를 선언과 동시에 초기화 해준다.  
RecyclerView.Adapter를 상속받으며, <>안에 어떤 View Holder를 업데이트 해줄지 지정한다.  
RecyclerView.Adpater를 상속받으면 아래 **3가지 메소드를 오버라이드** 해줘야 한다.  
```kotlin
override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder
// View Holder가 새로 만들어질 때 호출된다. 각 아이템을 위한 xml 레이아웃을 이용해 뷰 객체를 만든다. 
// 화면에 보여지는 View Holder와 여분의 View Holder를 만들고, 실행되지 않는다.(이후 재활용한다.)
// Layout Manager에 의해 호출된다.
```
```kotlin
override fun onBindViewHolder(holder: SampleViewHolder, position: Int)
// View Holder의 데이터를 바꾼다.
// Layout Manager에 의해 호출된다. 
   ``` 
```kotlin
override fun getItemCount(): Int
// data의 총 개수를 return 한다.
```
```kotlin
class SampleAdapter (private val context : Context) : RecyclerView.Adapter<SampleViewHolder>(){  
  
    var data = mutableListOf<SampleData>()  
  
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {  
        val view = LayoutInflater.from(context).inflate(R.layout.sample_item_list, parent, false)  
		// xml파일을 토대로 view 객체를 생성한다. 
        return SampleViewHolder(view)  
    }  
  
    override fun getItemCount(): Int = data.size  
  
    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {.  
         holder.onBind(data[position])  // view holder의 onBind()를 통해 새로운 데이터로 바인딩
    } 
}
```

## 🛴적용	
RecyclerView에 **Adapter**와 **Layout Manager**를 지정해준다.  
```kotlin
private lateinit var profileAdapter: SampleAdapter
```
```kotlin
profileAdapter = SampleAdapter(this) // Adapter 객체 생성
   
// main_rcv.adapter = profileAdapter  // RecyclerView에 Adpater 지정
// main_rcv.layoutManager = LinearLayoutManager(this) // RecyclerView에 Layout Manager 지정  

main_rcv.apply { // scope function으로 작성 가능
    adapter = profileAdapter
    layoutManager=LinearLayoutManager(context)
}
  
profileAdapter.data = mutableListOf(  
    SampleData("이름", "양승완"),  
    SampleData("나이", "2?"),  
    SampleData("이름", "안드로이드"),  
    SampleData("이름", "www.github.com/wandukong"),  
    SampleData("이름", "www.sopt.org")  
)
```

## 🚲Grid Layout
격자 모양으로 list들을 배치할 수 있다.  
```kotlin
rcv_teamList_home.layoutManager = GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
// parameter : context, 나눌 개수, 나누는 방향, 채우는 방향 
```

## 🚉Item View에 Click Event 넣기
긱 data에 맞는 상세 페이지 click event를 넣기 위해,   
**onBind()로 data가 view holder에 바인딩 될 때, 각 data에 맞는 click event도 정의해준다.**   
onBindViewHolder()에서 처리하려고 했으나, List에서 데이터 값들은 바뀌지만,    
onBindViewHolder()가 호출되지 않는 경우, click event가 재정의 되지 않아, 기존의 데이터에 대한 click event가 실행되었다.   
(onBindViewHolder()가 호출되는 시점은 view holder가 처음 만들어지거나, 스크롤을 해서 데이터 바인딩이 새롭게 필요할 때 마다 호출된다.)  

```kotlin
fun onBind(data : TeamData) {
        title.text = data.title
        subTitle.text = data.subTitle

        itemView.setOnClickListener {
            var intent = Intent(itemView.context, TeamDetailActivity::class.java)

            intent.putExtra("title", data.title)
            intent.putExtra("subTitle", data.subTitle)
            intent.putExtra("date", data.date)
            intent.putExtra("detail", data.detail)

            itemView.context.startActivity(intent)
        }
    }
```

## 🚝Touch Helpr를 통한 Item View 이동 및 삭제


### ItemTouchHelper
ItemTouchHelper는 RecyclerView의 swipe 및 drag & drop 기능들을 지원하기 위한 클래스이다. RecyclerView와  Callback class와 같이 작동한다.   

### ItemTouchHelper.Callback
사용자의 어떤 이벤트를 받을 것이고, 그에 대응되는 어떤 작업을 할 것인지 정의된다.  
즉, view holder마다 어떤 터치 동작을 유효하게 할지를 제어하고, 사용자가 해당 터치를 했을 때, 해당 동작에 맞는 콜백 함수들을 받는다.   

1️⃣``getMovementFlags(RecyclerView, ViewHolder)``  
사용자 수행한 터치 동작을 제어하기 위해 getMovementFlags(RecyclerView, ViewHolder)를 재정의하고 적절한 방향 플래그 집합(START, END, UP, DOWN)을 반환해야 한다. makeMovementFlags(int, int)를 사용하여 쉽게 구성할 수 있다.   
```kotlin
override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {  
    return makeMovementFlags(  // (dragFlags, swipeFlags)
        ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END,  
        ItemTouchHelper.START or ItemTouchHelper.END  
  )  
}
```
2️⃣```onMove(recyclerView, dragged, target)```  
사용자가 아이템을 drag하면, ItemTouchHelper는 onMove()를 호출한다.   
해당 Callback 함수를 받으면, 아이템을 기존 위치에서 새로운 위치로 이동시킬 수 있다.   
또한, item이 이동했다는 것을 알려 주기위해 adapter의 notifyItemMoved(fromPosition, toPosition)를 호출해야한다.   
```kotlin
override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {  
    return listener.onDragDrop(viewHolder.adapterPosition, target.adapterPosition)  
}  
```
```kotlin
override fun onDragDrop(fromPosition: Int, toPosition: Int) : Boolean{  
    val moveData = data[fromPosition]  
    data.removeAt(fromPosition)  
    data.add(toPosition, moveData)  
  
    notifyItemMoved(fromPosition, toPosition)  
    return true  
}  
```
3️⃣```onSwiped(ViewHolder, int)```  
사용자가 item을 swipe하면, ItemTouchHelper는 onSwiped()를 호출한다.  
해당 Callback 함수를 받으면, 원하는 동작을 시킬 수 있다.  
또한, 원하는 동작에 맞는 notifyEVENT()를 호출해야한다.   
 ```kotlin 
override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {  
    listener.onSwipe(viewHolder.adapterPosition)  
}
```
```kotlin
override fun onSwipe(position: Int) {  
    data.removeAt(position)  
    notifyItemRemoved(position)  
}
```
### 구현
이동 및 삭제를 구현하기 위한 **interface**를 만든다.  
```kotlin
interface ItemTouchListener{
    fun onDragDrop(fromPosition : Int, toPosition : Int) : Boolean
    fun onSwipe(position : Int)
}  
```
**ItemTouchHelper.Callback()** 을 상속받고, 해당 함수들을 구현하는 클래스를 만든다.  
```kotlin
class ItemTouchCallback (private val listener : ItemTouchListener) : ItemTouchHelper.Callback(){  
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {  
        return makeMovementFlags(  
                ItemTouchHelper.UP or ItemTouchHelper.DOWN or 
                 ItemTouchHelper.START or ItemTouchHelper.END,  
				ItemTouchHelper.START or ItemTouchHelper.END  
        )  
    }  
  
    override fun onMove(recyclerView: RecyclerView, 
					    viewHolder: RecyclerView.ViewHolder, 
		                target: RecyclerView.ViewHolder): Boolean {  
        return listener.onDragDrop(viewHolder.adapterPosition, target.adapterPosition)  
    }  
  
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {  
        listener.onSwipe(viewHolder.adapterPosition)  
    }  
}
```
**Adapter**에서 interface를 상속받아 해당 메소드를  구현한다.   
```kotlin
class TeamAdapter (private val context : Context) : RecyclerView.Adapter<TeamViewHolder>(), ItemTouchListener {
	lateinit var touchHelper : ItemTouchHelper // adapter를 먼저 만들고, 그 이후에 touchHelper를 정의해준다.
    // ...
    
    override fun onDragDrop(fromPosition: Int, toPosition: Int) : Boolean{  
        val moveData = data[fromPosition]  
        data.removeAt(fromPosition)  
        data.add(toPosition, moveData)  
  
        notifyItemMoved(fromPosition, toPosition)  
        return true  
  }  
  
    override fun onSwipe(position: Int) {  
        data.removeAt(position)  
        notifyItemRemoved(position)  
    }  
}
```
RecyclerView가 있는 Activity에서 adapter의 touchHelper를 정의해준다.  
이후, **attachToRecyclerView()** 로 해당 touchHelper를 RecyclerView에 연결시킨다.  
```kotlin
teamAdapter = TeamAdapter(this)
teamAdapter.touchHelper = ItemTouchHelper(ItemTouchCallback(teamAdapter))
teamAdapter.touchHelper.attachToRecyclerView(rcv_teamList_team)
```

<hr />

# 📣Seminar03 Fragement
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

### Fragment Transaction
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

### Fragement에서 또 하나의 View Pager 만들기
Activity에서 View Pager 인스턴스를 만들 경우, Fragment Manager 클래스로 **supportFragmentManager**를 보냈다.    
```kotlin
viewPagerAdapter = HomeViewPagerAdapter(supportFragmentManager)
```
하지만, Fragment에서 View Pager 인스턴스를 만들 경우, Fragment Manager 클래스로 **childFragmentManager** 보내야 한다.  
```kotlin
viewPagerAdapter = ProfileViewPagerAdapter(childFragmentManager)
```

### 특정 Fragment에서만 option menu 추가하기
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

### Activity에서 Fragment로 데이터 보내기
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

### Adapter 생성
ViewPagerAdapter의 역할을 하기 위해 **FragmentStatePagerAdapter를 상속**을 받는다.  
|FragmentStatePagerAdapter|FragmentPagerAdapter|
|:--:|:--:|
|양 옆 프래그먼트를 제외한 나머지 완전히 파괴 | 프래그먼트의 인스턴스를 완전히 파괴 X|
|보여지는 화면 기준|onDestroyView()만 호출|
|메모리 누수 관리에 효과적|프래그먼트 개수가 고정적일 때 효과적|
	
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
### Activity에 적용 
View Pager를 적용할 Activity에 viewPagerAdapter 인스턴스를 만든 후, **ViewPager view에 적용**시킨다.  
```kotlin
viewPagerAdapter = HomeViewPagerAdapter(supportFragmentManager, bundle)
vp_home.adapter = viewPagerAdapter
```

## 🍍Bottom Navigation
앱에서 **하단 탭**을 만들 때 사용한다.    
View Pager와 연동하여 화면(page)들을 전환한다.    
**화면이 3개 이상일 때** 사용하는 것을 권장한다.  

### menu.xml 생성
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

### selector.xml 생성
menu selected 또는 unselected 일 경우 메뉴 **아이콘 색깔 변경**을 위한 xml 파일을 만든다.  
```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:color="@color/yellow" android:state_checked="true"/>
    <item android:color="#9E9E9E" android:state_checked="false"/>
</selector>
```
### BottomNavigationView 생성
BottomNavigationView를 적용할 Activity xml에 추가한다.   
```xml
<com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bnvg_home"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/colorPrimaryDark"
        app:itemIconTint="@color/bottom_navi_color"       // tab의 icon 색상
        app:itemRippleColor="#FFEB3B"                     // tab 터치할 때 물결 효과 색상
        app:itemTextColor="@color/bottom_navi_color"      // tab title 색상
        app:labelVisibilityMode="selected"                // title 보여주는 경우 설정
        app:layout_constraintBottom_toBottomOf="parent"		
        app:layout_constraintEnd_toEndOf="parent"		
        app:layout_constraintStart_toStartOf="parent"
        app:menu="@menu/menu" />                          // menu item들의 tab으로 적용된다.
```

### bottomNavigation view에 이벤트 Listener 설정
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

### TabLayout 생성
```kotlin
<com.google.android.material.tabs.TabLayout
            android:id="@+id/tl_profile"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="#4A677E"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:tabIndicatorColor="@color/yellow"           // 인디케이터의 색 변경
            app:tabSelectedTextColor="@color/yellow" />     // 탭이 선택됐을 때 글자 색 변경
```

### TabLayout을 View Pager와 연동
```kotlin
tl_profile.setupWithViewPager(vp_profile)
```

### TabLayout의 Tab title 설정
각 Tab의 title을 설정한다.    
반드시 **View Pager와 연동 후**, 설정해야 한다.    
```kotlin
tl_profile.apply{
	getTabAt(0)?.text = "INFO"
	getTabAt(1)?.text = "OTHER"
}
```

<hr />

# 📣Seminar06 서버 통신(Retrofit)
**작성일자 : 2020.11.29**

## 📱결과 화면

<p float="left">
	<img src="https://user-images.githubusercontent.com/47289479/100542891-4c32a900-3290-11eb-819c-dff073c90dc0.jpg" width="260" height="430"/>
	<img src="https://user-images.githubusercontent.com/47289479/100543019-088c6f00-3291-11eb-962b-0a388cb09794.jpg" width="260" height="430"/>
	<img src="https://user-images.githubusercontent.com/47289479/100543365-088d6e80-3293-11eb-8206-cf240eb52023.gif" width="260" height="430"/>
</p>
①Dummy 리스트 구현　　　　　　　②Kakao API web search 구현　　　③전체 실행 

## 💼준비 사항
#### 라이브러리 추가
```gradle
// https://github.com/square/retrofit
implementation 'com.squareup.retrofit2:retrofit:2.9.0’
// Retrofit 라이브러리 응답으로 가짜 객체를 만들기 위함
implementation 'com.squareup.retrofit2:retrofit-mock:2.9.0'
// https://github.com/google/gson
implementation 'com.google.code.gson:gson:2.8.6’
// Retrofit에서 Gson을 사용하기 위한 라이브러리
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
```
#### AndroidManifest.xml 
```xml
<uses-permission android:name="android.permission.INTERNET"/>  // 인터넷 권한 허용

<application 
	android:usesCleartextTraffic="true"  
	 ...  />   // http 프로토콜 접속 예외 처리
```

## ❤interface 생성
API를 통한 request&response를 위해서 **interface**를 생성해준다.  
```kotlin
interface UserService {  
    @Headers("Content-Type:application/json")  
    @POST("/users/signup")  
    fun signUp(  
        @Body body: SignupRequestData  
    ) : Call<SignupResponseData>  
  
    @Headers("Content-Type:application/json")  
    @POST("/users/signin")  
    fun signIn(  
        @Body body : SigninRequestData  
    ) : Call<SigninResponseData>  
  
    @GET("/api/users")
    fun loadUsers(
        @Query("page") page : Int
    ) : Call<LoadUsersResponseData>
}
```

#### URL에 파라미터 넣기
**@Query** 어노테이션을 매개변수로 명시해줘야 한다.  
```kotlin
@GET("/api/users")
fun loadUsers(
    @Query("page") page : Int  
) : Call<LoadUsersResponseData>
```

#### Headers와 Header차이
```kotlin
interface WebService {  
    @Headers("Authorization:KakaoAK 181f5e5c475eb6a7f7a4e535f7e8e783")  
    @GET("/v2/search/web")  
    fun webSearch(
        @Header("Authorization") authorization : String
    ) : Call<WebSearchResponseData>  
}
```
**@Headers**는 기존 헤더들의 정보를 보내줄 때 사용한다.  
메소드 안의 **@Header**는 주로 custom Header들을 보내줄 때 사용한다.    
(위 코드는 Authorization 헤더는 두 개중 한 개만 사용하면 된다.)   

#### URL 다루기
URL을 동적으로 부분 치환 하기 위하여 **{}** 로 감싸서 정의해준다.  
이후, **@Path** 어노테이션을 매개변수로 명시해줘야 한다.  
```kotlin
@GET("group/{id}/users")
fun getUsers(
    @Path("id") id : Int
)
```

## 🧡구현체 만들기 (싱글톤으로)  
객체를 한 번만 생성 후, 어디에서나 사용하기 위해 **싱글톤**으로 구현체를 만든다.  
싱글톤 객체로 사용하기 위해서 **object**로 선언한다. 
 
```kotlin
object UserServiceImpl {
    private const val BASE_URL = "http://15.164.83.210:3000"

    private val retrofit : Retrofit = Retrofit.Builder()    // retrofit 빌더 생성
        .baseUrl(BASE_URL)                                  // 빌더 객체의 baseUrl 호출, 호스트 URL 전달
        .addConverterFactory(GsonConverterFactory.create()) // json으로 받아오는 데이터를 gson을 통해 다루기 쉽게 변한시킨다.
        .build()											// retrofit 객체 반환

    val service : UserService = retrofit.create(UserService::class.java) // interface를 넘겨 구현체를 생성한다.
}
```

## 💛Call & Callback
**Call** 객체는 웹서버와 통신하여 response 값을 받아오는 객체이다.  
**Callback** 객체는 웹서버와 통신하여 전달 받은 response를 가지고 어떠한 방식으로 처리할 것인지 정의하는 객체이다.  
Call 객체를 생성한 후, 서버로부터 받은 response를 어떻게 처리할 것인지에 대한 Callback 함수를 **object**로 작성하여 **enqueue()** 의 파라미터로 넘겨준다.  
```kotlin
// Call 객체 생성
val call : Call<SigninResponseData> = UserServiceImpl.service.signIn(
		SigninRequestData(email = et_id_login.text.toString(), password = et_pw_login.text.toString())
)
call.enqueue(object : Callback<SigninResponseData>{
	override fun onResponse(call: Call<SigninResponseData>, response: Response<SigninResponseData>) {  // 통신 성공 로직
		response.takeIf { response.isSuccessful }  // status Code가 [200~300)일 경우
				?.body() 
				?.let { // body가 null이 아닌 경우
				
				} ?: UserServiceImpl.showError(this@MainActivity, response.errorBody())  // status Code가 300 초과 or body가 null인 경우
	}
	override fun onFailure(call: Call<SigninResponseData>, t: Throwable) {	// 통신 실패 로직
	}
})
```