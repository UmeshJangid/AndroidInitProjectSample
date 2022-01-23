package com.app.androidinitialproject.ui.login

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.app.androidinitialproject.AppManager
import com.app.androidinitialproject.R
import com.app.androidinitialproject.domain.annotations.BaseViewModel
import com.app.androidinitialproject.domain.annotations.InputErrorType
import com.app.androidinitialproject.domain.model.User
import com.app.androidinitialproject.domain.base.BaseFragment
import com.app.androidinitialproject.domain.base.SingleActionEvent
import kotlinx.coroutines.launch


class LoginViewModel : BaseViewModel() {

    val onLoginSuccessful = MutableLiveData<SingleActionEvent<String>>()
    val copyId = MutableLiveData<SingleActionEvent<Boolean>>()
    val onSaveUserClicked = MutableLiveData<SingleActionEvent<Boolean>>()

    var deviceId: String = ""
    var username: String = ""
    var password: String = ""
    var saveUser: Boolean = false


    fun login() {
        closeKeyBoard()
        if (TextUtils.isEmpty(username.trim())) {
            showInputError.value = SingleActionEvent(
                BaseFragment.InputError(
                    InputErrorType.EMAIL,
                    AppManager.getString(R.string.err_username_empty)
                )
            )
            return
        }
        if (TextUtils.isEmpty(password.trim())) {
            showInputError.value = SingleActionEvent(
                BaseFragment.InputError(
                    InputErrorType.PASSWORD,
                    AppManager.getString(R.string.err_password_empty)
                )
            )
            return
        }
        if (password.trim()?.length!! <= 5) {
            showInputError.value = SingleActionEvent(
                BaseFragment.InputError(
                    InputErrorType.PASSWORD,
                    AppManager.getString(R.string.err_password_min_length)
                )
            )
            return
        }

        uiScope.launch {
            saveUserPermanentlyIfNeeded()
            var user = User();
            user.username = username
            user.first_name = "Test"
            user.last_name = "Kumar"
            user.email = username
            AppManager.setUser(user)
            onLoginSuccessful.value = SingleActionEvent("")

            /*Call Service*/
//            val res = AuthRepo(repoListener).requestUserLogin(
//                username.trim(),
//                password.trim(),
//                deviceId
//            )
//            res?.apply {
//                if (!this.error) {
//                    AppManager.setUser(User(this.cid, this.uid, this.badgeId, this.orgName))
//                    saveUserPermanentlyIfNeeded()
//                    onLoginSuccessful.value = SingleActionEvent("")
//                } else showToast.value = SingleActionEvent(this.errorMessage)
//            }
        }
    }

    fun copyId() {
        copyId.value = SingleActionEvent(true)
    }

    fun onSaveUserClicked() {
        onSaveUserClicked.value = SingleActionEvent(true)
    }

    fun saveUserPermanentlyIfNeeded() {
        if (saveUser) {
            AppManager.saveLoginEmail(username)
            AppManager.saveLoginPassword(password)
        } else {
            AppManager.saveLoginEmail("")
            AppManager.saveLoginPassword("")
        }
    }


}