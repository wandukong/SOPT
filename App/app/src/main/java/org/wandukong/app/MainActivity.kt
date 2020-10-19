package org.wandukong.app

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_signup.*
import org.json.JSONArray

const val REQUEST_SIGNUP = 201;
const val REQUEST_LOGIN = 202;

class MainActivity : AppCompatActivity() {

    private lateinit var member: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        member = getSharedPreferences( "memberDB", MODE_PRIVATE)

        val loginIntent = Intent(this, HomeActivity::class.java)

        if(member.contains("*LATEST*")){
            loginIntent.putExtra("autoLogin",true)
            startActivityForResult(loginIntent, REQUEST_LOGIN)
        }

        btn_login_login.setOnClickListener {//TODO 로그인

            var memberJson: String? = member.getString(et_id_login.text.toString(), null) // 아이디로 value를 가지고 온다.

            var memberArray:JSONArray? = null // value를 담을 배열 ["이름", "비밀번호"]
            if(memberJson != null){
                memberArray = JSONArray(memberJson)
                Log.e("result",memberArray.toString())
            }

            if(et_id_login.text.toString().isNullOrBlank() || et_pw_login.text.toString().isNullOrBlank()) {    // 아이디 or 비밀번호 입력 x
                Toast.makeText(this, "빈칸을 채워주세요.",Toast.LENGTH_SHORT).show()
            }else if(memberArray == null){ // 아이디와 일치하는 정보 x
                Toast.makeText(this, "아이디를 확인하세요.",Toast.LENGTH_SHORT).show()
            }else if(et_pw_login.text.toString() != memberArray[1].toString()){ // 아이디에 맞는 비밀번호 x
                Toast.makeText(this, "아이디와 비밀번호를 확인하세요.",Toast.LENGTH_SHORT).show()
            }else{ // 로그인 성공
                val preferencesEditor: SharedPreferences.Editor = member.edit()

                preferencesEditor.putString("*LATEST*",memberArray[0].toString())
                preferencesEditor.commit()

                loginIntent.putExtra("autoLogin",false)
                loginIntent.putExtra("name", memberArray[0].toString())

                startActivityForResult(loginIntent, REQUEST_LOGIN)
            }
        }

        btn_signup_login.setOnClickListener { //TODO  회원가입
            var intent = Intent(this, SignupActivity::class.java)
            startActivityForResult(intent, REQUEST_SIGNUP);
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                REQUEST_SIGNUP->{ //TODO 회원가입
                    Toast.makeText(this,"회원가입 성공",Toast.LENGTH_SHORT).show()
                    et_id_login.setText(data?.getStringExtra("email"))
                    et_pw_login.setText(data?.getStringExtra("password"))
                }
                REQUEST_LOGIN->{ //TODO 로그아웃 - 홈에서 로그아웃할 때, MainActivity로 되돌아온다.
                    Toast.makeText(this,"${data?.getStringExtra("name")}님 로그아웃 성공",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}