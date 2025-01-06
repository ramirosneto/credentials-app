package br.com.credentials.app.viewmodel.validation

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.credentials.app.model.data.ValidationResult
import br.com.credentials.app.navigator.Navigator
import br.com.credentials.app.ui.home.MainActivity
import br.com.credentials.app.utils.CryptoManager
import br.com.credentials.app.utils.DataStore
import br.com.credentials.app.utils.passwordIsCorrect
import br.com.credentials.app.utils.usernameIsCorrect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ValidationViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var navigator: Navigator

    @Mock
    private lateinit var dataStore: DataStore

    @Mock
    private lateinit var cryptoManager: CryptoManager

    private lateinit var viewModel: ValidationViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        startKoin {
            modules(module {
                single { navigator }
                single { dataStore }
                single { cryptoManager }
            })
        }
        Dispatchers.setMain(testDispatcher)
        viewModel = ValidationViewModel()
    }

    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `when credentials are valid, then should navigate to MainActivity with success and write success log`() =
        runTest {
            val cryptoUser = "encryptedUser"
            val cryptoPass = "encryptedPass"
            val decryptedUser = "testUser"
            val decryptedPass = "testPassword"
            val expectedLog = "Acesso realizado com sucesso às ${getCurrentTime()}"

            `when`(cryptoManager.decrypt(cryptoUser)).thenReturn(decryptedUser)
            `when`(cryptoManager.decrypt(cryptoPass)).thenReturn(decryptedPass)
            `when`(decryptedUser.usernameIsCorrect()).thenReturn(true)
            `when`(decryptedPass.passwordIsCorrect()).thenReturn(true)

            viewModel.validateCredentials(cryptoUser, cryptoPass)

            verify(dataStore).writeAccessLog(expectedLog)
            verify(navigator).startActivity(
                MainActivity::class.java,
                Bundle().apply {
                    putSerializable("VALIDATION_STATUS", ValidationResult.SUCCESS)
                }
            )
        }

    @Test
    fun `when credentials are invalid, then should navigate to MainActivity with error and write error log`() =
        runTest {
            val cryptoUser = "encryptedUser"
            val cryptoPass = "encryptedPass"
            val decryptedUser = "testUser"
            val decryptedPass = "testPassword"
            val expectedLog = "Falha ao realizar acesso às ${getCurrentTime()}"

            `when`(cryptoManager.decrypt(cryptoUser)).thenReturn(decryptedUser)
            `when`(cryptoManager.decrypt(cryptoPass)).thenReturn(decryptedPass)
            `when`(decryptedUser.usernameIsCorrect()).thenReturn(false)
            `when`(decryptedPass.passwordIsCorrect()).thenReturn(false)

            viewModel.validateCredentials(cryptoUser, cryptoPass)

            verify(dataStore).writeAccessLog(expectedLog)
            verify(navigator).startActivity(
                MainActivity::class.java,
                Bundle().apply {
                    putSerializable("VALIDATION_STATUS", ValidationResult.ERROR)
                }
            )
        }

    private fun getCurrentTime(): String {
        val brasiliaZoneId = ZoneId.of("America/Sao_Paulo")
        val localTimeNow = LocalTime.now(brasiliaZoneId)
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        return localTimeNow.format(formatter)
    }
}