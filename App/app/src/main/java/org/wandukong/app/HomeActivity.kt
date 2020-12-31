package org.wandukong.app

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_home.*
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
                R.id.menu_search -> index = 2
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

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean { // editText focus 없어지면 키보드 숨기기
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}