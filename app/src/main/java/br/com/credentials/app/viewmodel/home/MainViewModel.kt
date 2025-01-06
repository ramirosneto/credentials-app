package br.com.credentials.app.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.credentials.app.model.data.ValidationResult
import br.com.credentials.app.utils.DataStore
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel : ViewModel(), KoinComponent {

    private val dataStore: DataStore by inject()

    private val _validationResult = MutableLiveData(ValidationResult.EMPTY)
    val validationResult: LiveData<ValidationResult> = _validationResult

    private val _accessLogs = MutableLiveData("")
    val accessLogs: LiveData<String> = _accessLogs

    fun updateResult(newResult: ValidationResult) {
        _validationResult.value = newResult
    }

    private fun updateAccessLogs(newLog: String) {
        _accessLogs.value = newLog
    }

    fun retrieveAccessLogs() {
        val logs = dataStore.readAccessLog()
        logs?.let { updateAccessLogs(it) }
    }
}