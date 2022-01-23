package com.app.androidinitialproject.domain.model.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("status") @Expose val status: Boolean,
    @SerializedName("message") @Expose val message: String,
    @SerializedName("code") @Expose val code: Int,
    @SerializedName("data") @Expose val data: T
)