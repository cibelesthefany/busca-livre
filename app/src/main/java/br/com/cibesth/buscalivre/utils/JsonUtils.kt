package br.com.cibesth.buscalivre.utils

import android.content.Context
import java.io.IOException

object JsonUtils {
    fun loadJsonFromAssets(context: Context, filePath: String): String? {
        return try {
            val inputStream = context.assets.open("mocks/$filePath")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, charset("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }
}
