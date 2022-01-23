package com.app.androidinitialproject.domain.base

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.ViewDataBinding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import com.app.androidinitialproject.domain.annotations.BaseViewModel

abstract class BaseDrawerFragment<T : BaseViewModel, B : ViewDataBinding> : BaseFragment<T, B>() {

    private var drawerToggle: ActionBarDrawerToggle? = null
    abstract var drawerCreationInfo: DrawerCreationInfo

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDrawer()
    }

    fun initDrawer() {
        drawerCreationInfo?.let { info ->
            if (!info.overrideInitialization()) {
                getDrawerToggle()?.let {
                    info.drawerLayout.addDrawerListener(it)
                    it.isDrawerIndicatorEnabled = true
                }
                info.drawerRecyclerView
                    .setLayoutManager(info.drawerRecyclerLayoutManager)
                info.drawerRecyclerView
                    .setAdapter(info.drawerRecyclerAdapter)
            }
        }
        if (drawerCreationInfo != null) getDrawerToggle()?.syncState()
    }

    fun getDrawerToggle(): ActionBarDrawerToggle? {
        if (drawerToggle == null)
            drawerToggle = object : ActionBarDrawerToggle(
                activity,
                drawerCreationInfo.drawerLayout,
                drawerCreationInfo.drawerOpenContentDesc,
                drawerCreationInfo.drawerCloseContentDesc
            ) {
//                override fun onDrawerClosed(view: View?) {
//                    super.onDrawerClosed(view)
//                }
//
//                override fun onDrawerOpened(drawerView: View?) {
//                    super.onDrawerOpened(drawerView)
//                }
//
//                override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {
//                    super.onDrawerSlide(drawerView, slideOffset)
//                    //                    animateImageUser(slideOffset);
//                }
            }
        return drawerToggle
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerCreationInfo?.let {
            getDrawerToggle()?.onConfigurationChanged(newConfig)
        }
    }

    private fun closeDrawer() {
        if (drawerCreationInfo != null && drawerCreationInfo.drawerLayout.isDrawerOpen(
                drawerCreationInfo.drawerGravity
            )
        )
            drawerCreationInfo.drawerLayout.closeDrawer(drawerCreationInfo.drawerGravity)
    }

    private fun openDrawer() {
        if (drawerCreationInfo != null)
            drawerCreationInfo.drawerLayout.openDrawer(drawerCreationInfo.drawerGravity)
    }

    private fun openCloseDrawer() {
        if (drawerCreationInfo != null) {
            if (drawerCreationInfo.drawerLayout.isDrawerOpen(drawerCreationInfo.drawerGravity))
                closeDrawer()
            else
                openDrawer()
        }
    }

    interface DrawerCreationInfo {

        val drawerRecyclerView: RecyclerView

        val drawerRecyclerLayoutManager: RecyclerView.LayoutManager

        val drawerRecyclerAdapter: RecyclerView.Adapter<*>

        @get:Slide.GravityFlag
        val drawerGravity: Int

        @get:StringRes
        val drawerOpenContentDesc: Int

        @get:StringRes
        val drawerCloseContentDesc: Int

        val drawerLayout: DrawerLayout

        // if you want to manually call initDrawer()
        fun overrideInitialization(): Boolean
    }
}