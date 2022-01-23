package com.app.androidinitialproject.domain.model.remote.request

import com.app.androidinitialproject.AppManager
import com.google.gson.annotations.SerializedName


data class LoginRequest(
    @SerializedName("username")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("device_id")
    val deviceId: String,
    @SerializedName("version")
    val version: String = AppManager.API_VERSION
)