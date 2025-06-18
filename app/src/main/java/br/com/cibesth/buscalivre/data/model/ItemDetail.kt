package br.com.cibesth.buscalivre.data.model

data class ItemDetail(
    val id: String,
    val title: String,
    val price: Double,
    val condition: String,
    val thumbnail: String,
    val available_quantity: Int,
    val pictures: List<Picture>?
)
