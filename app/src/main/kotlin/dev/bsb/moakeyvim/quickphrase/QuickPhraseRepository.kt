package dev.bsb.moakeyvim.quickphrase

import android.content.Context
import dev.bsb.moakeyvim.settings.SettingsPreferences

object QuickPhraseRepository {

    fun getPhrase(context: Context, key: QuickPhraseKey): String {
        val value = context.getSharedPreferences(SettingsPreferences.PREFS_NAME, Context.MODE_PRIVATE)
            .getString(key.prefKey, null)
        return if (value.isNullOrEmpty()) key.defaultPhrase else value
    }

    fun getFirstChar(context: Context, key: QuickPhraseKey): String {
        val phrase = getPhrase(context, key)
        return phrase.firstOrNull()?.toString() ?: ""
    }
}
