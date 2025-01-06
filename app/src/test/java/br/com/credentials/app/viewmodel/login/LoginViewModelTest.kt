package br.com.credentials.app.viewmodel.login

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.credentials.app.navigator.Navigator
import br.com.credentials.app.ui.validation.ValidationActivity
import br.com.credentials.app.utils.CryptoManager
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var navigator: Navigator

    @Mock
    private lateinit var cryptoManager: CryptoManager

    private lateinit var viewModel: LoginViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        startKoin {
            modules(module {
                single { navigator }
                single { cryptoManager }
            })
        }
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel()
    }

    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }


    @Test
    fun `when username is updated, then username live data should be updated`() {
        val testUsername = "testUser"
        val observer = Observer<String> {}
        viewModel.username.observeForever(observer)

        viewModel.updateUsername(testUsername)

        assert(viewModel.username.value == testUsername)
        viewModel.username.removeObserver(observer)
    }

    @Test
    fun `when password is updated, then password live data should be updated`() {
        val testPassword = "testPassword"
        val observer = Observer<String> {}
        viewModel.password.observeForever(observer)

        viewModel.updatePassword(testPassword)

        assert(viewModel.password.value == testPassword)
        viewModel.password.removeObserver(observer)
    }
}