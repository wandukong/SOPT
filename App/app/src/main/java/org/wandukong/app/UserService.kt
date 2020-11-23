package org.wandukong.app

import android.content.Context
import android.widget.Toast
import okhttp3.ResponseBody
import org.json.JSONObject
import org.wandukong.app.model.SigninRequestData
import org.wandukong.app.model.SigninResponseData
import org.wandukong.app.model.SignupRequestData
import org.wandukong.app.model.SignupResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

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


}