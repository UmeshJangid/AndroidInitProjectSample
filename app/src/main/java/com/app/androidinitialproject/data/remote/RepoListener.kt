package com.app.androidinitialproject.data.remote

import com.app.androidinitialproject.domain.annotations.DataRequestType


interface RepoListener {
    fun onDataRequestFailed(@DataRequestType dataRequestType: Int, statusCode: Int, message: String)
    fun onNetworkConnectionError(@DataRequestType dataRequestType: Int, message: String)
    fun setDataRequestProgressIndicator(@DataRequestType dataRequestType: Int, visible: Boolean)
    fun <T> onDataRequestSucceed(dataRequestType: Int?, await: T?) {

    }
}