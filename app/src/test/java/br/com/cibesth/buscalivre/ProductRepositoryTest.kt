package br.com.cibesth.buscalivre

import android.content.Context
import br.com.cibesth.buscalivre.data.repository.ProductRepository
import br.com.cibesth.buscalivre.utils.JsonUtils
import io.mockk.*
import org.junit.*

class ProductRepositoryTest {

    private val context = mockk<Context>(relaxed = true)
    private lateinit var repository: ProductRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = ProductRepository(context)
        mockkObject(JsonUtils)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getSearchResult should return items when json is valid`() {
        val fakeJson = """
            {
              "results": [
                {
                  "id": "123",
                  "title": "Produto Teste",
                  "price": 100.0,
                  "thumbnail": "https://example.com/thumb.jpg",
                  "pictures": []
                }
              ]
            }
        """.trimIndent()

        every {
            JsonUtils.loadJsonFromAssets(context, "arroz/search-MLA-arroz.json")
        } returns fakeJson

        val result = repository.getSearchResult("arroz")

        Assert.assertEquals(1, result.size)
        Assert.assertEquals("Produto Teste", result[0].title)
    }

    @Test
    fun `getSearchResult should return empty list when exception occurs`() {
        every {
            JsonUtils.loadJsonFromAssets(context, "arroz/search-MLA-arroz.json")
        } throws RuntimeException("File not found")

        val result = repository.getSearchResult("arroz")

        Assert.assertTrue(result.isEmpty())
    }

    @Test
    fun `getItemDetail should return item detail when json is valid`() {
        val fakeJson = """
            {
              "title": "Produto Teste",
              "price": 199.99,
              "thumbnail": "https://example.com/image.jpg",
              "pictures": []
            }
        """.trimIndent()

        every {
            JsonUtils.loadJsonFromAssets(context, "arroz/item-123.json")
        } returns fakeJson

        val result = repository.getItemDetail("arroz", "123")

        Assert.assertNotNull(result)
        Assert.assertEquals("Produto Teste", result?.title)
    }

    @Test
    fun `getItemDescription should return description when json is valid`() {
        val fakeJson = """
            {
              "plain_text": "Descrição do produto"
            }
        """.trimIndent()

        every {
            JsonUtils.loadJsonFromAssets(context, "arroz/item-123-description.json")
        } returns fakeJson

        val result = repository.getItemDescription("arroz", "123")

        Assert.assertNotNull(result)
        Assert.assertEquals("Descrição do produto", result?.plain_text)
    }

    @Test
    fun `getItemCategory should return category when json is valid`() {
        val fakeJson = """
            {
              "path_from_root": [
                { "id": "1", "name": "Categoria Pai" },
                { "id": "2", "name": "Categoria Filha" }
              ]
            }
        """.trimIndent()

        every {
            JsonUtils.loadJsonFromAssets(context, "arroz/item-123-category.json")
        } returns fakeJson

        val result = repository.getItemCategory("arroz", "123")

        Assert.assertNotNull(result)
        Assert.assertEquals(2, result?.path_from_root?.size)
        Assert.assertEquals("Categoria Filha", result?.path_from_root?.get(1)?.name)
    }
}
