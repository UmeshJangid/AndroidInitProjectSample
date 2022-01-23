package com.app.androidinitialproject.domain.base

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide.GravityFlag

abstract class BaseDrawerActivity : AppCompatActivity() {
    //                    animateImageUser(slideOffset);
    var drawerToggle: ActionBarDrawerToggle? = null
        get() {
            if (field == null) field = object : ActionBarDrawerToggle(
                this,
                drawerCreationInfo!!.drawerLayout,
                drawerCreationInfo!!.drawerOpenContentDesc,
                drawerCreationInfo!!.drawerCloseContentDesc
            ) {
                override fun onDrawerClosed(view: View) {
                    super.onDrawerClosed(view)
                }

                override fun onDrawerOpened(drawerView: View) {
                    super.onDrawerOpened(drawerView)
                }

                override fun onDrawerSlide(
                    drawerView: View,
                    slideOffset: Float
                ) {
                    super.onDrawerSlide(drawerView, slideOffset)
                    //                    animateImageUser(slideOffset);
                }
            }
            return field
        }
        private set
    abstract val drawerCreationInfo: DrawerCreationInfo?
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initDrawer()
    }

    fun initDrawer() {
        if (drawerCreationInfo != null && !drawerCreationInfo!!.overrideInitialization()) {
            drawerCreationInfo!!.drawerLayout.addDrawerListener(drawerToggle!!)
            drawerToggle!!.isDrawerIndicatorEnabled = true
            drawerCreationInfo!!.drawerRecyclerView.layoutManager =
                drawerCreationInfo!!.drawerRecyclerLayoutManager
            drawerCreationInfo!!.drawerRecyclerView.adapter =
                drawerCreationInfo!!.drawerRecyclerAdapter
        }
        if (drawerCreationInfo != null) drawerToggle!!.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (drawerCreationInfo != null) drawerToggle!!.onConfigurationChanged(newConfig)
    }

    private fun closeDrawer() {
        if (drawerCreationInfo != null
            && drawerCreationInfo!!.drawerLayout.isDrawerOpen(drawerCreationInfo!!.drawerGravity)
        ) drawerCreationInfo!!.drawerLayout.closeDrawer(drawerCreationInfo!!.drawerGravity)
    }

    private fun openDrawer() {
        if (drawerCreationInfo != null) drawerCreationInfo!!.drawerLayout.openDrawer(
            drawerCreationInfo!!.drawerGravity
        )
    }

    private fun openCloseDrawer() {
        if (drawerCreationInfo != null) {
            if (drawerCreationInfo!!.drawerLayout.isDrawerOpen(drawerCreationInfo!!.drawerGravity)) closeDrawer() else openDrawer()
        }
    }

    interface DrawerCreationInfo {
        val drawerRecyclerView: RecyclerView
        val drawerRecyclerLayoutManager: RecyclerView.LayoutManager?
        val drawerRecyclerAdapter: RecyclerView.Adapter<*>?
        @get:GravityFlag
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