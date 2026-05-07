package dev.bsb.moakeyvim.util

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TextWidthClassifierTest {

    @Test
    fun `returns true for hangul syllables`() {
        assertTrue(TextWidthClassifier.isWideGlyphText("안녕"))
    }

    @Test
    fun `returns true for hangul jamo`() {
        assertTrue(TextWidthClassifier.isWideGlyphText("ㅎㅏ"))
    }

    @Test
    fun `returns true for hiragana`() {
        assertTrue(TextWidthClassifier.isWideGlyphText("こんにちは"))
    }

    @Test
    fun `returns true for katakana`() {
        assertTrue(TextWidthClassifier.isWideGlyphText("カタカナ"))
    }

    @Test
    fun `returns true for cjk unified ideographs`() {
        assertTrue(TextWidthClassifier.isWideGlyphText("漢字"))
    }

    @Test
    fun `returns false for half-width katakana`() {
        assertFalse(TextWidthClassifier.isWideGlyphText("ｱｲｳ"))
    }

    @Test
    fun `returns true for emoji surrogate pair`() {
        assertTrue(TextWidthClassifier.isWideGlyphText("🎉"))
    }

    @Test
    fun `returns true when text mixes ascii and japanese`() {
        assertTrue(TextWidthClassifier.isWideGlyphText("Hello世界"))
    }

    @Test
    fun `returns false for pure ascii`() {
        assertFalse(TextWidthClassifier.isWideGlyphText("Hello World"))
    }

    @Test
    fun `returns false for latin extended accented`() {
        assertFalse(TextWidthClassifier.isWideGlyphText("café résumé"))
    }

    @Test
    fun `returns false for empty string`() {
        assertFalse(TextWidthClassifier.isWideGlyphText(""))
    }

    @Test
    fun `returns true for cjk compatibility ideographs`() {
        assertTrue(TextWidthClassifier.isWideGlyphText("豈"))
    }

    @Test
    fun `returns true for cjk symbols and punctuation`() {
        assertTrue(TextWidthClassifier.isWideGlyphText("。"))
    }

    @Test
    fun `returns false for arabic`() {
        assertFalse(TextWidthClassifier.isWideGlyphText("مرحبا"))
    }

    @Test
    fun `returns false for cyrillic`() {
        assertFalse(TextWidthClassifier.isWideGlyphText("Привет"))
    }
}
