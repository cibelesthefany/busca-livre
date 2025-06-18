package br.com.cibesth.buscalivre.data.repository

import android.content.Context
import br.com.cibesth.buscalivre.data.model.*
import br.com.cibesth.buscalivre.utils.GsonInstance.gson
import br.com.cibesth.buscalivre.utils.JsonUtils.loadJsonFromAssets

class ProductRepository(private val context: Context) {

    fun getSearchResult(category: String): List<Item> {
        return try {
            val json = loadJsonFromAssets(context, "$category/search-MLA-$category.json")
            val response = gson.fromJson(json, SearchResponse::class.java)
            response.results
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun getItemDetail(category: String, productId: String): ItemDetail? {
        val json = loadJsonFromAssets(context, "$category/item-$productId.json")
        return gson.fromJson(json, ItemDetail::class.java)
    }

    fun getItemDescription(category: String, productId: String): Description? {
        val json = loadJsonFromAssets(context, "$category/item-$productId-description.json")
        return gson.fromJson(json, Description::class.java)
    }

    fun getItemCategory(category: String, productId: String): Category? {
        val json = loadJsonFromAssets(context, "$category/item-$productId-category.json")
        return gson.fromJson(json, Category::class.java)
    }
}
