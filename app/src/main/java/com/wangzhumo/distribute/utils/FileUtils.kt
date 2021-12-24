package com.wangzhumo.distribute.utils

import android.util.Log
import okhttp3.ResponseBody
import okio.IOException
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream


/**
 * If you have any questions, you can contact by email {wangzhumoo@gmail.com}
 *
 * @author 王诛魔 2021/12/24 10:44
 *
 * 文件操作
 */
object FileUtils {
    fun writeResponseBodyToDisk(body: ResponseBody, path: String): Boolean {
        return try {
            val futureStudioIconFile = File(path)
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            try {
                val fileReader = ByteArray(4096)
                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0
                inputStream = body.byteStream()
                outputStream = FileOutputStream(futureStudioIconFile)
                while (true) {
                    val read: Int = inputStream.read(fileReader)
                    if (read == -1) {
                        break
                    }
                    outputStream.write(fileReader, 0, read)
                    fileSizeDownloaded += read.toLong()
                    Log.d("FileDown", "file download: $fileSizeDownloaded of $fileSize")
                }
                outputStream.flush()
                true
            } catch (e: IOException) {
                false
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: IOException) {
            false
        }
    }
}