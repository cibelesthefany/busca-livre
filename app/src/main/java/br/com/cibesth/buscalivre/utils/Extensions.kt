package br.com.cibesth.buscalivre.utils

import java.text.NumberFormat
import java.util.Locale

fun Double.toBrazilianCurrency(): String {
    val format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return format.format(this)
}

fun Double.fixPrice(): Double {
    return if (this > 100000) this / 100.0 else this
}
