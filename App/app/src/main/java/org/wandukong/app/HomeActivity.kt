package org.wandukong.app

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var member: SharedPreferences
    private lateinit var teamAdapter: TeamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        member = getSharedPreferences("memberDB", MODE_PRIVATE)

        if(getIntent().getBooleanExtra("autoLogin",false)){
            Toast.makeText(this,"${member.getString("*LATEST*", "")}님 자동 로그인",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this,"${member.getString("*LATEST*", "")}님 로그인 성공",Toast.LENGTH_SHORT).show()
        }

        teamAdapter = TeamAdapter(this)

        rcv_teamList_home.adapter = teamAdapter
        rcv_teamList_home.layoutManager = GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
        //project_rcv.layoutManager = LinearLayoutManager(this)

        teamAdapter.data = mutableListOf(
            TeamData("Manchester United", "Winner","1878-03-05","Red Devils"),
            TeamData("Tottenham Hotspur", "Runner up","1905-03-10","Spurs"),
            TeamData("Liverpool", "3rd","1892-03-15","Reds"),
            TeamData("Manchester city", "4th","1894-04-16","Blues"),
            TeamData("Chelsea", "5th","1905-03-10","The Blues")
        )

        btn_logout_home.setOnClickListener {
            val intent = Intent()
            intent.putExtra("name", member.getString("*LATEST*", ""))
            setResult(Activity.RESULT_OK, intent)

            val preferencesEditor: SharedPreferences.Editor = member.edit()
            preferencesEditor.remove("*LATEST*")
            preferencesEditor.commit()

            finish()
        }
    }

    override fun onBackPressed() {  // 뒤로가기 버튼 막기
        //super.onBackPressed()
    }
}