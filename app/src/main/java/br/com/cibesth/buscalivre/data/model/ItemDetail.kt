package br.com.cibesth.buscalivre.data.model

data class ItemDetail(
    val title: String,
    val price: Double,
    val thumbnail: String,
    val pictures: List<Picture>? = null
)
