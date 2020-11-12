package org.wandukong.app

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_signup.*
import org.json.JSONArray

class SignupActivity : AppCompatActivity() {

    private lateinit var member: SharedPreferences
    private val editTextEventListener = EditTextEventListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        member = getSharedPreferences("memberDB", MODE_PRIVATE)

        editTextEventListener.editTextIsChanged(et_name_signup)
        editTextEventListener.editTextIsChanged(et_id_signup)
        editTextEventListener.editTextIsChanged(et_pw_signup)
        editTextEventListener.editTextIsChanged(et_cpw_signup)

        btn_submit_signup.setOnClickListener {
            //TODO 빈칸있을 경우
            if(et_name_signup.text.toString().isNullOrBlank() || et_id_signup.text.toString().isNullOrBlank() || et_pw_signup.text.toString().isNullOrBlank()  || et_cpw_signup.text.toString().isNullOrBlank() ) {
                Toast.makeText(this, "빈칸을 채워주세요.", Toast.LENGTH_SHORT).show()
            }else if(member.contains(et_id_signup.text.toString())){
                Toast.makeText(this, "이미 존재하는 ID입니다.",Toast.LENGTH_SHORT).show()
            }else if(!et_cpw_signup.text.toString().equals(et_pw_signup.text.toString())) { //TODO 비밀번호 확인이 다른 경우
                Toast.makeText(this, "비밀번호와 비밀번호 확인이 다릅니다.",Toast.LENGTH_SHORT).show()
            }else{ //TODO 회원가입 성공
                val intent = Intent()
                intent.putExtra("email",et_id_signup.text.toString())
                intent.putExtra("password",et_pw_signup.text.toString())
                setResult(Activity.RESULT_OK, intent)


                val memberArray = JSONArray()
                memberArray.put(et_name_signup.text.toString())
                memberArray.put(et_pw_signup.text.toString())

                val preferencesEditor: SharedPreferences.Editor = member.edit()
                preferencesEditor.putString(et_id_signup.text.toString(), memberArray.toString())

                preferencesEditor.commit()
                finish()
            }
        }
    }
}