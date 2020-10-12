package org.wandukong.seminar01

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var member: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        member = getSharedPreferences("memberDB", MODE_PRIVATE)

        if(getIntent().getBooleanExtra("autoLogin",false)){
            Toast.makeText(this,"${member.getString("*LATEST*","")} 자동 로그인",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this,"${member.getString("*LATEST*","")} 로그인 성공",Toast.LENGTH_SHORT).show()
        }

        btn_logout_home.setOnClickListener {

            val intent = Intent()
            intent.putExtra("id", member.getString("*LATEST*",""))
            setResult(Activity.RESULT_OK, intent)

            val preferencesEditor: SharedPreferences.Editor = member.edit()
            preferencesEditor.remove("*LATEST*")
            preferencesEditor.commit()

            finish()
        }


    }
}