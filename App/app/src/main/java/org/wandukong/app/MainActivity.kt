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
import kotlinx.android.synthetic.main.activity_main.*
import org.wandukong.app.R
import org.wandukong.app.sign.data.SigninRequestData
import org.wandukong.app.sign.data.SigninResponseData
import org.wandukong.app.network.UserServiceImpl
import org.wandukong.app.sign.SignupActivity
import org.wandukong.app.util.EditTextEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val REQUEST_SIGNUP = 201;
const val REQUEST_LOGIN = 202;

class MainActivity : AppCompatActivity() {

    private lateinit var member: SharedPreferences
    private val editTextEventListener = EditTextEventListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        member = getSharedPreferences( "memberDB", MODE_PRIVATE)

        val loginIntent = Intent(this, HomeActivity::class.java)

        if(member.contains("*LATEST*")){
            startActivityForResult(loginIntent, REQUEST_LOGIN)
        }

        btn_login_login.setOnClickListener {//TODO 로그인

            if(et_id_login.text.toString().isNullOrBlank() || et_pw_login.text.toString().isNullOrBlank()) {    // 아이디 or 비밀번호 입력 x
                Toast.makeText(this, "빈칸을 채워주세요.",Toast.LENGTH_SHORT).show()
            }else{
                val call : Call<SigninResponseData> = UserServiceImpl.service.signIn(
                        SigninRequestData(email = et_id_login.text.toString(), password = et_pw_login.text.toString())
                )
                call.enqueue(object : Callback<SigninResponseData>{
                    override fun onResponse(call: Call<SigninResponseData>, response: Response<SigninResponseData>) {

                        response.takeIf { response.isSuccessful }  // TODO 회원가입 성공
                                ?.body()
                                ?.let { signinData ->

                                    val preferencesEditor: SharedPreferences.Editor = member.edit()
                                    preferencesEditor.putString("*LATEST*", signinData.data.userName)
                                    preferencesEditor.commit()

                                    startActivityForResult(loginIntent, REQUEST_LOGIN)

                                } ?: UserServiceImpl.showError(this@MainActivity, response.errorBody())  // TODO 회원가입 실패
                    }
                    override fun onFailure(call: Call<SigninResponseData>, t: Throwable) {
                    }
                })
            }
        }

        tv_signup_login.setOnClickListener { //TODO  회원가입
            var intent = Intent(this, SignupActivity::class.java)
            startActivityForResult(intent, REQUEST_SIGNUP);
        }

        editTextEventListener.editTextIsChanged(et_id_login)
        editTextEventListener.editTextIsChanged(et_pw_login)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_SIGNUP -> { //TODO 회원가입
                    Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                    et_id_login.setText(data?.getStringExtra("email"))
                    et_pw_login.setText(data?.getStringExtra("password"))
                }
                REQUEST_LOGIN -> { //TODO 로그아웃 - 홈에서 로그아웃할 때, MainActivity로 되돌아온다.
                    Toast.makeText(
                        this,
                        "${data?.getStringExtra("name")}님 로그아웃 성공",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {    // editText focus 없어지면 키보드 숨기기
        if (currentFocus != null) {
            val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}