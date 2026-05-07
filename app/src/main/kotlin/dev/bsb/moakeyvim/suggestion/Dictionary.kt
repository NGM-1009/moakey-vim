package dev.bsb.moakeyvim.suggestion

interface Dictionary {
    suspend fun prefix(prefix: String, limit: Int): List<String>
    suspend fun chosung(pattern: String, limit: Int): List<String> = emptyList()
    suspend fun contains(word: String): Boolean = false
}
