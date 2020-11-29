package org.wandukong.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signup.*
import org.wandukong.app.model.SignupRequestData
import org.wandukong.app.model.SignupResponseData
import org.wandukong.app.service.UserServiceImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {

    private lateinit var member: SharedPreferences
    private val editTextEventListener = EditTextEventListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        editTextEventListener.editTextIsChanged(et_name_signup)
        editTextEventListener.editTextIsChanged(et_id_signup)
        editTextEventListener.editTextIsChanged(et_pw_signup)
        editTextEventListener.editTextIsChanged(et_cpw_signup)

        btn_submit_signup.setOnClickListener {
            //TODO 빈칸있을 경우
            if(et_name_signup.text.toString().isNullOrBlank() || et_id_signup.text.toString().isNullOrBlank() || et_pw_signup.text.toString().isNullOrBlank()  || et_cpw_signup.text.toString().isNullOrBlank() ) {
                Toast.makeText(this, "빈칸을 채워주세요.", Toast.LENGTH_SHORT).show()
            }else if(!et_cpw_signup.text.toString().equals(et_pw_signup.text.toString())) { //TODO 비밀번호 확인이 다른 경우
                Toast.makeText(this, "비밀번호와 비밀번호 확인이 다릅니다.",Toast.LENGTH_SHORT).show()
            }else{

                val call : Call<SignupResponseData> = UserServiceImpl.service.signUp(
                    SignupRequestData(email = et_id_signup.text.toString(), password = et_pw_signup.text.toString(), userName = et_name_signup.text.toString())
                )

                call.enqueue(object : Callback<SignupResponseData>{
                    override fun onResponse(call: Call<SignupResponseData>, response: Response<SignupResponseData>) {

                        response.takeIf {it.isSuccessful}
                            ?.body()
                            ?.let{
                                val intent = Intent()
                                intent.putExtra("email", et_id_signup.text.toString())
                                intent.putExtra("password", et_pw_signup.text.toString())
                                setResult(Activity.RESULT_OK, intent)

                                finish()
                            }
                            ?:let{ // body() 가 null 이거나, request가 실패인 경우
                                UserServiceImpl.showError(this@SignupActivity, response.errorBody())
                            }
                    }
                    override fun onFailure(call: Call<SignupResponseData>, t: Throwable) {
                    }
                })
            }
        }
    }



    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean { // editText focus 없어지면 키보드 숨기기
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}