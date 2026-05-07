package dev.bsb.moakeyvim.suggestion

object WordTokenizer {

    private const val MAX_LENGTH = 30
    private const val MIN_KOREAN_LENGTH = 2
    private const val MIN_ENGLISH_LENGTH = 4

    // 긴 것부터 먼저 매칭해야 "에게서"가 "에게"보다, "에서"가 "에"보다 먼저 제거됨
    private val KO_SUFFIXES = listOf(
        "에게서", "한테서",
        "으로", "에서", "에게", "한테",
        "와", "과", "로", "은", "는", "이", "가", "을", "를",
        "에", "도", "만", "의",
    )

    /** 유효성 검증만 수행. 조사·어미를 그대로 유지한 원형을 반환. 학습 저장용. */
    fun normalizeKorean(text: String): String? {
        val trimmed = validKorean(text) ?: return null
        return if (trimmed.length < MIN_KOREAN_LENGTH) null else trimmed
    }

    /** 후행 조사를 제거하고 어간만 반환. 사전 조회용. */
    fun extractKorean(text: String): String? {
        val trimmed = validKorean(text) ?: return null
        val stem = KO_SUFFIXES.firstOrNull { trimmed.endsWith(it) }
            ?.let { trimmed.dropLast(it.length) }
            ?: trimmed
        return if (stem.length < MIN_KOREAN_LENGTH) null else stem
    }

    private fun validKorean(text: String): String? {
        val trimmed = text.trim()
        if (trimmed.isEmpty() || trimmed.length > MAX_LENGTH) return null
        if (!trimmed.all { it.isKorean() }) return null
        return trimmed
    }

    // U+AC00..U+D7A3: 완성형, U+1100..U+11FF: 자모, U+3131..U+318E: 호환 자모
    private fun Char.isKorean() =
        this in '가'..'힣' || this in 'ᄀ'..'ᇿ' || this in 'ㄱ'..'ㆎ'

    fun extractEnglish(text: String): String? {
        val trimmed = text.trim()
        if (trimmed.length < MIN_ENGLISH_LENGTH || trimmed.length > MAX_LENGTH) return null

        if (!trimmed.first().isAsciiLetterOrDigit() || !trimmed.last().isAsciiLetterOrDigit()) return null

        val specials = setOf('.', '@', '-')
        var prevWasSpecial = false
        var atCount = 0
        for (ch in trimmed) {
            when {
                ch.isLetter() && ch.code < 128 -> prevWasSpecial = false
                ch.isDigit() && ch.code < 128 -> prevWasSpecial = false
                ch in specials -> {
                    if (prevWasSpecial) return null
                    if (ch == '@' && ++atCount > 1) return null
                    prevWasSpecial = true
                }
                else -> return null
            }
        }

        return trimmed.lowercase()
    }

    private fun Char.isAsciiLetterOrDigit() = (isLetter() || isDigit()) && code < 128
}
