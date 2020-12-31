package org.wandukong.app.network

import org.wandukong.app.sign.data.SigninRequestData
import org.wandukong.app.sign.data.SigninResponseData
import org.wandukong.app.sign.data.SignupRequestData
import org.wandukong.app.sign.data.SignupResponseData
import org.wandukong.app.users.data.LoadUsersResponseData
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