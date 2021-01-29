package org.wandukong.maskinfo

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.wandukong.maskinfo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private lateinit var maskAdapter: MaskAdapter
    private val binding get() = _binding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        private val REQUIRED_PERMISSIONS =
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        private const val REQUEST_CODE_PERMISSION_LOCATION = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        requestLocation()

        viewModel.apply {
            loadingData.observe(this@MainActivity, Observer { isLoading ->
                binding!!.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            })
            maskList.observe(this@MainActivity, Observer { maskList ->
                maskAdapter.updateItems(maskList as MutableList<ResponseStoreData.Store>)
                supportActionBar?.title = "마스크 재고 있는 곳: " + maskList.size
            })
        }
    }

    private fun makeMaskList() {
        maskAdapter = MaskAdapter()
        binding!!.rvMask.adapter = maskAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_refresh -> {
                requestLocation()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun requestLocation() {
        if (allPermissionGranted()) {
            loadLocation()
            return
        }
        requestPermission()
    }

    @SuppressLint("MissingPermission")
    private fun loadLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    viewModel.setLatitude(location.latitude)
                    viewModel.setLongitude(location.longitude)
                    viewModel.fetchMaskInfo()
                    makeMaskList()
                }
            }
    }

    private fun allPermissionGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            applicationContext,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            REQUIRED_PERMISSIONS,
            REQUEST_CODE_PERMISSION_LOCATION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION_LOCATION -> permissionResponseEvent()
        }
    }

    private fun permissionResponseEvent() {
        if (allPermissionGranted()) {
            loadLocation()
            return
        }
        permissionDeniedEvent()
    }

    private fun permissionDeniedEvent() {
        Toast.makeText(this, "Permission Denied!!!", Toast.LENGTH_SHORT).show()
        viewModel.setLoadingData(false)
    }
}