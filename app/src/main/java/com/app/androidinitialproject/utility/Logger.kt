package com.app.androidinitialproject.utility

import android.util.Log
import com.app.androidinitialproject.AppManager

/**  Here is Login enabled means if user is login with correct credentials
 * Than only Logs Print
 * */
class Logger {

    companion object {

        fun d(s: String) {
            if (AppManager.IS_LOGGING_ENABLED) Log.d("", s)
        }

        fun d(tag: String, s: String) {
            if (AppManager.IS_LOGGING_ENABLED) Log.d(tag, s)
        }

        fun e(s: String) {
            if (AppManager.IS_LOGGING_ENABLED) Log.e("", s)
        }

        fun e(tag: String, s: String) {
            if (AppManager.IS_LOGGING_ENABLED) Log.e(tag, s)
        }

//        fun print(s: String) {
//            if (AppManager.IS_LOGGING_ENABLED) println(s)
//        }

        @JvmStatic
        fun print(s: String) {
            if (AppManager.IS_LOGGING_ENABLED) println(s)
        }
    }
}