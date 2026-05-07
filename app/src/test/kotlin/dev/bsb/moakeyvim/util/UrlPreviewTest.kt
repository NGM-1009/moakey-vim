package dev.bsb.moakeyvim.util

import org.junit.Assert.assertEquals
import org.junit.Test

class UrlPreviewTest {

    @Test
    fun `strips https www prefix`() {
        assertEquals("example.com/path", stripUrlPrefix("https://www.example.com/path"))
    }

    @Test
    fun `strips http www prefix`() {
        assertEquals("example.com/path", stripUrlPrefix("http://www.example.com/path"))
    }

    @Test
    fun `strips https without www`() {
        assertEquals("example.com/path", stripUrlPrefix("https://example.com/path"))
    }

    @Test
    fun `strips http without www`() {
        assertEquals("example.com/path", stripUrlPrefix("http://example.com/path"))
    }

    @Test
    fun `does not strip non-url text`() {
        assertEquals("안녕하세요", stripUrlPrefix("안녕하세요"))
    }

    @Test
    fun `does not strip plain text`() {
        assertEquals("hello world", stripUrlPrefix("hello world"))
    }

    @Test
    fun `does not strip ftp url`() {
        assertEquals("ftp://example.com", stripUrlPrefix("ftp://example.com"))
    }

    @Test
    fun `strips only leading prefix not mid-string`() {
        assertEquals("example.com?ref=https://other.com", stripUrlPrefix("https://example.com?ref=https://other.com"))
    }

    @Test
    fun `handles empty string`() {
        assertEquals("", stripUrlPrefix(""))
    }

    @Test
    fun `handles url with subdomain other than www`() {
        assertEquals("sub.example.com", stripUrlPrefix("https://sub.example.com"))
    }

    @Test
    fun `handles protocol-only url`() {
        assertEquals("", stripUrlPrefix("https://"))
    }

    @Test
    fun `handles www-only url`() {
        assertEquals("", stripUrlPrefix("https://www."))
    }
}
