package org.wandukong.seminar01

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    private lateinit var member: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        member = getSharedPreferences("memberDB", MODE_PRIVATE)

        btn_submit_signup.setOnClickListener {
            
            //TODO 빈칸있을 경우
            if(et_namme_signup.text.toString().isNullOrBlank() || et_id_signup.text.toString().isNullOrBlank() || et_pw_signup.text.toString().isNullOrBlank()  || et_cpw_signup.text.toString().isNullOrBlank() )
                Toast.makeText(this, "빈칸을 채워주세요.",Toast.LENGTH_SHORT).show()
            else if(!et_cpw_signup.text.toString().equals(et_pw_signup.text.toString())) { //TODO 비밀번호 확인이 다른 경우
                Toast.makeText(this, "비밀번호와 비밀번호 확인이 다릅니다.",Toast.LENGTH_SHORT).show()
            }else{ //TODO 회원가입 성공
                val intent = Intent()
                intent.putExtra("email",et_id_signup.text.toString())
                intent.putExtra("password",et_pw_signup.text.toString())
                setResult(Activity.RESULT_OK, intent)


                val preferencesEditor: SharedPreferences.Editor = member.edit()
                preferencesEditor.putString(et_id_signup.text.toString(),et_pw_signup.text.toString())

                preferencesEditor.commit()
                finish()
            }
        }
    }
}