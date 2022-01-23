package com.app.androidinitialproject.ui.splash

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.annotation.RequiresApi
import com.app.androidinitialproject.AppManager
import com.app.androidinitialproject.R
import com.app.androidinitialproject.databinding.FragmentSplashBinding
import com.app.androidinitialproject.ui.home.base.HomeBaseFragment
import com.app.androidinitialproject.utility.showMessageDialog
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.theartofdev.edmodo.cropper.CropImage
import java.util.ArrayList


class SplashFragment : HomeBaseFragment<SplashViewModel, FragmentSplashBinding>() {

    companion object {
        fun newInstance() = SplashFragment()
    }

    override fun getToolbarMenuHandler(): ToolbarMenuHandler? = null
    override fun getLayoutResource(): Int = R.layout.fragment_splash
    override fun getViewModelClass(): Class<SplashViewModel> = SplashViewModel::class.java

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.image.setImageDrawable(context?.getDrawable(R.drawable.app_icon))

        splashWork()

//        AppManager.deviceID
    }

    private fun splashWork() {
        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                Handler().postDelayed({
                    if (AppManager.isUserLoggedIn()) fragmentListener?.navigateToWebViewFragment()
                    else fragmentListener?.navigateToLoginFragment()
                }, 2000)
            }
            override fun onPermissionDenied(deniedPermissions: List<String>) {
                showToast("Permission Denied\n$deniedPermissions")
            }
        }

        TedPermission.with(context)
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("CAMERA and STORAGE permission is required for this functionality.")
            .setPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
            .check();
    }

}