package com.app.androidinitialproject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.annotation.StringRes
import com.app.androidinitialproject.domain.model.User
import com.app.androidinitialproject.ui.home.HomeActivity
import com.app.androidinitialproject.utility.Utils
import com.google.gson.Gson


class AppManager(context: Context) {

    /* Constructor */
    init {
        if (instance == null) {
            preferences = context.applicationContext.getSharedPreferences(
                PrefKeys.PREF_NAME,
                Context.MODE_PRIVATE
            )
        }
        c = context
    }

    /**
     * Store your Preferences keys here
     * */
    private interface PrefKeys {
        companion object {
            val PREF_NAME = "AndroidInitPref"
            val PREF_DEVICE_ID = "device_id"
            val PREF_USER = "your_user"
            val PREF_SAVED_EMAIL = "email"
            val PREF_SAVED_PASSWORD = "password"
        }
    }

    companion object {
        val IS_LOGGING_ENABLED = true
        val DATABASE_NAME = "your_db_name_if_any"
        val REMOTE_BASE_URL = "http://yourbaseurl.com/api/"
        val API_VERSION = "1.0"
        val authSalt = "Bearer "
        private var instance: AppManager? = null
        private lateinit var preferences: SharedPreferences
        private lateinit var c: Context

        fun getInstance(): AppManager? {
            return instance
        }

        @Synchronized
        fun setInstance(instance: AppManager) {
            if (Companion.instance == null)
                Companion.instance = instance
        }

        private var sUser: User? = null

        fun getUser(): User? {
            if (sUser == null && preferences == null) return null
            else if (sUser == null) {
                preferences?.apply {
                    if (this.contains(PrefKeys.PREF_USER)) {
                        try {
                            val s = this.getString(PrefKeys.PREF_USER, null)
                            sUser = Gson().fromJson<User>(s, User::class.java)
                        } catch (e: Exception) {
                            sUser = null
                        }
                    }
                }
                return sUser
            }
            return sUser
        }

        fun setUser(user: User?) {
            preferences?.apply {
                this.edit().putString(PrefKeys.PREF_USER, Gson().toJson(user)).apply()
            }
            sUser = user
        }

        fun isUserLoggedIn(): Boolean {
            return getUser() != null
        }

        val context: Context
            get() {
                return c
            }

        fun getString(@StringRes str: Int): String {
            return c.getString(str)
        }

        fun isNetworkConnectedAvailable(): Boolean {
            return Utils.isNetworkConnectedAvailable(c)
        }

        fun getSavedLoginEmail(): String {
            return preferences.getString(PrefKeys.PREF_SAVED_EMAIL, null) ?: ""
        }

        fun getSavedLoginPassword(): String {
            return preferences.getString(PrefKeys.PREF_SAVED_PASSWORD, null) ?: ""
        }

        fun saveLoginEmail(email: String) {
            preferences.edit().putString(PrefKeys.PREF_SAVED_EMAIL, email).apply()
        }

        fun saveLoginPassword(password: String) {
            preferences.edit().putString(PrefKeys.PREF_SAVED_PASSWORD, password).apply()
        }

        fun logoutUser(con : Context) {
            setUser(null)
            preferences.edit().clear().commit()
            con.startActivity(Intent(con, HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }

    }
}