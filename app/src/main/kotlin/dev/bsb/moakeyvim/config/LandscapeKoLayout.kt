package dev.bsb.moakeyvim.config

import dev.bsb.moakeyvim.R

enum class LandscapeKoLayout(val labelResId: Int) {
    NONE(R.string.settings_landscape_ko_layout_none),
    QWERTY(R.string.settings_input_mode_qwerty),
    QWERTY_SIMPLE(R.string.settings_input_mode_qwerty_simple);

    val isQwerty: Boolean get() = this != NONE
    val isSimple: Boolean get() = this == QWERTY_SIMPLE

    companion object {
        fun fromString(value: String?): LandscapeKoLayout =
            values().find { it.name == value } ?: NONE
    }
}
