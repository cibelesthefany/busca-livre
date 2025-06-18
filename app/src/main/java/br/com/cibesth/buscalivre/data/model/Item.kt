package br.com.cibesth.buscalivre.data.model

data class Item(
    val id: String,
    val title: String,
    val price: Double,
    val thumbnail: String,
    val pictures: List<Picture>? = null
)
