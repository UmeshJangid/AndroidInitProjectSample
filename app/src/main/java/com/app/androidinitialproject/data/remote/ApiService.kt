package com.app.androidinitialproject.data.remote

import com.app.androidinitialproject.domain.model.remote.request.LoginRequest
import com.app.androidinitialproject.domain.model.remote.response.LoginResponse
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiService {
    /**
     * Post Method Example
     * */
    @POST("auth")// Api suffix path ex. auth
    fun requestUserLogin(@Body request: LoginRequest): Deferred<LoginResponse>

    /**
     * Form Url Encoded Data
     * */
    /* If any one need to send some formdata than below can be example of it */
    @FormUrlEncoded
    @POST("Transaction") // Api suffix path ex. Transaction
    fun requestSomeFromData(
        @Field("tansactionId") tansactionId: String,
        @Field("cid") cid: Long,
        @Field("uid") uid: Long,
        @Field("data") data: String
    ): Deferred<String> // Here You can change with preferred class / response in Deferred Type

    /**
     * Get : Method Example
     * */
    @GET("download")
    fun downloadTicketImage(@Query("file") file: String, @Query("did") deviceId: String): Deferred<ResponseBody>

}