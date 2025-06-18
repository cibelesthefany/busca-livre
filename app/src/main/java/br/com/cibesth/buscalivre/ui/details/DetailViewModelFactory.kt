package br.com.cibesth.buscalivre.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.cibesth.buscalivre.data.repository.ProductRepository

class DetailViewModelFactory(
    private val repository: ProductRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(repository) as T
    }
}
