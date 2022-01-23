package com.app.androidinitialproject.data.remote

import com.app.androidinitialproject.AppManager
import com.app.androidinitialproject.R
import com.app.androidinitialproject.data.remote.RepoListener
import com.app.androidinitialproject.domain.annotations.DataRequestType

import kotlinx.coroutines.Deferred
import retrofit2.HttpException

interface RemoteRepo<T> {

    val deferred: Deferred<T>
    @DataRequestType
    val dataRequestType: Int
    val repoListener: RepoListener

    suspend fun executeApiRequest(): T? {
        if (AppManager.isNetworkConnectedAvailable()) {
            try {
                repoListener.setDataRequestProgressIndicator(dataRequestType, true)
                val t: T = deferred.await()
                repoListener.onDataRequestSucceed(dataRequestType, t)
                repoListener.setDataRequestProgressIndicator(dataRequestType, false)
                return t
            } catch (httpException: HttpException) {
                // a non-2XX response was received
                repoListener.onDataRequestFailed(
                    dataRequestType,
                    httpException.code(),
                    AppManager.getString(R.string.err_something_wrong)
                )
                repoListener.setDataRequestProgressIndicator(dataRequestType, false)
                return null
            } catch (t: Throwable) {
                // a networking or data conversion error
                repoListener.onDataRequestFailed(
                    dataRequestType,
                    ERROR_CODE_FAILED_TO_CONNECT,
                    AppManager.getString(R.string.err_something_wrong)
                )
                repoListener.setDataRequestProgressIndicator(dataRequestType, false)
                return null
            }
        } else {
            // No network connections available error
            repoListener.onNetworkConnectionError(
                dataRequestType,
                AppManager.getString(R.string.err_network_connection)
            )
            repoListener.setDataRequestProgressIndicator(dataRequestType, false)
            return null
        }
    }

    companion object {
        const val ERROR_CODE_FAILED_TO_CONNECT = -7131313
    }
}