package org.wandukong.seminar02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var profileAdapter: SampleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        profileAdapter = SampleAdapter(this)

        main_rcv.apply {
            adapter = profileAdapter
            layoutManager=LinearLayoutManager(context)
        }

        // main_rcv.adapter = profileAdapter
        // main_rcv.layoutManager = LinearLayoutManager(this)

        profileAdapter.data = mutableListOf(
            SampleData("이름", "양승완"),
                SampleData("나이", "27"),
                SampleData("이름", "안드로이드"),
                SampleData("이름", "www.github.com/wandukong"),
                SampleData("이름", "www.sopt.org")
        )
    }
}