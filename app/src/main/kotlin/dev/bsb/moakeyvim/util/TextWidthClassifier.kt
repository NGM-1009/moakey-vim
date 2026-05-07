package dev.bsb.moakeyvim.util

object TextWidthClassifier {

    fun isWideGlyphText(text: String): Boolean {
        var i = 0
        while (i < text.length) {
            val cp = text.codePointAt(i)
            if (isWideCodePoint(cp)) return true
            i += Character.charCount(cp)
        }
        return false
    }

    private fun isWideCodePoint(cp: Int): Boolean = cp in 0xAC00..0xD7A3   // 한글 음절
        || cp in 0x1100..0x11FF   // 한글 자모
        || cp in 0x3130..0x318F   // 한글 호환 자모
        || cp in 0xA960..0xA97F   // 한글 자모 확장 A
        || cp in 0xD7B0..0xD7FF   // 한글 자모 확장 B
        || cp in 0x3040..0x309F   // 히라가나
        || cp in 0x30A0..0x30FF   // 가타카나
        || cp in 0x31F0..0x31FF   // 가타카나 음성 확장
        || cp in 0x4E00..0x9FFF   // CJK 통합 한자
        || cp in 0x3400..0x4DBF   // CJK 한자 확장 A
        || cp in 0xF900..0xFAFF   // CJK 호환 한자
        || cp in 0x3000..0x303F   // CJK 기호와 구두점
        || cp in 0xFF01..0xFF60   // 전각 라틴/기호
        || cp in 0xFFE0..0xFFE6   // 전각 통화/기호
        || cp in 0x1F000..0x1FAFF // 이모지, 마장패, 기타 기호
        || cp in 0x20000..0x2CEAF // CJK 한자 확장 B~E
}
