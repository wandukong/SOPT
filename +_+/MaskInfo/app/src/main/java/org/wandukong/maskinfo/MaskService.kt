package org.wandukong.maskinfo

import retrofit2.http.GET
import retrofit2.http.Query

interface MaskService {
    @GET("sample.json")
    suspend fun getMaskInfo( // suspend : 비동기로 동작할 코드를 나타낸다.
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
    ): ResponseStoreData
    // retrofit에서 kotlin을 공식적으로 지원하기 때문에 Call 객체로 받을 필요가 없다.
}