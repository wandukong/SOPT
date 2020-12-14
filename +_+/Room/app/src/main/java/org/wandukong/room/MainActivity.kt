package org.wandukong.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var planViewModel : PlanViewModel
    //private  var planSize = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        planViewModel= ViewModelProvider(this).get(PlanViewModel::class.java)

        val planAdapter = PlanAdapter(this, planViewModel)

        rcv_plan.apply {
            adapter = planAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        planViewModel.allData.observe(this, Observer { planList ->
            planAdapter.data = planList
            //planSize = planList.size
            planAdapter.notifyDataSetChanged()
        })

        btn_add_plan.setOnClickListener {
            insertDataToDatabase()
        }
    }

    private fun insertDataToDatabase() {
        val planTitle = et_plan.text.toString()

        if(inputCheck(planTitle)){
            val plan = Plan(planTitle)
            planViewModel.addPlan(plan)
            et_plan.setText("")
        }
    }

    private fun inputCheck(planTitle : String): Boolean {
        return !(planTitle.isNullOrEmpty())
    }
}