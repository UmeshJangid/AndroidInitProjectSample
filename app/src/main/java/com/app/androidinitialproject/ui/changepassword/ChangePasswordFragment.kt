package com.app.androidinitialproject.ui.changepassword

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.app.androidinitialproject.BR
import com.app.androidinitialproject.R
import com.app.androidinitialproject.databinding.FragmentChangePasswordBinding
import com.app.androidinitialproject.domain.annotations.InputErrorType
import com.app.androidinitialproject.domain.base.BaseFragment


class ChangePasswordFragment :
    BaseFragment<ChangePasswordViewModel, FragmentChangePasswordBinding>() {

    override fun getToolbarMenuHandler(): ToolbarMenuHandler? = object : ToolbarMenuHandler {
        override val toolbarId: Int
            get() = R.id.toolbar
        override val toolbarTitleId: Int
            get() = R.id.toolbarTitle
        override val menuResource: Int
            get() = 0
        override val toolbarTitle: String
            get() = getString(R.string.change_password)

        override fun hasMenu(): Boolean = false
        override fun hasBackButton(): Boolean = true
    }

    override fun getLayoutResource(): Int = R.layout.fragment_change_password
    override fun getViewModelClass(): Class<ChangePasswordViewModel> =
        ChangePasswordViewModel::class.java

   // override fun getViewModelBindingId(): Int = BR.viewmodel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onPasswordChanged.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                activity?.onBackPressed()
            }
        })
    }

    override fun showInputError(inputError: InputError) {
        when (inputError.errorType) {
            InputErrorType.PASSWORD -> {
                viewBinding.editOldPassword.requestFocus()
                showToast(inputError.message)
            }
            InputErrorType.NEW_PASSWORD -> {
                viewBinding.editNewPassword.requestFocus()
                showToast(inputError.message)
            }
            InputErrorType.CONFIRM_PASSWORD -> {
                viewBinding.editConfirmPassword.requestFocus()
                showToast(inputError.message)
            }
            InputErrorType.MISMATCH_PASSWORD -> {
                viewBinding.editConfirmPassword.requestFocus()
                showToast(inputError.message)
            }
        }
    }

    companion object {
        val TAG = "ChangePasswordFragment33"
        fun newInstance() = ChangePasswordFragment()
    }
}