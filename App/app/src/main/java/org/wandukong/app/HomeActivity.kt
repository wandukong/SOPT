package org.wandukong.app

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private lateinit var member: SharedPreferences
    private lateinit var teamAdapter: TeamAdapter
    private var teamList = mutableListOf<TeamData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        checkAutoLogin()
        createRecyclerView()

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
        teamList.add(TeamData("Arsenal", "6th", "1886-10-01", "The Gunners"))
        teamList.add(TeamData("Leicester city", "7th", "18884-01-01", "The Foxes"))
        teamList.add(TeamData("Everton", "8th", "1878-01-01", "The Toffees"))

        teamAdapter = TeamAdapter(this)
        teamAdapter.touchHelper = ItemTouchHelper(ItemTouchCallback(teamAdapter))

        teamAdapter.data = teamList

        rcv_teamList_team.apply {
            //layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
            layoutManager = LinearLayoutManager(context)
            adapter = teamAdapter
        }
        teamAdapter.notifyDataSetChanged()
        teamAdapter.touchHelper.attachToRecyclerView(rcv_teamList_team)
    }

    override fun onBackPressed() {  // 뒤로가기 버튼 막기
        //super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menu?.add(Menu.NONE, Menu.FIRST, Menu.NONE, "LOG OUT")

        var subMenu: Menu? = menu?.addSubMenu("SORT")
        subMenu?.add(Menu.NONE, Menu.FIRST + 1, Menu.NONE, "List")
        subMenu?.add(Menu.NONE, Menu.FIRST + 2, Menu.NONE, "Grid")

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            Menu.FIRST -> {
                val intent = Intent()
                intent.putExtra("name", member.getString("*LATEST*", ""))
                setResult(Activity.RESULT_OK, intent)

                val preferencesEditor: SharedPreferences.Editor = member.edit()
                preferencesEditor.remove("*LATEST*")
                preferencesEditor.commit()

                finish()
            }
            Menu.FIRST + 1 -> {
                teamAdapter.viewType = 1
                rcv_teamList_team.layoutManager = LinearLayoutManager(this)
            }
            Menu.FIRST + 2 -> {
                teamAdapter.viewType = 2
                rcv_teamList_team.layoutManager = GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}