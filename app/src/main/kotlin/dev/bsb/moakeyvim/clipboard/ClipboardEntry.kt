package dev.bsb.moakeyvim.clipboard

data class ClipboardEntry(
    val id: String,
    val text: String,
    val pinned: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
)
