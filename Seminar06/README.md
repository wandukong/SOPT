# 📣Seminar06 서버 통신(Retrofit)
**작성일자 : 2020.11.29**

## 📱결과 화면

<img src="https://user-images.githubusercontent.com/47289479/101166576-d8bbdd80-367b-11eb-8d94-81e3cf5df71a.JPG" width="495" height="300"/>
<img src="https://user-images.githubusercontent.com/47289479/101166614-eb361700-367b-11eb-93ed-5bf40d8562d9.JPG" width="495" height="300"/>
POSTMAN TEST  

<p float="left">
	<img src="https://user-images.githubusercontent.com/47289479/101168306-9b0c8400-367e-11eb-8900-ba9caaa0f7f2.gif" width="260" height="430"/>
	<img src="https://user-images.githubusercontent.com/47289479/100543365-088d6e80-3293-11eb-8206-cf240eb52023.gif" width="260" height="430"/>
</p>
구현 결과 화면  

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