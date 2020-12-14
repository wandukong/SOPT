# 💾Room (+MVVM)

**ROOM은 ORM(Object Relational Mapping) 라이브러리**이다.  
즉, ROOM은 데이터베이스의 객체를 Java or Kotlin 객체로 mapping해 준다.  
ROOM은 SQLite의 추상레이어 위에 제공하고 있으며, SQLite의 모든 기능을 제공하면서 편한 데이터베이스의 접근을 허용합니다.  
 
## 📱결과 화면

<p float="left">
	<img src="https://user-images.githubusercontent.com/47289479/102084700-e120cf00-3e58-11eb-9914-b674b8680295.gif" width="300" height="495"/>
	<img src="https://user-images.githubusercontent.com/47289479/102084717-ea11a080-3e58-11eb-8014-05a21f4639e6.gif" width="300" height="495"/>
</p>
①데이터 추가　　　　　　　　　　　　②데이터 삭제 

## 👒준비사항(build.gradle)
```kotlin
plugins {
	id 'kotlin-kapt'
}

dependencies {
	// Room  
	implementation "androidx.room:room-runtime:2.2.5"  
	kapt "androidx.room:room-compiler:2.2.5"  
	implementation "androidx.room:room-ktx:2.2.5"  
	androidTestImplementation "androidx.room:room-testing:2.2.5"  
	  
	// Lifecycles  
	implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'  
	implementation 'androidx.lifecycle:lifecycle-common-java8:2.2.0'  
	implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0'
}
```

## 👓Entity
Database의 **Table**을 나타낸다.  
Table을 담을 변수들을 지정한다.
```kotlin
@Entity(tableName = "plan_table")  
data class Plan(  

  @PrimaryKey(autoGenerate = true)
  val id: Int?,  
  @ColumnInfo(name="title")
  val title: String,  
  @ColumnInfo(name="date")
  val date : String = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(System.currentTimeMillis())  
)
```

## 👔Dao
데이터베이스에 액세스하는 데 사용되는 메서드가 포함되어 있다.   
즉, **query문**을 작성하는 부분이다.  **interface로 작성**한다.  
``Room이 coroutine을 지원하면서, Dao에서 suspend function으로  선언하면, 해당 function들은 main thread에서 동작하지 않습니다. ``  
 
```kotlin
@Dao  
interface PlanDao{  
  @Query("SELECT * FROM plan_table")  
  fun getAll(): LiveData<MutableList<Plan>>  
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)  
  suspend fun addPlan(plan : Plan)  
  
  @Delete  
  fun deletePlan(plan : Plan)  
}
```

## 👖Database
데이터베이스 홀더를 포함하며 앱의 지속적인 관계형 데이터의 기본 연결을 위한 기본 액세스 포인트 역할을 한다.  
**RoomDatabase 클래스를 상속받는 추상 클래스**여야한다.   
@Database 애노테이션안에 해당 Database와 관련된 **Entity 리스트를 포함**해야한다.   
또한, ``Dao를 반환하는 추상 메소드**가 존재해야 한다.   
싱글톤의 중복생성을 방지하기위해, **synchronized**로 구성한다.

```kotlin
@Database(entities = [Plan::class], version = 1, exportSchema = false)  
abstract class PlanDatabase : RoomDatabase() {  
    abstract fun planDao(): PlanDao  
  
    companion object {  
        @Volatile  
        private var INSTANCE: PlanDatabase? = null  
	    fun getDatabase(context: Context): PlanDatabase {  
			if (INSTANCE == null) {  
				synchronized(this) {  
					INSTANCE = Room.databaseBuilder(  
						context.applicationContext,  
						PlanDatabase::class.java, "plan_table"  
					).build()  
				}  
			}  
           return INSTANCE!!  
	    }  
    }  
}
```
## 🧦ViewModel
View에 연결할 데이터와 명령으로 구성되어 있으며, **변경 알림을 통해서 View에게 상태 변화를 전달**한다.    
ViewModel의 **viewModelScope** 속성을 통해 **ViewModel의 CoroutineScope**에 액세스할 수 있습니다.   
즉, viewModelScope를 사용하면 lifecycle을 인식하는 **CoroutineScope** 를 만들 수 있다.  
viewModelScope 블록에서 실행되는 작업은 별도의 처리를 하지 않아도 ViewModel이 clear 되는 순간 자동으로 취소된다.  
```kotlin
class PlanViewModel(application: Application) : AndroidViewModel(application) {  
  
	// LiveData로 선언하여, 데이터가 변경될 때마다 관찰자에게 알려준다.
	val allData : LiveData<MutableList<Plan>>  
	private val repository : PlanRepository  

	init {  
		val planDao = PlanDatabase.getDatabase(application).planDao()  
		repository = PlanRepository(planDao)  
		allData = repository.allData  
	}  

	fun addPlan(plan : Plan){  
		viewModelScope.launch(Dispatchers.IO){  
			// Dispatcher를 launch에 전달하지 않으면, 
			// viewModelScope에서 실행된 코루틴은 기본 스레드에서 실행됩니다.
			repository.addPlan(plan)  
		}  
	}  

	fun deletePlan(plan : Plan){  
		viewModelScope.launch(Dispatchers.IO){  
			repository.deletePlan(plan)  
		}  
	}  
}
```

## 👞Repository
Repository는 **러 백엔드**(Room, Retrofit, ...)로부터 데이터를 가져와 사용할 수 있도록 한다.
```kotlin
class PlanRepository(private val planDao : PlanDao) {  
  
	val allData : LiveData<MutableList<Plan>> = planDao.getAll()  

	suspend fun addPlan(plan : Plan){  
		planDao.addPlan(plan)  
	}  
	
	suspend fun deletePlan(plan : Plan){  
		planDao.deletePlan(plan)  
	}
}
```


## 🎒View(Activity)
```kotlin
private lateinit var planViewModel : PlanViewModel  

// viewModel 객체 생성
planViewModel= ViewModelProvider(this).get(PlanViewModel::class.java)  

// planviewModel의 allData의 변화를 관찰한다.
planViewModel.allData.observe(this, Observer { planList ->  
	planAdapter.data = planList  
	planSzie = planList.size  
	planAdapter.notifyDataSetChanged()  
})  

private fun insertDataToDatabase() {  
	val planTitle = et_plan.text.toString()  
	if(inputCheck(planTitle)){  
		val plan = Plan(id = planSzie, title = planTitle)  
		planViewModel.addPlan(plan)  // plan 추가
		et_plan.setText("")  
	} 
}  
```
```kotlin
class PlanAdapter(private val context: Context, private val planViewModel: PlanViewModel) : RecyclerView.Adapter<PlanViewHolder>() {
	// ...
	// viewHolder에서 Button 클릭 리스너를 통해 데이터 삭제를 구현
	override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
		// ...
		holder.btnDelete.setOnClickListener {  
		  planViewModel.deletePlan(data[position])  
		}
	}
}
	
```