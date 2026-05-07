package dev.bsb.moakeyvim.quickphrase

import android.content.Context
import dev.bsb.moakeyvim.settings.SettingsPreferences

object QwertyLongKeyRepository {

    fun getPhrase(context: Context, key: QwertyLongKey): String {
        val value = context.getSharedPreferences(SettingsPreferences.PREFS_NAME, Context.MODE_PRIVATE)
            .getString(key.prefKey, null)
        return if (value.isNullOrEmpty()) key.defaultPhrase else value
    }

}
