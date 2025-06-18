package br.com.cibesth.buscalivre

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.cibesth.buscalivre.ui.search.SearchViewModel
import br.com.cibesth.buscalivre.utils.Constants
import io.mockk.MockKAnnotations
import io.mockk.mockk
import io.mockk.verify
import org.junit.*

class SearchViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = SearchViewModel()
    }

    @Test
    fun `onQueryChanged should enable button when query is not blank`() {
        val observer = mockk<Observer<Boolean>>(relaxed = true)
        viewModel.isButtonEnabled.observeForever(observer)

        viewModel.onQueryChanged("arroz")

        verify { observer.onChanged(true) }
    }

    @Test
    fun `onQueryChanged should disable button when query is blank`() {
        val observer = mockk<Observer<Boolean>>(relaxed = true)
        viewModel.isButtonEnabled.observeForever(observer)

        viewModel.onQueryChanged("")

        verify { observer.onChanged(false) }
    }

    @Test
    fun `validateQuery should return category when valid`() {
        val result = viewModel.validateQuery("arroz")

        Assert.assertEquals(Constants.CATEGORY_ARROZ, result)
        Assert.assertNull(viewModel.error.value)
    }

    @Test
    fun `validateQuery should return null and set error when invalid`() {
        val result = viewModel.validateQuery("invalido")

        Assert.assertNull(result)
        Assert.assertTrue(
            viewModel.error.value?.contains("não encontramos a categoria") == true
        )
    }


    @Test
    fun `onQueryChanged should clear previous errors`() {
        val errorObserver = mockk<Observer<String?>>(relaxed = true)
        viewModel.error.observeForever(errorObserver)

        // Simula erro anterior
        viewModel.validateQuery("invalido")
        Assert.assertNotNull(viewModel.error.value)

        // Agora muda a query e deve limpar o erro
        viewModel.onQueryChanged("arroz")

        verify { errorObserver.onChanged(null) }
    }
}
