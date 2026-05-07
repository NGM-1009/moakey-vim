package dev.bsb.moakeyvim.hardware

sealed interface ShortcutAction {
    object Pass : ShortcutAction
    object ConsumeToggleLanguage : ShortcutAction
}
