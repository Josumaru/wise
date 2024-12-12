package com.padangmurah.wise.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import com.padangmurah.wise.util.UriConverter.Companion.MAXIMAL_SIZE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UriConverter {
    suspend fun uriToFile(imageUri: Uri, context: Context): File = withContext(Dispatchers.IO) {
        val myFile = createCustomTempFile(context)
        val inputStream = context.contentResolver.openInputStream(imageUri) as InputStream
        val outputStream = FileOutputStream(myFile)
        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(
            buffer,
            0,
            length
        )
        outputStream.close()
        inputStream.close()
        myFile
    }

    private fun createCustomTempFile(context: Context): File {
        val filesDir = context.externalCacheDir
        val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())
        return File.createTempFile(timeStamp, ".jpg", filesDir)
    }


    companion object {
        const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
        const val MAXIMAL_SIZE = 1000000
    }
}

suspend fun File.reduceFileImage(): File = withContext(Dispatchers.IO) {
    val bitmap =
        BitmapFactory.decodeFile(this@reduceFileImage.path).getRotatedBitmap(this@reduceFileImage)
    var compressQuality = 100
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > MAXIMAL_SIZE)
    bitmap.compress(
        Bitmap.CompressFormat.JPEG,
        compressQuality,
        FileOutputStream(this@reduceFileImage)
    )
    this@reduceFileImage
}

fun Bitmap.getRotatedBitmap(file: File): Bitmap {
    val orientation = ExifInterface(file).getAttributeInt(
        ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED
    )
    return when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(this, 90F)
        ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(this, 180F)
        ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(this, 270F)
        ExifInterface.ORIENTATION_NORMAL -> this
        else -> this
    }
}

fun rotateImage(source: Bitmap, angle: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(
        source, 0, 0, source.width, source.height, matrix, true
    )
}