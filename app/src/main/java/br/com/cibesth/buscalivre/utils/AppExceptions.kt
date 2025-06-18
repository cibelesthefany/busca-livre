package br.com.cibesth.buscalivre.utils

sealed class AppExceptions(message: String) : Exception(message) {
    class CategoryNotFoundException(message: String) : AppExceptions(message)
}
