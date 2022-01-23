package com.app.androidinitialproject.domain.repos

import com.app.androidinitialproject.data.remote.RemoteRepo
import com.app.androidinitialproject.data.remote.RepoListener
import com.app.androidinitialproject.domain.annotations.DataRequestType
import com.app.androidinitialproject.domain.model.remote.request.LoginRequest
import com.app.androidinitialproject.domain.model.remote.response.LoginResponse
import com.app.androidinitialproject.data.remote.ApiClient
import kotlinx.coroutines.Deferred

class AuthRepo(val repoListener: RepoListener) {

    suspend fun requestUserLogin(
        email: String,
        password: String,
        deviceId: String
    ): LoginResponse? {
        return object : RemoteRepo<LoginResponse> {
            override val deferred: Deferred<LoginResponse>
                get() = ApiClient.apiService.requestUserLogin(
                    LoginRequest(
                        email,
                        password,
                        deviceId
                    )
                )
            override val dataRequestType: Int
                get() = DataRequestType.USER_LOGIN
            override val repoListener: RepoListener
                get() = this@AuthRepo.repoListener
        }.executeApiRequest()
    }


}