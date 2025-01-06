package br.com.credentials.app.di

import br.com.credentials.app.utils.CryptoManager
import br.com.credentials.app.viewmodel.login.LoginViewModel
import br.com.credentials.app.viewmodel.validation.ValidationViewModel
import org.junit.After
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.assertEquals
import kotlin.test.assertNotSame

class AppModuleTest : KoinTest {

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test CryptoManager is resolved as a singleton`() {
        startKoin {
            modules(AppModule.appModule)
        }
        val cryptoManager1: CryptoManager = get()
        val cryptoManager2: CryptoManager = get()
        assertEquals(cryptoManager1, cryptoManager2)
    }

    @Test
    fun `test LoginViewModel is resolved as a factory`() {
        startKoin {
            modules(AppModule.appModule)
        }
        val loginViewModel1: LoginViewModel = get()
        val loginViewModel2: LoginViewModel = get()
        assertNotSame(loginViewModel1, loginViewModel2)
    }

    @Test
    fun `test ValidationViewModel is resolved as a factory`() {
        startKoin {
            modules(AppModule.appModule)
        }
        val validationViewModel1: ValidationViewModel = get()
        val validationViewModel2: ValidationViewModel = get()
        assertNotSame(validationViewModel1, validationViewModel2)
    }
}