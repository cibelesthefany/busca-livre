package br.com.cibesth.buscalivre.ui.result

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.cibesth.buscalivre.data.model.Item
import br.com.cibesth.buscalivre.data.repository.ProductRepository

class ResultsViewModel(private val repository: ProductRepository) : ViewModel() {

    val items = MutableLiveData<List<Item>>()
    val error = MutableLiveData<String>()

    fun search(category: String) {
        try {
            val result = repository.getSearchResult(category)
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
