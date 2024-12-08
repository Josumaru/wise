package com.padangmurah.wise.util

class HtmlConverter {

    fun convertToHtml(text: String): String {
        val regex = """\s*(\d+)\.\s*(.*)""".toRegex()
        val firstSentenceRegex = Regex("\\n\\s*(.*?)\\s*\\n")

        val sourceRegex = Regex("https?://\\S+")

        val firstSentenceMatch = firstSentenceRegex.find(text)
        val sourceMatch = sourceRegex.find(text)

        val htmlContent = buildString {
            append("<p>${firstSentenceMatch?.value}</p>")

            append("<ol>\n")
            regex.findAll(text).forEach { match ->
                val content = match.groupValues[2]
                append("<li>$content</li>\n")
            }
            append("</ol>\n")
            append("<p>Sources:</p>")
            append("<p>${sourceMatch?.value}</p>")

        }

        return htmlContent
    }
    fun sourcesToHtml(text: String): String {
        // Regex to match a source in the format of "https://..." or similar
        val sourceRegex = Regex("https?://\\S+")

        val htmlContent = buildString {
            sourceRegex.findAll(text).forEach { match ->
                append("<p>${match.value}</p>")
            }
        }

        return htmlContent
    }

}