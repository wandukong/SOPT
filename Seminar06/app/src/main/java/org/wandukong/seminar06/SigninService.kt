package org.wandukong.seminar06

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SigninService{
    @Headers("Content-Type:application/json")
    @POST("users/signin") // 자원 식별 경로
    fun postLogin(
        @Body body : RequestSigninData

    ) : Call<ResponseSigninData>
}