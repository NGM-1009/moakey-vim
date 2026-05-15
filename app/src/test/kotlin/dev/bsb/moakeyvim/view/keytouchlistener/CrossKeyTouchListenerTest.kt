package dev.bsb.moakeyvim.view.keytouchlistener

import dev.bsb.moakeyvim.view.message.StringKeyMessage
import org.junit.Assert.assertEquals
import org.junit.Test

class CrossKeyTouchListenerTest {

    private val threshold = 50f

    private fun testKeyList() = listOf(
        StringKeyMessage("UP"),
        StringKeyMessage("RIGHT"),
        StringKeyMessage("CENTER"),
        StringKeyMessage("LEFT"),
    )

    private fun moeumKeyList() = listOf(
        StringKeyMessage("ㆍ"),
        StringKeyMessage("ㅡ"),
        StringKeyMessage("ㆍ"),
        StringKeyMessage("ㅣ"),
    )

    @Test
    fun `임계값 이하 이동은 CENTER키 반환`() {
        val result = resolveKeyFromGesture(0f, 0f, 10f, 10f, threshold, testKeyList())
        assertEquals("CENTER", result.key)
    }

    @Test
    fun `위로 스와이프하면 keyList-0 반환`() {
        val result = resolveKeyFromGesture(0f, 100f, 0f, 0f, threshold, testKeyList())
        assertEquals("UP", result.key)
    }

    @Test
    fun `오른쪽으로 스와이프하면 keyList-1 반환`() {
        val result = resolveKeyFromGesture(0f, 0f, 100f, 0f, threshold, testKeyList())
        assertEquals("RIGHT", result.key)
    }

    @Test
    fun `아래로 스와이프하면 keyList-2 반환`() {
        val result = resolveKeyFromGesture(0f, 0f, 0f, 100f, threshold, testKeyList())
        assertEquals("CENTER", result.key)
    }

    @Test
    fun `왼쪽으로 스와이프하면 keyList-3 반환`() {
        val result = resolveKeyFromGesture(100f, 0f, 0f, 0f, threshold, testKeyList())
        assertEquals("LEFT", result.key)
    }

    @Test
    fun `임계값 경계값 - threshold 초과 시 방향 감지`() {
        val justOver = resolveKeyFromGesture(0f, 100f, 0f, 49f, threshold, testKeyList())
        assertEquals("UP", justOver.key)
    }

    @Test
    fun `임계값 경계값 - threshold 이하 시 CENTER 반환`() {
        val justUnder = resolveKeyFromGesture(0f, 0f, 0f, 50f, threshold, testKeyList())
        assertEquals("CENTER", justUnder.key)
    }

    @Test
    fun `모아키 모음키 위로 스와이프는 아래아 단일 반환 - 이중 아래아 아님`() {
        val result = resolveKeyFromGesture(0f, 100f, 0f, 0f, threshold, moeumKeyList())
        assertEquals("ㆍ", result.key)
        assert(result.key != "ᆢ") { "위로 스와이프에 아래아 이중(ᆢ)이 반환되면 안 됨" }
    }
}
