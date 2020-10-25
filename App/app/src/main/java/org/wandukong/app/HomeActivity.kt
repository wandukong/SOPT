package org.wandukong.app

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private lateinit var member: SharedPreferences
    private lateinit var teamAdapter: TeamAdapter
    private var teamList = mutableListOf<TeamData>()
    private lateinit var touchHelper : ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        button15.setOnClickListener {

        }
        checkAutoLogin()
        createRecyclerView()

        btn_logout_home.setOnClickListener { // 로그아웃
            val intent = Intent()
            intent.putExtra("name", member.getString("*LATEST*", ""))
            setResult(Activity.RESULT_OK, intent)

            val preferencesEditor: SharedPreferences.Editor = member.edit()
            preferencesEditor.remove("*LATEST*")
            preferencesEditor.commit()

            finish()
        }
    }

    private fun checkAutoLogin(){
        member = getSharedPreferences("memberDB", MODE_PRIVATE)

        if(intent.getBooleanExtra("autoLogin", false)){
            Toast.makeText(this, "${member.getString("*LATEST*", "")}님 자동 로그인", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "${member.getString("*LATEST*", "")}님 로그인 성공", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createRecyclerView(){
        teamList.add(TeamData("Manchester United", "Winner", "1878-03-05", "Red Devils"))
        teamList.add(TeamData("Tottenham Hotspur", "Runner up", "1905-03-10", "Spurs"))
        teamList.add(TeamData("Liverpool", "3rd", "1892-03-15", "Reds"))
        teamList.add(TeamData("Manchester city", "4th", "1894-04-16", "Blues"))
        teamList.add(TeamData("Chelsea", "5th", "1905-03-10", "The Blues"))

        touchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val fromPosition: Int = viewHolder.layoutPosition
                val toPosition: Int = target.layoutPosition

                val moveData = teamAdapter.data[fromPosition]
                teamAdapter.data.removeAt(fromPosition)
                teamAdapter.data.add(toPosition, moveData)

                teamAdapter.notifyItemMoved(fromPosition, toPosition)

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                teamAdapter.data.removeAt(viewHolder.layoutPosition)
                teamAdapter.notifyItemRemoved(viewHolder.layoutPosition)
            }
        })

        teamAdapter = TeamAdapter(this, touchHelper)
        teamAdapter.data = teamList

        rcv_teamList_team.apply {
            layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
            //layoutManager = LinearLayoutManager(context)
            adapter = teamAdapter
        }
        teamAdapter.notifyDataSetChanged()
        touchHelper.attachToRecyclerView(rcv_teamList_team)
    }

    override fun onBackPressed() {  // 뒤로가기 버튼 막기
        //super.onBackPressed()
    }
}