package org.wandukong.maskinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import org.wandukong.maskinfo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private lateinit var maskAdapter: MaskAdapter
    private val binding get() = _binding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        maskAdapter = MaskAdapter()
        binding!!.rvMask.adapter = maskAdapter

        viewModel.maskList.observe(this, Observer {
            maskAdapter.updateItems(it as MutableList<ResponseStoreData.Store>)
            supportActionBar?.title = "마스크 재고 있는 곳: " + it.size
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_refresh ->{
                viewModel.fetchMaskInfo()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}