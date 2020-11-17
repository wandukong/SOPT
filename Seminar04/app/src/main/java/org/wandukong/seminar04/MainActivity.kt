package org.wandukong.seminar04

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray

const val REQUEST_SIGNUP = 201;
const val REQUEST_LOGIN = 202;

class MainActivity : AppCompatActivity() {

    private lateinit var member: SharedPreferences
    private val editTextEventListener = EditTextEventListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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

}