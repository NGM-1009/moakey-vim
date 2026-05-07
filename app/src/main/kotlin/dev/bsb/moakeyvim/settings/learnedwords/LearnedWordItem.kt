package dev.bsb.moakeyvim.settings.learnedwords

sealed interface LearnedWordItem {
    val word: String

    data class Word(override val word: String, val count: Int) : LearnedWordItem
    data class Blacklist(override val word: String, val isEn: Boolean) : LearnedWordItem
}

fun List<LearnedWordItem>.filterByQuery(query: String): List<LearnedWordItem> {
    val trimmed = query.trim()
    if (trimmed.isEmpty()) return this
    val lower = trimmed.lowercase()
    return filter { it.word.lowercase().contains(lower) }
}
