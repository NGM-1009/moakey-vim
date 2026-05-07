package pe.aioo.openmoa.settings.learnedwords

import org.junit.Assert.assertEquals
import org.junit.Test

class LearnedWordsSortOrderTest {

    private val sample = listOf(
        "banana" to 5,
        "apple" to 10,
        "cherry" to 5,
        "date" to 1,
    )

    @Test
    fun `COUNT_DESC sorts by count descending`() {
        val result = sample.sortedByOrder(LearnedWordsSortOrder.COUNT_DESC)
        assertEquals(10, result[0].second)
        assertEquals(1, result[3].second)
    }

    @Test
    fun `COUNT_ASC sorts by count ascending`() {
        val result = sample.sortedByOrder(LearnedWordsSortOrder.COUNT_ASC)
        assertEquals(1, result[0].second)
        assertEquals(10, result[3].second)
    }

    @Test
    fun `WORD_ASC sorts alphabetically ascending`() {
        val result = sample.sortedByOrder(LearnedWordsSortOrder.WORD_ASC)
        assertEquals("apple", result[0].first)
        assertEquals("date", result[3].first)
    }

    @Test
    fun `WORD_DESC sorts alphabetically descending`() {
        val result = sample.sortedByOrder(LearnedWordsSortOrder.WORD_DESC)
        assertEquals("date", result[0].first)
        assertEquals("apple", result[3].first)
    }

    @Test
    fun `fromString returns DEFAULT for unknown name`() {
        assertEquals(LearnedWordsSortOrder.DEFAULT, LearnedWordsSortOrder.fromString(null))
        assertEquals(LearnedWordsSortOrder.DEFAULT, LearnedWordsSortOrder.fromString("INVALID"))
    }

    @Test
    fun `fromString round-trips all values`() {
        LearnedWordsSortOrder.values().forEach { order ->
            assertEquals(order, LearnedWordsSortOrder.fromString(order.name))
        }
    }
}
