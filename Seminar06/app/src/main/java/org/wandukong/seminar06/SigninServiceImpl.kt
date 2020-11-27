package org.wandukong.seminar06

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SigninServiceImpl {
    private const val BASE_URL = "http://15.164.83.210:3000/"

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service : SigninService = retrofit.create(SigninService::class.java)
}