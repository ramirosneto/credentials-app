package br.com.credentials.app.ui.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import br.com.credentials.app.ui.theme.CredentialsAppTheme
import br.com.credentials.app.viewmodel.login.LoginViewModel

class LoginActivity : ComponentActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent()
    }

    private fun setContent() {
        setContent {
            CredentialsAppTheme {
                Surface(color = Color.White) {
                    LoginScreen(viewModel)
                }
            }
        }
    }
}