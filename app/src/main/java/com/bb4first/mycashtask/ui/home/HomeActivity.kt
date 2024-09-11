package com.bb4first.mycashtask.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bb4first.mycashtask.R
import com.bb4first.mycashtask.base.BaseActivity
import com.bb4first.mycashtask.databinding.ActivityHomeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override val bindingFactory: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate

    override val enableEdgeToEdgeToThisActivity: Boolean = false

    private var callback: MyCashTaskLocationCallBack? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun initialization() {
    }

    fun startGetLocation(callback0: MyCashTaskLocationCallBack) {
        this.callback = callback0
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        callback?.onLocationReceived(location)
                    } else {
                        callback?.onFail(
                            getString(R.string.unable_to_detect_location)
                        )
                    }
                }
                .addOnFailureListener {
                    callback?.onFail(
                        it.message ?: getString(
                            R.string.unable_to_detect_location
                        )
                    )
                }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    interface MyCashTaskLocationCallBack {
        fun onLocationReceived(location: Location)
        fun onFail(error: String)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE &&
            grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            startGetLocation(callback!!)
        } else {
            callback?.onFail(
                getString(R.string.permission_denied)
            )
        }
    }

    override fun setListeners() {
    }

}