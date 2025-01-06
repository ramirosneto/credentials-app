package br.com.credentials.app.viewmodel.validation

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.credentials.app.model.data.ValidationResult
import br.com.credentials.app.navigator.Navigator
import br.com.credentials.app.ui.home.MainActivity
import br.com.credentials.app.utils.CryptoManager
import br.com.credentials.app.utils.DataStore
import br.com.credentials.app.utils.VALIDATION_STATUS_EXTRA
import br.com.credentials.app.utils.passwordIsCorrect
import br.com.credentials.app.utils.usernameIsCorrect
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ValidationViewModel : ViewModel(), KoinComponent {

    private val navigator: Navigator by inject()
    private val dataStore: DataStore by inject()
    private val cryptoManager: CryptoManager by inject()

    fun validateCredentials(cryptoUser: String, cryptoPass: String) {
        viewModelScope.launch {
            val decryptedUser = cryptoManager.decrypt(cryptoUser)
            val decryptedPass = cryptoManager.decrypt(cryptoPass)

            if (decryptedUser.usernameIsCorrect() && decryptedPass.passwordIsCorrect()) {
                handleSuccess()
            } else {
                handleError()
            }
        }
    }

    private fun handleSuccess() {
        val accessLog = "Acesso realizado com sucesso às ${now()}"
        dataStore.writeAccessLog(accessLog)
        val extras = Bundle().apply {
            putSerializable(VALIDATION_STATUS_EXTRA, ValidationResult.SUCCESS)
        }
        navigator.startActivity(MainActivity::class.java, extras)
    }

    private fun handleError() {
        val accessLog = "Falha ao realizar acesso às ${now()}"
        dataStore.writeAccessLog(accessLog)
        val extras = Bundle().apply {
            putSerializable(VALIDATION_STATUS_EXTRA, ValidationResult.ERROR)
        }
        navigator.startActivity(MainActivity::class.java, extras)
    }

    private fun now(): String {
        val brasiliaZoneId = ZoneId.of("America/Sao_Paulo")
        val localTimeNow = LocalTime.now(brasiliaZoneId)
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        return localTimeNow.format(formatter)
    }
}