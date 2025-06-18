package br.com.cibesth.buscalivre.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.cibesth.buscalivre.data.model.Category
import br.com.cibesth.buscalivre.data.model.Description
import br.com.cibesth.buscalivre.data.model.ItemDetail
import br.com.cibesth.buscalivre.data.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(private val repository: ProductRepository) : ViewModel() {

    val itemDetail = MutableLiveData<ItemDetail?>()
    val description = MutableLiveData<Description?>()
    val category = MutableLiveData<Category?>()
    val error = MutableLiveData<String?>()

    fun loadProductDetails(categoryName: String, productId: String) {
        viewModelScope.launch {
            try {
                val item = withContext(Dispatchers.IO) {
                    repository.getItemDetail(categoryName, productId)
                }
                val desc = withContext(Dispatchers.IO) {
                    repository.getItemDescription(categoryName, productId)
                }
                val cat = withContext(Dispatchers.IO) {
                    repository.getItemCategory(categoryName, productId)
                }

                if (item != null) {
                    itemDetail.postValue(item)
                } else {
                    error.postValue("Ops, não estamos encontrando esse produto.")
                }

                description.postValue(desc)
                category.postValue(cat)

            } catch (e: Exception) {
                error.postValue("Erro inesperado: ${e.localizedMessage}")
            }
        }
    }
}
