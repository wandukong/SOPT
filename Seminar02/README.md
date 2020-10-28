# 📣Seminar02
**작성일자 : 2020.10.19**  
**1. RecyclerView**   
 
## 📱결과 화면

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
        val view = LayoutInflater.from(context).inflate(R.layout.sample_item_list, parent, false)  // xml파일을 토대로 view 객체를 생성한다. 
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

## 🚉Touch Helpr를 통한 Item View 이동 및 삭제


#### ItemTouchHelper
ItemTouchHelper는 RecyclerView의 swipe 및 drag & drop 기능들을 지원하기 위한 클래스이다. RecyclerView와  Callback class와 같이 작동한다.   

#### ItemTouchHelper.Callback
사용자의 어떤 이벤트를 받을 것이고, 그에 대응되는 어떤 작업을 할 것인지 정의된다.  즉, view holder마다 어떤 터치 동작을 유효하게 할지를 제어하고, 사용자가 해당 터치를 했을 때, 해당 동작에 맞는 콜백 함수들을 받는다.   

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
사용자가 아이템을 drag하면, ItemTouchHelper는 onMove()를 호출한다. 해당 Callback 함수를 받으면, 아이템을 기존 위치에서 새로운 위치로 이동시킬 수 있다. 또한, item이 이동했다는 것을 알려 주기위해 adapter의 notifyItemMoved(fromPosition, toPosition)를 호출해야한다.  
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
사용자가 item을 swipe하면, ItemTouchHelper는 onSwiped()를 호출한다.해당 Callback 함수를 받으면, 원하는 동작을 시킬 수 있다. 또한, 원하는 동작에 맞는 notifyEVENT()를 호출해야한다.   
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
#### 구현
이동 및 삭제를 구현하기 위한** interface**를 만든다.  
```kotlin
interface ItemTouchListener{
    fun onDragDrop(fromPosition : Int, toPosition : Int) : Boolean
    fun onSwipe(position : Int)
}  
```
ItemTouchHelper.Callback() 상속받고, 해당 함수들을 구현하는 클래스를 만든다.  
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
이후, 해당 touchHelper를 RecyclerView에 연결시킨다.  
```kotlin
teamAdapter = TeamAdapter(this)
teamAdapter.touchHelper = ItemTouchHelper(ItemTouchCallback(teamAdapter))
teamAdapter.touchHelper.attachToRecyclerView(rcv_teamList_team)
```




