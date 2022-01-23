package com.app.androidinitialproject.ui.dashboard

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.transition.Slide
import com.app.androidinitialproject.AppManager
import com.app.androidinitialproject.R
import com.app.androidinitialproject.databinding.FragmentDashboardBinding
import com.app.androidinitialproject.ui.home.base.HomeBaseFragment
import com.app.androidinitialproject.ui.login.LoginFragment
import com.app.androidinitialproject.ui.profile.ProfileFragment
import com.app.androidinitialproject.ui.ui.home.HomeFragment


class DashboardFragment : HomeBaseFragment<DashboardViewModel, FragmentDashboardBinding>() {
    companion object {
        fun newInstance() = DashboardFragment()
    }
    override fun getToolbarMenuHandler(): ToolbarMenuHandler? = object : ToolbarMenuHandler {
        override val toolbarId: Int
            get() = R.id.toolbar
        override val toolbarTitleId: Int
            get() = R.id.toolbarTitle
        override val menuResource: Int
            get() = 0
        override val toolbarTitle: String
            get() = getString(R.string.home)

        override fun hasMenu(): Boolean = true
        override fun hasBackButton() = false
    }

    override fun getLayoutResource(): Int = R.layout.fragment_dashboard
    override fun getViewModelClass(): Class<DashboardViewModel> = DashboardViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        manageClicks();
        childFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commitNow()
    }

    private fun manageClicks() {
        viewBinding.imgMenu.setOnClickListener {
            viewBinding.drawer.openDrawer(Gravity.LEFT)
        }
        viewBinding.llHome.setOnClickListener {
            viewBinding.drawer.closeDrawer(Gravity.LEFT);
            childFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commitNow()
            viewBinding.toolbarTitle.text = "Home"
        }
        viewBinding.llProfile.setOnClickListener {
            viewBinding.drawer.closeDrawer(Gravity.LEFT)
            childFragmentManager.beginTransaction().replace(R.id.container, ProfileFragment()).commitNow()
            viewBinding.toolbarTitle.text = "Profile"
        }
        viewBinding.llLogout.setOnClickListener {
            viewBinding.drawer.closeDrawer(Gravity.LEFT)
            context?.let {
                    it1 -> AppManager.logoutUser(it1)
            }
        }


    }

    private fun observeLiveData() {
        viewModel.onChooseSiteClicked.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it.getContentIfNotHandled()?.let {
                //fragmentListener?.navigateToSitesFragment()
            }
        })
        viewModel.onTicketLogClicked.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it.getContentIfNotHandled()?.let {
                showToast("Ticket Log Clicked!")
            }
        })
        viewModel.onSyncClicked.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it.getContentIfNotHandled()?.let {
              //  fragmentListener?.navigateToSyncFragment()
            }
        })
        viewModel.onPrinterTestClicked.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it.getContentIfNotHandled()?.let {
                showToast("Printer Test Clicked!")
//                fragmentListener?.navigateToPhotoFoldersFragment()
            }
        })
    }



    private fun showLogoutDialog() {
    }
}