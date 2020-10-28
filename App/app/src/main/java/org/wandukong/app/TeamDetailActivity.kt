package org.wandukong.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_project_detail.*

class TeamDetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_detail)

        var team = intent.getParcelableExtra<TeamData>("team")
        tv_title_project_detail.text = team.title
        tv_subtitle_project_detail.text= team.subTitle
        tv_date_poject_detail.text =  team.date
        tv_detail_project_detail.text = team.detail
    }
}