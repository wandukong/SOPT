package org.wandukong.seminar06

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signin_btn.setOnClickListener{
            val email = email_edit.text.toString()
            val password = password_edit.text.toString()
            val call : Call<ResponseSigninData> = SigninServiceImpl.service.postLogin(
                RequestSigninData(email = email, password = password)
            )
            call.enqueue(object : Callback<ResponseSigninData>{
                override fun onFailure(call: Call<ResponseSigninData>, t: Throwable) {
                    Toast.makeText(this@MainActivity,"Fail",Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(
                    call: Call<ResponseSigninData>,
                    response: Response<ResponseSigninData>
                ) {
                    response.takeIf { it.isSuccessful }
                        ?.body()
                        ?.let { data ->                            // body() null이 아니면 실행
                            Toast.makeText(this@MainActivity,data.message,Toast.LENGTH_SHORT).show()
                        }
                        ?: showError(response.errorBody())      // ?: body() null이거나 실패인 경우 실행
                }
            })
        }
    }

    private fun showError(error : ResponseBody?){
        Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
        val e = error ?: return
        val ob = JSONObject(e.string())
        Toast.makeText(this, ob.getString("message"),Toast.LENGTH_SHORT).show()
    }
}