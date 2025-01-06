package br.com.credentials.app.navigator

import android.os.Bundle

interface Navigator {

    fun startActivity(targetClass: Class<*>, extras: Bundle? = null)
}