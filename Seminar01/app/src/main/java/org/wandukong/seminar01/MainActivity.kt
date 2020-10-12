package org.wandukong.seminar01

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_relative.*
import kotlinx.android.synthetic.main.activity_signup.*

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
            if(!member.getString(et_id_login.text.toString(),"").equals(et_pw_login.text.toString())){
                Toast.makeText(this, "아이디와 비밀번호를 확인하세요.",Toast.LENGTH_SHORT).show()
            }else{
                val preferencesEditor: SharedPreferences.Editor = member.edit()
                preferencesEditor.putString("*LATEST*",et_id_login.text.toString())
                preferencesEditor.commit()

                loginIntent.putExtra("autoLogin",false)
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
                    Toast.makeText(this,"${data?.getStringExtra("id")} 로그아웃 성공",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}