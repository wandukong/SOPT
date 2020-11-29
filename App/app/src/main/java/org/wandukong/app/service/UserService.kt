package org.wandukong.app.service

import android.content.Context
import android.widget.Toast
import okhttp3.ResponseBody
import org.json.JSONObject
import org.wandukong.app.model.*
import retrofit2.Call
import retrofit2.http.*

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