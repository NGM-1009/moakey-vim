package dev.bsb.moakeyvim.settings.learnedwords

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LearnedWordsFilterTest {

    private val words = listOf(
        LearnedWordItem.Word("apple", 5),
        LearnedWordItem.Word("banana", 3),
        LearnedWordItem.Word("apricot", 2),
        LearnedWordItem.Word("사과", 4),
        LearnedWordItem.Word("바나나", 1),
    )

    @Test
    fun `empty query returns all items`() {
        val result = words.filterByQuery("")
        assertEquals(5, result.size)
    }

    @Test
    fun `blank query returns all items`() {
        val result = words.filterByQuery("   ")
        assertEquals(5, result.size)
    }

    @Test
    fun `partial match filters correctly`() {
        val result = words.filterByQuery("ap")
        assertEquals(2, result.size)
        assertTrue(result.all { (it as LearnedWordItem.Word).word.startsWith("ap") })
    }

    @Test
    fun `case insensitive for english`() {
        val result = words.filterByQuery("AP")
        assertEquals(2, result.size)
    }

    @Test
    fun `korean partial match works`() {
        val result = words.filterByQuery("사")
        assertEquals(1, result.size)
        assertEquals("사과", (result[0] as LearnedWordItem.Word).word)
    }

    @Test
    fun `no match returns empty list`() {
        val result = words.filterByQuery("xyz")
        assertTrue(result.isEmpty())
    }

    @Test
    fun `blacklist items are also filtered`() {
        val items = listOf(
            LearnedWordItem.Blacklist("spam", false),
            LearnedWordItem.Blacklist("english_spam", true),
            LearnedWordItem.Word("spaniel", 1),
        )
        val result = items.filterByQuery("spa")
        assertEquals(3, result.size)
    }
}
