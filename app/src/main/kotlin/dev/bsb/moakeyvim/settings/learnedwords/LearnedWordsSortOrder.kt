package dev.bsb.moakeyvim.settings.learnedwords

import java.text.Collator
import java.util.Locale

enum class LearnedWordsSortOrder {
    COUNT_DESC,
    COUNT_ASC,
    WORD_ASC,
    WORD_DESC;

    companion object {
        val DEFAULT = COUNT_DESC
        private val collator: Collator = Collator.getInstance(Locale.KOREAN)

        fun fromString(name: String?): LearnedWordsSortOrder =
            values().find { it.name == name } ?: DEFAULT

        fun compare(a: String, b: String): Int = collator.compare(a, b)
    }
}

fun List<Pair<String, Int>>.sortedByOrder(order: LearnedWordsSortOrder): List<Pair<String, Int>> =
    when (order) {
        LearnedWordsSortOrder.COUNT_DESC -> sortedByDescending { it.second }
        LearnedWordsSortOrder.COUNT_ASC -> sortedBy { it.second }
        LearnedWordsSortOrder.WORD_ASC -> sortedWith { a, b -> LearnedWordsSortOrder.compare(a.first, b.first) }
        LearnedWordsSortOrder.WORD_DESC -> sortedWith { a, b -> LearnedWordsSortOrder.compare(b.first, a.first) }
    }
