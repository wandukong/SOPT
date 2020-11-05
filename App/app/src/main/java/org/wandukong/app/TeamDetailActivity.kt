package org.wandukong.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.wandukong.app.model.TeamData

class TeamDetailActivity : AppCompatActivity() {

    private lateinit var team: TeamData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        team = intent.getParcelableExtra("team")
        tv_title_team_detail.text = team.title
        tv_subtitle_team_detail.text= team.subTitle
        tv_date_team_detail.text =  team.date
        tv_detail_team_detail.text = team.detail
    }
}