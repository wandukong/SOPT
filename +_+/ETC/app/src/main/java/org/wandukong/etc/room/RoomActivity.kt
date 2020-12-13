package org.wandukong.etc.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_room.*
import org.wandukong.etc.R

class RoomActivity : AppCompatActivity() {

    private lateinit var planViewModel: PlanViewModel
    var planSzie : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        val planAdapter = PlanAdapter(this)
        rcv_plan.apply {
            adapter = planAdapter
            layoutManager = LinearLayoutManager(this@RoomActivity)
        }

        planViewModel = ViewModelProvider(this).get(PlanViewModel::class.java)
        planViewModel.allData.observe(this, Observer { planList ->
            planAdapter.data = planList
            planSzie = planList.size
            planAdapter.notifyDataSetChanged()
        })


        btn_add.setOnClickListener {
            insertDataToDatabase()
        }
    }

    private fun insertDataToDatabase() {
        val planTitle = et_plan.text.toString()
        
        if(inputCheck(planTitle)){
            val plan = Plan(id = planSzie, title = planTitle)
            planViewModel.addPlan(plan)
        }
    }

    private fun inputCheck(planTitle : String): Boolean {
        return !(planTitle.isNullOrEmpty())
    }
}