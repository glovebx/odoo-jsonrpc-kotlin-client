package com.ria4.odoo.presentation.utils.extensions

import okhttp3.ResponseBody
import java.io.*

// 可能会抛出错误
@Throws(Throwable::class)
fun ResponseBody.saveToDisk(absolutePath: String): Boolean {

    return this.byteStream().use {
        FileOutputStream(File(absolutePath)).use { it2 ->
            val fileReader = ByteArray(4096)

            while (true) {
                val read = it.read(fileReader)

                if (read == -1) {
                    break
                }

                it2.write(fileReader, 0, read)
            }
            it2.flush()
        }

        return true
    }
//
//    var inputStream: InputStream? = null
//    var outputStream: OutputStream? = null
//
//    return try {
//        val fileReader = ByteArray(4096)
//
//        inputStream = this.byteStream()
//        outputStream = FileOutputStream(File(absolutePath))
//
//        while (true) {
//            val read = inputStream!!.read(fileReader)
//
//            if (read == -1) {
//                break
//            }
//
//            outputStream.write(fileReader, 0, read)
//        }
//
//        outputStream.flush()
//
//        return true
//    } catch (ex: Exception) {
//        return false
//    } finally {
//        try {
//            inputStream?.close()
//            outputStream?.close()
//        } catch (e: IOException) {
//            // do nothing
//        }
//    }
}