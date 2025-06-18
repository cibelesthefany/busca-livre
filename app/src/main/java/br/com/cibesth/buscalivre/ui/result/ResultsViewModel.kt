package br.com.cibesth.buscalivre.ui.result

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.cibesth.buscalivre.data.model.Item
import br.com.cibesth.buscalivre.data.repository.ProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResultsViewModel(
    private val repository: ProductRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    val items = MutableLiveData<List<Item>>()
    val error = MutableLiveData<String>()

    fun search(category: String) {
        viewModelScope.launch {
            try {
                val result = withContext(dispatcher) {
                    repository.getSearchResult(category)
                }
                if (result.isNotEmpty()) {
                    items.postValue(result)
                } else {
                    error.postValue("Nenhum resultado encontrado para \"$category\"")
                }
            } catch (e: Exception) {
                error.postValue("Erro: ${e.message}")
            }
        }
    }
}
