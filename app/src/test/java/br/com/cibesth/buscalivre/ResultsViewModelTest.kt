package br.com.cibesth.buscalivre

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.cibesth.buscalivre.data.model.Item
import br.com.cibesth.buscalivre.data.repository.ProductRepository
import br.com.cibesth.buscalivre.ui.result.ResultsViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class ResultsViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val repository: ProductRepository = mockk()

    private lateinit var viewModel: ResultsViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ResultsViewModel(repository, dispatcher = testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `search should return items when found`() = runTest {
        val mockItems = listOf(
            Item(id = "1", title = "Item 1", price = 100.0, thumbnail = "url1"),
            Item(id = "2", title = "Item 2", price = 200.0, thumbnail = "url2")
        )

        coEvery { repository.getSearchResult("arroz") } returns mockItems

        val itemsObserver = mockk<Observer<List<Item>>>(relaxed = true)
        val errorObserver = mockk<Observer<String>>(relaxed = true)

        viewModel.items.observeForever(itemsObserver)
        viewModel.error.observeForever(errorObserver)

        viewModel.search("arroz")
        advanceUntilIdle()

        verify { itemsObserver.onChanged(mockItems) }
        verify(exactly = 0) { errorObserver.onChanged(any()) }
    }

    @Test
    fun `search should return error when no items found`() = runTest {
        coEvery { repository.getSearchResult("arroz") } returns emptyList()

        val errorObserver = mockk<Observer<String>>(relaxed = true)

        viewModel.error.observeForever(errorObserver)

        viewModel.search("arroz")
        advanceUntilIdle()

        verify { errorObserver.onChanged("Nenhum resultado encontrado para \"arroz\"") }
    }

    @Test
    fun `search should handle exception`() = runTest {
        coEvery { repository.getSearchResult("arroz") } throws RuntimeException("Falha na busca")

        val errorObserver = mockk<Observer<String>>(relaxed = true)

        viewModel.error.observeForever(errorObserver)

        viewModel.search("arroz")
        advanceUntilIdle()

        verify { errorObserver.onChanged(match { it.contains("Erro: Falha na busca") }) }
    }
}
