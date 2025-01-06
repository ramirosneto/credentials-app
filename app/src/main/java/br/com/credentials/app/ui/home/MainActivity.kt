package br.com.credentials.app.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import br.com.credentials.app.model.data.ValidationResult
import br.com.credentials.app.ui.theme.CredentialsAppTheme
import br.com.credentials.app.utils.VALIDATION_STATUS_EXTRA
import br.com.credentials.app.viewmodel.home.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent()
        getStatus()
    }

    private fun setContent() {
        setContent {
            CredentialsAppTheme {
                Surface(color = Color.White) {
                    MainScreen(viewModel)
                }
            }
        }
    }

    private fun getStatus() {
        val validationStatus =
            intent.getSerializableExtra(VALIDATION_STATUS_EXTRA) as ValidationResult
        viewModel.updateResult(validationStatus)
        viewModel.retrieveAccessLogs()
    }
}