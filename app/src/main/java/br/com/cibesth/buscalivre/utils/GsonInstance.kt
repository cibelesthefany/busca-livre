package br.com.cibesth.buscalivre.utils

import com.google.gson.Gson

object GsonInstance {
    val gson: Gson by lazy {
        Gson()
    }
}