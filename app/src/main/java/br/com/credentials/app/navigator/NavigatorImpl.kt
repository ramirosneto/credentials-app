package br.com.credentials.app.navigator

import android.content.Context
import android.content.Intent
import android.os.Bundle

class NavigatorImpl(private val context: Context) : Navigator {

    override fun startActivity(targetClass: Class<*>, extras: Bundle?) {
        val intent = Intent(context, targetClass)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        extras?.let { intent.putExtras(it) }
        context.startActivity(intent)
    }
}