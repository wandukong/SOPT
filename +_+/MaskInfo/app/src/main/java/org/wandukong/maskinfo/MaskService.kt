package org.wandukong.maskinfo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MaskService {

    @GET("sample.json")
    fun getMaskInfo(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double
    ) : Call<ResponseStoreData>
}