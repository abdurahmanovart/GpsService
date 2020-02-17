package ai.arturxdroid.gpsservice

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE = 100

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        )
            getGpsPermission()
        else
            initButton()
    }

    private fun getGpsPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            initButton()
    }

    private fun initButton() {
        button.isChecked = !(viewModel.isServiceRunning.value ?: false)

        button.setOnClickListener {
            if (button.isChecked) {
                startGpsService()
                viewModel.isServiceRunning.value = true
            } else {
                stopGpsService()
                viewModel.isServiceRunning.value = false
            }
        }
        button.isEnabled = true
    }

    private fun startGpsService() {
        startService(Intent(this, GpsService::class.java))

    }

    private fun stopGpsService() {
        stopService(Intent(this, GpsService::class.java))
    }
}
