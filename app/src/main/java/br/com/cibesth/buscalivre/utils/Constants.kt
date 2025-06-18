package br.com.cibesth.buscalivre.utils

object Constants {
    const val CATEGORY_IPHONE = "iphone"
    const val CATEGORY_CAFE = "cafe"
    const val CATEGORY_ARROZ = "arroz"
    const val CATEGORY_CAMISA = "camisa"
    const val CATEGORY_ZAPATILHAS = "zapatilhas"

    val QUERY_TO_CATEGORY = mapOf(
        "iphone" to CATEGORY_IPHONE,
        "iphones" to CATEGORY_IPHONE,
        "café" to CATEGORY_CAFE,
        "cafe" to CATEGORY_CAFE,
        "arroz" to CATEGORY_ARROZ,
        "camisa" to CATEGORY_CAMISA,
        "camisas" to CATEGORY_CAMISA,
        "zapatilla" to CATEGORY_ZAPATILHAS,
        "zapatillas" to CATEGORY_ZAPATILHAS,
        "sapatilhas" to CATEGORY_ZAPATILHAS,
        "tênis" to CATEGORY_ZAPATILHAS,
        "tenis" to CATEGORY_ZAPATILHAS
    )

    fun mapQueryToCategoryOrThrow(query: String): String {
        val normalized = query.lowercase().trim()
        return QUERY_TO_CATEGORY[normalized]
            ?: throw AppExceptions.CategoryNotFoundException("Ops, não encontramos a categoria \"$query\", pedimos desculpa")
    }
}
