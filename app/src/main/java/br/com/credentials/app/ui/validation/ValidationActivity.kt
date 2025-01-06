package br.com.credentials.app.ui.validation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import br.com.credentials.app.ui.theme.CredentialsAppTheme
import br.com.credentials.app.utils.ENCRYPTED_PASS
import br.com.credentials.app.utils.ENCRYPTED_USER
import br.com.credentials.app.viewmodel.validation.ValidationViewModel

class ValidationActivity : ComponentActivity() {

    private val viewModel: ValidationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent()
        getCredentials()
    }

    private fun setContent() {
        setContent {
            CredentialsAppTheme {
                Surface(color = Color.White) {
                    ValidationScreen()
                }
            }
        }
    }

    private fun getCredentials() {
        val encryptedUser = intent.getStringExtra(ENCRYPTED_USER) ?: ""
        val encryptedPass = intent.getStringExtra(ENCRYPTED_PASS) ?: ""
        viewModel.validateCredentials(encryptedUser, encryptedPass)
        finish()
    }
}