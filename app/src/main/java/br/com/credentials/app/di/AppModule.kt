package br.com.credentials.app.di

import br.com.credentials.app.navigator.Navigator
import br.com.credentials.app.navigator.NavigatorImpl
import br.com.credentials.app.utils.CryptoManager
import br.com.credentials.app.utils.CryptoManagerImpl
import br.com.credentials.app.utils.DataStore
import br.com.credentials.app.utils.DataStoreImpl
import br.com.credentials.app.viewmodel.login.LoginViewModel
import br.com.credentials.app.viewmodel.validation.ValidationViewModel
import org.koin.dsl.module

object AppModule {

    val appModule = module {
        single<CryptoManager> { CryptoManagerImpl() }

        single<Navigator> { NavigatorImpl(get()) }

        single<DataStore> { DataStoreImpl(get()) }

        factory<LoginViewModel> { LoginViewModel() }

        factory<ValidationViewModel> { ValidationViewModel() }
    }
}