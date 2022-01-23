package com.app.androidinitialproject.domain.annotations

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.androidinitialproject.data.remote.RepoListener
import com.app.androidinitialproject.domain.base.BaseFragment
import com.app.androidinitialproject.domain.base.SingleActionEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseViewModel : ViewModel() {

    // (<..>) use SingleActionEvent class to perform single actions that you don't want repeat when they are performed once
    // (Not even viewmodel is reattached)

    private val mainViewJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + mainViewJob)

    var repoListener: RepoListener = object : RepoListener {
        override fun onDataRequestFailed(dataRequestType: Int, statusCode: Int, message: String) {
        }

        override fun onNetworkConnectionError(dataRequestType: Int, message: String) {
        }

        override fun setDataRequestProgressIndicator(dataRequestType: Int, visible: Boolean) {
        }
    }

    val showToast = MutableLiveData<SingleActionEvent<String>>()
    val showMessageDialog = MutableLiveData<SingleActionEvent<String>>()
    val showInputError = MutableLiveData<SingleActionEvent<BaseFragment.InputError>>()
    val closeKeyBoard = MutableLiveData<SingleActionEvent<Boolean>>()
    val showProgressDialog = MutableLiveData<SingleActionEvent<Boolean>>()

    override fun onCleared() {
        super.onCleared()
        mainViewJob.cancel()
    }

    fun showToast(message: String) {
        showToast.value = SingleActionEvent(message)
    }

    fun showInputError(error: BaseFragment.InputError) {
        showInputError.value = SingleActionEvent(error)
    }

    fun closeKeyBoard() {
        closeKeyBoard.value = SingleActionEvent(true)
    }
}