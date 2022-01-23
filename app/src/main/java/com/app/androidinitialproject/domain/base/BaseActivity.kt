package com.app.androidinitialproject.domain.base

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.androidinitialproject.BR
import com.app.androidinitialproject.data.remote.RepoListener
import com.app.androidinitialproject.domain.annotations.BaseViewModel
import com.app.androidinitialproject.domain.dialogs.ProgressDialog
import com.app.androidinitialproject.utility.Utils


abstract class BaseActivity<T : BaseViewModel, B : ViewDataBinding> : BaseDrawerActivity(),
    RepoListener {

    lateinit var viewModel: T
    lateinit var viewBinding: B
    abstract fun getViewModelClass(): Class<T>
    @LayoutRes
    abstract fun getLayoutResource(): Int
//    abstract fun getViewModelBindingId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(getViewModelClass())
        viewBinding = DataBindingUtil.setContentView(this, getLayoutResource())
        viewModel.repoListener = this
        // Hardcoded viewmodel binding variable be careful because it's necessary to define in xml file
        viewBinding.setVariable(BR.viewmodel, viewModel)
        //viewBinding.setVariable(getViewModelBindingId(), viewModel)
        viewBinding.setLifecycleOwner(this)
        viewBinding.executePendingBindings()

        viewModel.showToast.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                showToast(message = it)
            }
        })

        viewModel.showInputError.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                showInputError(inputError = it)
            }
        })
        viewModel.closeKeyBoard.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                closeKeyboard()
            }
        })
    }

    fun showToast(message: String) {
        if (!TextUtils.isEmpty(message)) Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

//    fun showSnackBar(message: String) {
//        val rootView: View = getWindow().getDecorView().findViewById(android.R.id.content)
//            Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show()
//    }

    fun closeKeyboard() {
        Utils.closeKeyBoard(this)
    }

    override fun onDataRequestFailed(dataRequestType: Int, statusCode: Int, message: String) {
        showToast(message)
    }

    override fun onNetworkConnectionError(dataRequestType: Int, message: String) {
        showToast(message)
    }

    override fun setDataRequestProgressIndicator(dataRequestType: Int, visible: Boolean) {
//        showToast("setDataRequestProgressIndicator : " + visible)
        if (visible) showProgressDialog()
        else dismissProgressDialog()
    }

    open fun showInputError(inputError: BaseFragment.InputError) {}

    private var progressDialog: ProgressDialog? = null

    fun getProgressDialog(): ProgressDialog? {
        return progressDialog
    }

    fun showProgressDialog() {
        if (progressDialog != null && progressDialog!!.dialog?.isShowing!!) return
        progressDialog = ProgressDialog(this)
        progressDialog!!.show()
    }

    fun showProgressDialog(message: String) {
        if (progressDialog != null && progressDialog!!.dialog?.isShowing!!) return
        progressDialog = ProgressDialog(this, message)
        progressDialog!!.show()
    }

    fun showProgressDialog(@NonNull progressDialogListener: ProgressDialog.ProgressDialogListener) {
        if (progressDialog != null && progressDialog!!.dialog?.isShowing!!) return
        progressDialog = ProgressDialog(this, progressDialogListener)
        progressDialog!!.show()
    }

    fun showProgressDialog(
        message: String,
        @NonNull progressDialogListener: ProgressDialog.ProgressDialogListener
    ) {
        if (progressDialog != null && progressDialog!!.dialog?.isShowing!!) return
        progressDialog = ProgressDialog(this, message, progressDialogListener)
        progressDialog!!.show()
    }

    fun dismissProgressDialog() {
        if (progressDialog != null && progressDialog!!.dialog?.isShowing!!)
            progressDialog!!.dialog?.dismiss()
    }
}