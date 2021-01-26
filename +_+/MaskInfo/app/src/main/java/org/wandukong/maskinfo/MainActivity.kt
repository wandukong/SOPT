package org.wandukong.maskinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import org.wandukong.maskinfo.MaskServiceImpl.customEnqueue
import org.wandukong.maskinfo.databinding.ActivityMainBinding
import retrofit2.Call

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private lateinit var maskAdapter: MaskAdapter
    private val binding get() = _binding
    private val maskList = mutableListOf<MaskData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        maskAdapter = MaskAdapter()
        binding!!.rvMask.adapter = maskAdapter
        maskList.add(MaskData("솔약국", "경기도 고양시", "4.3km", "양호", "35"))
        maskAdapter.updateItems(maskList)


//        val service: Call<ResponseStoreData> = MaskServiceImpl.service.getMaskInfo(36.5, 143.3)
//        service.customEnqueue(
//            onSuccess = {
//
//            },
//            onError = {
//
//            }
//        )
    }
}