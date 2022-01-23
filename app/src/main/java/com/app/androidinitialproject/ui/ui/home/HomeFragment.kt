package com.app.androidinitialproject.ui.ui.home

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.app.androidinitialproject.BuildConfig
import com.app.androidinitialproject.R
import com.app.androidinitialproject.service.LocationService
import com.app.androidinitialproject.utility.showToastShort
import com.facebook.FacebookSdk.getApplicationContext
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.theartofdev.edmodo.cropper.CropImage

class HomeFragment : Fragment(), View.OnClickListener {
    private val PERMISSION_REQUEST_CODE = 200
    var startButton: Button? = null
    var stopButton: Button? = null
    var statusTextView: TextView? = null

    var gpsService: LocationService? = null
    var mTracking = false
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //prepare service
        //prepare service
        setWidgetIds(view)

        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                val intent = Intent(context, LocationService::class.java)
                context?.startService(intent)
                context?.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) { showToastShort = "Permission Denied\n$deniedPermissions" }
        }

        TedPermission.with(context)
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("CAMERA and STORAGE permission is required for this functionality.")
            .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            .check()
    }

    fun stopTracking() {
        showToastShort = "stop tracking"
        mTracking = false
        gpsService?.stopTracking()
        toggleButtons()
    }

    private fun openSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri: Uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
        intent.data = uri
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun toggleButtons() {
        startButton!!.isEnabled = !mTracking
        stopButton!!.isEnabled = mTracking
        statusTextView!!.text = if (mTracking) "TRACKING" else "GPS Ready"
    }

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val name = className.className
            if (name.endsWith("LocationService")) {
                gpsService = (service as LocationService.LocationServiceBinder).getService()
                startButton!!.isEnabled = true
                statusTextView!!.text = "GPS Ready"
            }
        }

        override fun onServiceDisconnected(className: ComponentName) {
            if (className.className == "LocationService") {
                gpsService = null
            }
        }
    }

    private fun setWidgetIds(view: View) {
        startButton = view.findViewById(R.id.startButton) as Button
        stopButton = view.findViewById(R.id.stopButton) as Button
        statusTextView = view.findViewById(R.id.statusTextView) as TextView
        startButton!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.startButton -> startTracking()
            R.id.stopButton -> stopTracking()
        }
    }

    fun startTracking() { //check for permission
        if (context?.let { ContextCompat.checkSelfPermission(it, ACCESS_FINE_LOCATION) } == PackageManager.PERMISSION_GRANTED) {
            showToastShort = "start tracking"
            gpsService?.startTracking()
            mTracking = true
            toggleButtons()
        } else {
            activity?.let {
                requestPermissions(it, arrayOf(ACCESS_FINE_LOCATION), PERMISSION_REQUEST_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE)
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                startTracking()
    }


}
