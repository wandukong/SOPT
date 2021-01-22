package org.wandukong.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.wandukong.room.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val databinding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        databinding.lifecycleOwner = this
        databinding.viewModel = mainViewModel

        val planAdapter = PlanAdapter(this, mainViewModel)

        databinding.rcvPlan.apply {
            adapter = planAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        mainViewModel.getAll().observe(this, Observer { planList ->
            planAdapter.data = planList
            planAdapter.notifyDataSetChanged()
            databinding.etPlan.text = null
        })
    }
}