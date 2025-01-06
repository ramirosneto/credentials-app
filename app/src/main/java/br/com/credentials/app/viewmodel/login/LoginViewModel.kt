package br.com.credentials.app.viewmodel.login

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.credentials.app.navigator.Navigator
import br.com.credentials.app.ui.validation.ValidationActivity
import br.com.credentials.app.utils.CryptoManager
import br.com.credentials.app.utils.ENCRYPTED_PASS
import br.com.credentials.app.utils.ENCRYPTED_USER
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginViewModel : ViewModel(), KoinComponent {

    private val navigator: Navigator by inject()
    private val cryptoManager: CryptoManager by inject()

    private val _username = MutableLiveData("")
    val username: LiveData<String> = _username

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    fun updateUsername(newUsername: String) {
        _username.value = newUsername
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun login() {
        viewModelScope.launch {
            performLogin(username.value ?: "", password.value ?: "")
        }
    }

    private fun performLogin(username: String, password: String) {
        val extras = Bundle().apply {
            putString(ENCRYPTED_USER, cryptoManager.encrypt(username))
            putString(ENCRYPTED_PASS, cryptoManager.encrypt(password))
        }
        navigator.startActivity(ValidationActivity::class.java, extras)
    }
}