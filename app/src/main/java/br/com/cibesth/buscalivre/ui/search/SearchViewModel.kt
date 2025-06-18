package br.com.cibesth.buscalivre.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.cibesth.buscalivre.utils.AppExceptions
import br.com.cibesth.buscalivre.utils.Constants

class SearchViewModel : ViewModel() {

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _isButtonEnabled = MutableLiveData<Boolean>()
    val isButtonEnabled: LiveData<Boolean> = _isButtonEnabled

    fun onQueryChanged(query: String) {
        _isButtonEnabled.value = query.isNotBlank()
        _error.value = null
    }

    fun validateQuery(query: String): String? {
        return try {
            _error.value = null
            Constants.mapQueryToCategoryOrThrow(query)
        } catch (e: AppExceptions.CategoryNotFoundException) {
            _error.value = e.message
            null
        }
    }
}
