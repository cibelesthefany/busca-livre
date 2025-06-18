package br.com.cibesth.buscalivre.data.model

data class Category(
    val path_from_root: List<Breadcrumb>
)

data class Breadcrumb(
    val id: String,
    val name: String
)
