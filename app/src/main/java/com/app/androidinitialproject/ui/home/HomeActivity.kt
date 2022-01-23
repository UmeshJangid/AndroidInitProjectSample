package com.app.androidinitialproject.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.androidinitialproject.AppManager
import com.app.androidinitialproject.R
import com.app.androidinitialproject.databinding.ActivityHomeBinding
import com.app.androidinitialproject.domain.base.BaseActivity
import com.app.androidinitialproject.domain.base.BaseFragment
import com.app.androidinitialproject.service.LocationService
import com.app.androidinitialproject.ui.dashboard.DashboardFragment
import com.app.androidinitialproject.ui.home.base.HomeBaseFragment
import com.app.androidinitialproject.ui.home.base.HomeBaseFragmentListener
import com.app.androidinitialproject.ui.login.LoginFragment
import com.app.androidinitialproject.ui.splash.SplashFragment
import com.app.androidinitialproject.ui.webview.MyWebView
import com.app.androidinitialproject.utility.Utils


class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>(), HomeBaseFragmentListener {


    var container = R.id.container

    override fun getLayoutResource(): Int = R.layout.activity_home
    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null)
            navigateToSplashFragment()
        startService(Intent(this, LocationService::class.java))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val currentFragment: HomeBaseFragment<*, *>? =
            supportFragmentManager.findFragmentById(R.id.container) as HomeBaseFragment<*, *>?
        currentFragment?.onActivityResult(requestCode, resultCode, data)
    }

    fun toast(msg: String?) {
        Toast.makeText(this, Utils.nullCheck(msg), Toast.LENGTH_SHORT).show()
    }

    fun clearFragmentBackStack() {
        try {
            while (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStackImmediate()
            }
        } catch (e: Exception) {
            toast(e.message)
        }
    }

    fun replaceFragment(
        fragment: BaseFragment<*, *>,
        animate: Boolean,
        addToBackStack: Boolean/*clearBackStack: Boolean*/
    ) {
//        if (clearBackStack) clearFragmentBackStack()
        val supportFragmentManager = supportFragmentManager
        val transaction =
            supportFragmentManager.beginTransaction()
        if (animate) transaction.setCustomAnimations(
            R.anim.fade_in,
            R.anim.fade_out,
            R.anim.fade_in,
            R.anim.fade_out
        )
        transaction.replace(container, fragment, fragment.javaClass.simpleName)
        if (addToBackStack) transaction.addToBackStack(fragment.javaClass.simpleName)
        transaction.commitNow()
        //        transaction.commitAllowingStateLoss();
//        supportFragmentManager.executePendingTransactions();
    }

    fun addFragment(
        fragment: Fragment,
        animate: Boolean,
        addToBackStack: Boolean/*clearBackStack: Boolean*/
    ) {
//        if (clearBackStack) clearFragmentBackStack()
        val supportFragmentManager = supportFragmentManager
        val transaction =
            supportFragmentManager.beginTransaction()
        if (addToBackStack) transaction.addToBackStack(fragment.javaClass.simpleName)
        if (animate) transaction.setCustomAnimations(
            R.anim.fade_in,
            R.anim.fade_out,
            R.anim.fade_in,
            R.anim.fade_out
        )
        transaction.add(container, fragment, fragment.javaClass.simpleName)
        transaction.commitNow()
//        transaction.commitAllowingStateLoss()
//        supportFragmentManager.executePendingTransactions()
    }

    override fun logout() {
        AppManager.setUser(null)
        navigateToLoginFragment()
    }


    override fun navigateToLoginFragment() {
        replaceFragment(LoginFragment.newInstance(), true, false)
    }

    override fun navigateToSplashFragment() {
        replaceFragment(SplashFragment.newInstance(), true, false)
    }

    override fun navigateToDashboardFragment() {
        replaceFragment(DashboardFragment.newInstance(), true, false)
    }

    override fun navigateToWebViewFragment() {
        replaceFragment(MyWebView.newInstance(), true, false)
    }

    override val drawerCreationInfo: DrawerCreationInfo?
        get() = null

}