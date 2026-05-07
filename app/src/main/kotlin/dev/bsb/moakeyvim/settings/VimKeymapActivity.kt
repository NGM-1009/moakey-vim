package dev.bsb.moakeyvim.settings

import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import dev.bsb.moakeyvim.R
import dev.bsb.moakeyvim.databinding.ActivityVimKeymapBinding

class VimKeymapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVimKeymapBinding

    private data class KeyRow(val key: String, val description: String)
    private data class Section(val titleRes: Int, val rows: List<KeyRow>)

    private val sections = listOf(
        Section(R.string.vim_section_move, listOf(
            KeyRow("h / j / k / l", "커서 이동  ←↓↑→"),
            KeyRow("w / b", "다음 / 이전 단어"),
            KeyRow("e / \$", "줄 끝으로"),
            KeyRow("r / 0 / ^", "줄 처음으로"),
            KeyRow("f", "페이지 아래"),
            KeyRow("Shift + f", "페이지 위"),
            KeyRow("g", "문서 처음"),
            KeyRow("Shift + g", "문서 끝"),
        )),
        Section(R.string.vim_section_edit, listOf(
            KeyRow("x / .", "커서 글자 삭제"),
            KeyRow(",", "Backspace"),
            KeyRow("n", "단어 뒤로 삭제"),
            KeyRow("d", "줄 삭제"),
            KeyRow("u / Shift + u", "실행 취소 / 다시 실행"),
            KeyRow("y", "줄 복사"),
            KeyRow("p", "붙여넣기"),
            KeyRow("i / Shift + o", "위에 새 줄 삽입"),
            KeyRow("o", "아래에 새 줄 삽입"),
        )),
        Section(R.string.vim_section_visual, listOf(
            KeyRow("v", "선택 시작 / 해제"),
            KeyRow("이동 키 조합", "선택 영역 확장"),
            KeyRow("c / d / x", "선택 삭제"),
            KeyRow("y", "선택 복사"),
            KeyRow("Esc", "선택 해제"),
        )),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVimKeymapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        buildContent()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun buildContent() {
        val container = binding.contentContainer
        val density = resources.displayMetrics.density

        addHintText(container, getString(R.string.vim_intro_hint), density)
        sections.forEach { section ->
            addSectionHeader(container, getString(section.titleRes), density)
            section.rows.forEach { row -> addKeyRow(container, row.key, row.description, density) }
        }
    }

    private fun addHintText(container: LinearLayout, text: String, density: Float) {
        container.addView(TextView(this).apply {
            this.text = text
            textSize = 13f
            setTextColor(resolveAttrColor(android.R.attr.textColorSecondary))
            setPadding(0, 0, 0, (16 * density).toInt())
        })
    }

    private fun addSectionHeader(container: LinearLayout, title: String, density: Float) {
        container.addView(TextView(this).apply {
            text = title
            textSize = 13f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(resolveAttrColor(android.R.attr.colorPrimary))
            setPadding(0, (20 * density).toInt(), 0, (6 * density).toInt())
        })
    }

    private fun addKeyRow(container: LinearLayout, key: String, description: String, density: Float) {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(0, (5 * density).toInt(), 0, (5 * density).toInt())
            gravity = Gravity.CENTER_VERTICAL
        }
        row.addView(TextView(this).apply {
            text = key
            textSize = 13f
            setTypeface(android.graphics.Typeface.MONOSPACE, android.graphics.Typeface.BOLD)
            setTextColor(resolveAttrColor(android.R.attr.textColorPrimary))
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.42f)
        })
        row.addView(TextView(this).apply {
            text = description
            textSize = 14f
            setTextColor(resolveAttrColor(android.R.attr.textColorSecondary))
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.58f)
        })
        container.addView(row)
    }

    private fun resolveAttrColor(attr: Int): Int {
        val ta = obtainStyledAttributes(intArrayOf(attr))
        return try { ta.getColor(0, 0) } finally { ta.recycle() }
    }
}
