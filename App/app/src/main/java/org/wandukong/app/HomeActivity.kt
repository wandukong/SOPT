package org.wandukong.app

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_home.*
import org.wandukong.app.adapter.HomeViewPagerAdapter
import org.wandukong.app.fragment.ProfileFragment
import org.wandukong.app.fragment.TeamFragment
import org.wandukong.app.fragment.TempFragment
import kotlin.properties.Delegates

class HomeActivity : AppCompatActivity() {
    private lateinit var member: SharedPreferences
    private lateinit var viewPagerAdapter: HomeViewPagerAdapter
    private lateinit var name : String
    private var bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        checkAutoLogin()
        bundle.putString("name", name)      // ProfileFragment로 데이터 보내기
        makeViewPager()
    }

    private fun checkAutoLogin(){
        member = getSharedPreferences("memberDB", MODE_PRIVATE)
        name = member.getString("*LATEST*","").toString()

        if(intent.getBooleanExtra("autoLogin", false)){
            Toast.makeText(this, "${name}님 자동 로그인", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "${name}님 로그인 성공", Toast.LENGTH_SHORT).show()
        }
    }

    private fun makeViewPager(){
        viewPagerAdapter = HomeViewPagerAdapter(supportFragmentManager, bundle)
        vp_home.adapter = viewPagerAdapter

        bnvg_home.setOnNavigationItemSelectedListener {
            var index by Delegates.notNull<Int>()
            when(it.itemId) {
                R.id.menu_profile -> index = 0
                R.id.menu_team -> index= 1
                R.id.menu_temp -> index = 2
            }
            vp_home.currentItem = index
            true
        }

        // 뷰페이저를 슬라이드 했을 때, 그에 대응되는 하단 탭 변경
        vp_home.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                bnvg_home.menu.getItem(position).isChecked = true
                // 나머지 값들은 알아서 false로 바뀜.
            }
        })
    }

    override fun onBackPressed() {  // 뒤로가기 버튼 막기
        //super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(Menu.NONE, Menu.FIRST, Menu.NONE, "LOG OUT")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            Menu.FIRST -> {
                val intent = Intent()
                intent.putExtra("name", member.getString("*LATEST*", ""))
                setResult(Activity.RESULT_OK, intent)

                val preferencesEditor: SharedPreferences.Editor = member.edit()
                preferencesEditor.remove("*LATEST*")
                preferencesEditor.commit()

                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}