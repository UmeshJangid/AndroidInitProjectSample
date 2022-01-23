package com.app.androidinitialproject.ui.dashboard

import androidx.lifecycle.MutableLiveData
import com.app.androidinitialproject.domain.annotations.BaseViewModel
import com.app.androidinitialproject.domain.base.SingleActionEvent

class DashboardViewModel : BaseViewModel() {

    val onChooseSiteClicked = MutableLiveData<SingleActionEvent<Boolean>>()
    val onTicketLogClicked = MutableLiveData<SingleActionEvent<Boolean>>()
    val onSyncClicked = MutableLiveData<SingleActionEvent<Boolean>>()
    val onPrinterTestClicked = MutableLiveData<SingleActionEvent<Boolean>>()

    fun onChooseSiteClicked() {
        onChooseSiteClicked.value = SingleActionEvent(true)
    }

    fun onTicketLogClicked() {
        onTicketLogClicked.value = SingleActionEvent(true)
    }

    fun onSyncClicked() {
        onSyncClicked.value = SingleActionEvent(true)
    }

    fun onPrinterTestClicked() {
        onPrinterTestClicked.value = SingleActionEvent(true)
    }
}