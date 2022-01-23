package com.app.androidinitialproject.ui.home.base

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.app.androidinitialproject.domain.annotations.BaseViewModel
import com.app.androidinitialproject.domain.base.BaseFragment
import com.app.androidinitialproject.ui.home.HomeActivity

abstract class HomeBaseFragment<T : BaseViewModel, B : ViewDataBinding> : BaseFragment<T, B>() {
    var fragmentListener: HomeBaseFragmentListener? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeBaseFragmentListener)
            fragmentListener = context
        else
            throw RuntimeException("$context must implement HomeBaseFragmentListener")
    }

    override fun onDetach() {
        super.onDetach()
        fragmentListener = null
    }


    open fun getFileExtension(url: String): String? {
        var url = url
        return try {
            if (url.indexOf("?") > -1) {
                url = url.substring(0, url.indexOf("?"))
            }
            if (url.lastIndexOf(".") == -1) {
                null
            } else {
                var ext = url.substring(url.lastIndexOf(".") + 1)
                if (ext.indexOf("%") > -1) {
                    ext = ext.substring(0, ext.indexOf("%"))
                }
                if (ext.indexOf("/") > -1) {
                    ext = ext.substring(0, ext.indexOf("/"))
                }
                ext.toLowerCase()
            }
        } catch (e: Exception) {
            ""
        }
    }

    open fun replaceFragment(fragment: BaseFragment<*,*>?, animate: Boolean, addToBackStack: Boolean) {
        try {
            fragment?.let {
                (activity as HomeActivity?)?.replaceFragment(it, animate, addToBackStack)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
    open fun addFragment(fragment: BaseFragment<*,*>?, animate: Boolean, addToBackStack: Boolean) {
        try {
            fragment?.let {
                (activity as HomeActivity?)?.addFragment(it, animate, addToBackStack)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

}