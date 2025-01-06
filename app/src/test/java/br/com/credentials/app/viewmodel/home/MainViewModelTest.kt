package br.com.credentials.app.viewmodel.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.credentials.app.model.data.ValidationResult
import br.com.credentials.app.utils.DataStore
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
import org.koin.core.context.GlobalContext.get
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dataStore: DataStore

    private lateinit var viewModel: MainViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        startKoin {
            modules(module {
                single { dataStore }
            })
        }
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel()
    }

    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }


    @Test
    fun `when updateResult is called, then validationResult live data should be updated`() {
        val testResult = ValidationResult.SUCCESS
        val observer = Observer<ValidationResult> {}
        viewModel.validationResult.observeForever(observer)

        viewModel.updateResult(testResult)

        assert(viewModel.validationResult.value == testResult)
        viewModel.validationResult.removeObserver(observer)
    }

    @Test
    fun `when retrieveAccessLogs is called and dataStore returns logs, then accessLogs live data should be updated`() = runTest {
        val testLogs = "Test access logs"
        `when`(dataStore.readAccessLog()).thenReturn(testLogs)
        val observer = Observer<String> {}
        viewModel.accessLogs.observeForever(observer)

        viewModel.retrieveAccessLogs()

        assert(viewModel.accessLogs.value == testLogs)
        viewModel.accessLogs.removeObserver(observer)
    }

    @Test
    fun `when retrieveAccessLogs is called and dataStore returns null, then accessLogs live data should not be updated`() = runTest {
        `when`(dataStore.readAccessLog()).thenReturn(null)
        val initialValue = viewModel.accessLogs.value
        val observer = Observer<String> {}
        viewModel.accessLogs.observeForever(observer)

        viewModel.retrieveAccessLogs()

        assert(viewModel.accessLogs.value == initialValue)
        viewModel.accessLogs.removeObserver(observer)
    }
}