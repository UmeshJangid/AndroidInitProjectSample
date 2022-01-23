package com.app.androidinitialproject.ui.profile

import android.text.TextUtils
import com.app.androidinitialproject.AppManager
import com.app.androidinitialproject.R
import com.app.androidinitialproject.domain.annotations.BaseViewModel
import com.app.androidinitialproject.domain.annotations.InputErrorType
import com.app.androidinitialproject.domain.base.BaseFragment
import com.app.androidinitialproject.domain.base.SingleActionEvent
import kotlinx.coroutines.launch

class ProfileViewModel : BaseViewModel() {

    lateinit var viewActions: ProfileFragment.ViewActions
    var firstName: String = ""
    var lastName: String = ""
    var email: String = ""
    var mobileNumber: String = ""


    /**
     * Use this method when you have have to get profile info on the profile fragment
     * complete profile object
     * */

/*
    fun getUserProfileDetails() {
        uiScope.launch {
            val res = UserProfileRepo(repoListener).requestUserProfile()
            res?.apply {
                if (this.status) {
                    viewActions.setProfileDetails(res.data)
                } else showToast.value = SingleActionEvent(this.message)
            }
        }
    }
*/

    /**
     * Use this method when you have have to update profile info
     * @param firstName
     * @param LastName
     * @param mobile
     * */

/*
    fun updateUserProfileDetails(firstName: String, lastName: String, mobile: String) {
        if (AppManager.hasDeviceIdForAPIs())
            AppManager.getDeviceIdForAPIs()?.let {
                uiScope.launch {
                    val res = UserProfileRepo(repoListener).requestUpdateUserProfile(
                        firstName,
                        lastName,
                        mobile,
                        it
                    )
                    res?.apply {
                        if (this.status) {
                            viewActions.onUserProfileUpdated(res.message)
                        } else showToast.value = SingleActionEvent(this.message)
                    }
                }
            }
    }
*/

    fun onUpdateProfileClicked() {
        closeKeyBoard()
        if (TextUtils.isEmpty(firstName)) {
            showInputError.value = SingleActionEvent(
                BaseFragment.InputError(
                    InputErrorType.FIRST_NAME,
                    AppManager.getString(R.string.err_first_name_empty)
                )
            )
            return
        }
        if (TextUtils.isEmpty(lastName)) {
            showInputError.value = SingleActionEvent(
                BaseFragment.InputError(
                    InputErrorType.LAST_NAME,
                    AppManager.getString(R.string.err_last_name_empty)
                )
            )
            return
        }
        if (TextUtils.isEmpty(mobileNumber)) {
            showInputError.value = SingleActionEvent(
                BaseFragment.InputError(
                    InputErrorType.MOBILE,
                    AppManager.getString(R.string.err_mobile_empty)
                )
            )
            return
        }
        if (mobileNumber?.length < 8) {
            showInputError.value = SingleActionEvent(
                BaseFragment.InputError(
                    InputErrorType.MOBILE,
                    AppManager.getString(R.string.err_mobile_min_chars)
                )
            )
            return
        }
        if (mobileNumber?.length > 12) {
            showInputError.value = SingleActionEvent(
                BaseFragment.InputError(
                    InputErrorType.MOBILE,
                    AppManager.getString(R.string.err_mobile_max_chars)
                )
            )
            return
        }
        //updateUserProfileDetails(firstName, lastName, mobileNumber)
    }
}