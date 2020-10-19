package org.wandukong.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_project_detail.*

class TeamDetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_detail)

        tv_title_project_detail.setText(intent.getStringExtra("title"))
        tv_subtitle_project_detail.setText(intent.getStringExtra("subTitle"))
        tv_date_poject_detail.setText(intent.getStringExtra("date"))
        tv_detail_project_detail.setText(intent.getStringExtra("detail"))

    }
}