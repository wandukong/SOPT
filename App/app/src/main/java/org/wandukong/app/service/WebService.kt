package org.wandukong.app.service

import org.wandukong.app.model.WebSearchResponseData
import retrofit2.Call
import retrofit2.http.*

interface WebService {

    @Headers("Authorization:KakaoAK 181f5e5c475eb6a7f7a4e535f7e8e783")
    @GET("/v2/search/web")
    fun webSearch(
            @Query("query") query : String,
            @Query("sort") sort : String? = "accuracy",
            @Query("page") page : Int? = 1,
            @Query("size") size : Int? = 10,
    ) : Call<WebSearchResponseData>
}