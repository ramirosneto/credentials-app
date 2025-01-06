package br.com.credentials.app.utils

import android.content.Context
import android.content.SharedPreferences

class DataStoreImpl(context: Context) : DataStore {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE)

    override fun writeAccessLog(data: String) {
        val savedLogs = readAccessLog()
        val newValue = savedLogs?.let {
            savedLogs.joinToString(data)
        } ?: run {
            data
        }
        sharedPreferences.edit().putString(ACCESS_LOG_KEY, newValue).apply()
    }

    override fun readAccessLog(): String? {
        return sharedPreferences.getString(ACCESS_LOG_KEY, null)
    }

    companion object {
        private const val APP_PREF = "appPref"
        private const val ACCESS_LOG_KEY = "accessLogKey"
    }
}