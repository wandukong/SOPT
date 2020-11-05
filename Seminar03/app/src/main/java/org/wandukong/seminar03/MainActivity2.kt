package org.wandukong.seminar03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import dalvik.system.DelegateLastClassLoader
import kotlinx.android.synthetic.main.activity_main2.*
import kotlin.properties.Delegates

class MainActivity2 : AppCompatActivity() {
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        vp_sample.adapter = viewPagerAdapter

        bnvg_sample.setOnNavigationItemSelectedListener {
            var index by Delegates.notNull<Int>()
            when(it.itemId) {
                R.id.menu_home -> index = 0
                R.id.menu_search -> index = 1
                R.id.menu_camera -> index = 2
            }
            vp_sample.currentItem = index
            true
        }

        // 뷰페이저를 슬라이드 했을 때, 그에 대응되는 하단 탭 변경

        vp_sample.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                bnvg_sample.menu.getItem(position).isChecked = true
                // 나머지 값들은 알아서 false로 바뀜.
            }
        })

        tl_sample.setupWithViewPager(vp_sample)
        tl_sample.apply{
            getTabAt(0)?.text = "첫 번째"
            getTabAt(1)?.text = "두 번째"
            getTabAt(2)?.text = "세 번째"
        }
    }
}