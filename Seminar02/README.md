
# Seminar02
 **1. RecyclerView**  

## Data Class
RecyclervView의 각 리스트마다 필요한 데이터를 담을 **data class**를 만든다.  
```kotlin
data class SampleData(  
    val title : String,  
	val subTitle : String  
)
```

## View Holder
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

## Adapter	
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

## 적용	
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

## Grid Layout
격자 모양으로 list들을 배치할 수 있다.  
```kotlin
rcv_teamList_home.layoutManager = GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
// parameter : context, 나눌 개수, 나누는 방향, 채우는 방향 
```

## item View에 Click Event 넣기
모든 item View에 click event를 넣으려면, 항상 실행되는 onBindViewHolder() 안에서 setOnClickListener를 정의한다.  

```kotlin
override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
	// ... 
    holder.itemView.setOnClickListener {  
        // ... 
    }
```
setOnclickListener에서 startActivity()를 이용하여, 상세 정보를 확인할 수 있는 Acitivity를 호출한다.  
```kotlin
holder.itemView.setOnClickListener {  
    var intent = Intent(context, TeamDetailActivity::class.java)  
  
    intent.putExtra("title", data[position].title)  
    intent.putExtra("subTitle", data[position].subTitle)  
    intent.putExtra("date", data[position].date)  
    intent.putExtra("detail", data[position].detail)  
  
    context.startActivity(intent)  
}
```