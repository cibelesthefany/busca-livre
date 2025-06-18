package br.com.cibesth.buscalivre

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.cibesth.buscalivre.data.model.*
import br.com.cibesth.buscalivre.data.repository.ProductRepository
import br.com.cibesth.buscalivre.ui.details.DetailViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val repository: ProductRepository = mockk()

    private lateinit var viewModel: DetailViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = DetailViewModel(repository, dispatcher = testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadProductDetails should load data correctly`() = runTest {
        val item = ItemDetail(
            title = "Produto Teste",
            price = 199.99,
            thumbnail = "https://example.com/image.jpg",
            pictures = listOf(
                Picture(
                    url = "https://example.com/picture1.jpg",
                    secure_url = "https://example.com/picture1_secure.jpg"
                ),
                Picture(
                    url = "https://example.com/picture2.jpg",
                    secure_url = "https://example.com/picture2_secure.jpg"
                )
            )
        )

        val desc = Description(plain_text = "Descrição do produto")
        val cat = Category(
            path_from_root = listOf(
                Breadcrumb(id = "1", name = "Categoria Pai"),
                Breadcrumb(id = "2", name = "Categoria Filha")
            )
        )

        coEvery { repository.getItemDetail("arroz", "123") } returns item
        coEvery { repository.getItemDescription("arroz", "123") } returns desc
        coEvery { repository.getItemCategory("arroz", "123") } returns cat

        val itemObserver = mockk<Observer<ItemDetail?>>(relaxed = true)
        val descObserver = mockk<Observer<Description?>>(relaxed = true)
        val catObserver = mockk<Observer<Category?>>(relaxed = true)
        val errorObserver = mockk<Observer<String?>>(relaxed = true)

        viewModel.itemDetail.observeForever(itemObserver)
        viewModel.description.observeForever(descObserver)
        viewModel.category.observeForever(catObserver)
        viewModel.error.observeForever(errorObserver)

        viewModel.loadProductDetails("arroz", "123")
        advanceUntilIdle()

        verify { itemObserver.onChanged(item) }
        verify { descObserver.onChanged(desc) }
        verify { catObserver.onChanged(cat) }
        verify(exactly = 0) { errorObserver.onChanged(any()) }
    }
}