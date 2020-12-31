package org.wandukong.app.network

import android.content.Context
import android.os.Build
import android.text.Html
import android.widget.Toast
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WebServiceImpl {
    private const val BASE_URL = "https://dapi.kakao.com/"

    private val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val service : WebService = retrofit.create(WebService::class.java)

    fun showError(context: Context, error : ResponseBody?){
        val e = error ?: return
        val ob = JSONObject(e.string())
        Toast.makeText(context, ob.getString("message"), Toast.LENGTH_SHORT).show()
    }

    fun removeHTMLTag(str : String) : String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(str, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            Html.fromHtml(str).toString()
        }
    }
}