package com.padangmurah.wise.util

import java.text.SimpleDateFormat
import java.util.Locale

class DateFormatter {

    companion object {
        fun formatReadableDate(dateString: String): String {
            return try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                val date = inputFormat.parse(dateString)

                val outputFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.US)
                date?.let { outputFormat.format(it) } ?: "Invalid date"
            } catch (e: Exception) {
                "Invalid date"
            }
        }
    }
}
