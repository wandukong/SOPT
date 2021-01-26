package org.wandukong.maskinfo

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MaskServiceImpl {
    private const val BASE_URL = "https://gist.githubusercontent.com/junsuk5/bb7485d5f70974deee920b8f0cd1e2f0/raw/063f64d9b343120c2cb01a6555cf9b38761b1d94/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(MaskService::class.java)

    fun<ResponseType> Call<ResponseType>.customEnqueue(
        onSuccess : (ResponseType) -> Unit,
        onFail : () -> Unit,
        onError : (ResponseBody?) -> Unit = {}
    ){
        this.enqueue( object : Callback<ResponseType> {
            override fun onFailure(call: Call<ResponseType>, t: Throwable) {
                onFail()
            }
            override fun onResponse(call: Call<ResponseType>, response: Response<ResponseType>) {
                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let {
                        onSuccess(it)
                    }?:onError(response.errorBody())
            }
        })
    }
}