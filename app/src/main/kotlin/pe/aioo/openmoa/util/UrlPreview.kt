package pe.aioo.openmoa.util

private val URL_PREFIX_REGEX = Regex("^https?://(www\\.)?")

fun stripUrlPrefix(text: String): String = text.replaceFirst(URL_PREFIX_REGEX, "")
