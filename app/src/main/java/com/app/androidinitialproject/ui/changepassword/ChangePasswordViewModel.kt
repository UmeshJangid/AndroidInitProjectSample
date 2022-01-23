package com.app.androidinitialproject.ui.changepassword

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.app.androidinitialproject.AppManager
import com.app.androidinitialproject.R
import com.app.androidinitialproject.domain.annotations.BaseViewModel
import com.app.androidinitialproject.domain.annotations.InputErrorType
import com.app.androidinitialproject.domain.base.BaseFragment
import com.app.androidinitialproject.domain.base.SingleActionEvent

class ChangePasswordViewModel : BaseViewModel() {

    var currentPassword = ""
    var newPassword = ""
    var confirmNewPassword = ""
    val onPasswordChanged = MutableLiveData<SingleActionEvent<Boolean>>()

    fun onSubmitClicked() {
        closeKeyBoard()
        if (TextUtils.isEmpty(currentPassword)) {
            showInputError.value = SingleActionEvent(
                BaseFragment.InputError(
                    InputErrorType.PASSWORD,
                    AppManager.getString(R.string.err_current_password_empty)
                )
            )
            return
        }
        if (currentPassword?.length!! <= 5) {
            showInputError.value = SingleActionEvent(
                BaseFragment.InputError(
                    InputErrorType.PASSWORD,
                    AppManager.getString(R.string.err_password_min_length)
                )
            )
            return
        }
        if (TextUtils.isEmpty(newPassword)) {
            showInputError.value = SingleActionEvent(
                BaseFragment.InputError(
                    InputErrorType.NEW_PASSWORD,
                    AppManager.getString(R.string.err_new_password_empty)
                )
            )
            return
        }
        if (newPassword?.length!! <= 5) {
            showInputError.value = SingleActionEvent(
                BaseFragment.InputError(
                    InputErrorType.NEW_PASSWORD,
                    AppManager.getString(R.string.err_password_min_length)
                )
            )
            return
        }
        if (TextUtils.isEmpty(confirmNewPassword)) {
            showInputError.value = SingleActionEvent(
                BaseFragment.InputError(
                    InputErrorType.CONFIRM_PASSWORD,
                    AppManager.getString(R.string.err_confirm_new_password_empty)
                )
            )
            return
        }
        if (!confirmNewPassword.equals(newPassword)) {
            showInputError.value = SingleActionEvent(
                BaseFragment.InputError(
                    InputErrorType.MISMATCH_PASSWORD,
                    AppManager.getString(R.string.err_mismatch_password)
                )
            )
            return
        }
        showToast("Change Password APi")
        // requestChangePassword(currentPassword, newPassword)
    }


    /**
     * Use this method when you have api for this
     * Un comment the below method and give the desired parameters
     * */
    /*  private fun requestChangePassword(oldPassword: String, password: String) {
          if (AppManager.hasDeviceIdForAPIs())
              AppManager.getDeviceIdForAPIs()?.let {
                  uiScope.launch {
                      val res = UserProfileRepo(repoListener).requestChangePassword(
                          oldPassword,
                          password,
                          it
                      )
                      res?.apply {
                          if (this.status) {
                              showToast.value = SingleActionEvent(this.message)
                              onPasswordChanged.value = SingleActionEvent(true)
                          } else showToast.value = SingleActionEvent(this.message)
                      }
                  }
              }
      }*/
}