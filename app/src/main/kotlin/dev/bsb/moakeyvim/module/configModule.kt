package dev.bsb.moakeyvim.module

import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import dev.bsb.moakeyvim.config.Config
import dev.bsb.moakeyvim.suggestion.Dictionary
import dev.bsb.moakeyvim.suggestion.KoreanSuggestionEngine
import dev.bsb.moakeyvim.suggestion.KoreanTrieDictionary
import dev.bsb.moakeyvim.suggestion.SharedPreferencesUserWordStore
import dev.bsb.moakeyvim.suggestion.SuggestionEngine
import dev.bsb.moakeyvim.suggestion.TrieDictionary
import dev.bsb.moakeyvim.suggestion.DefaultSeedWords
import dev.bsb.moakeyvim.suggestion.UserWordStore
import dev.bsb.moakeyvim.view.feedback.KeyFeedbackPlayer

val configModule = module {
    single { Config(androidContext()) }
    single { KeyFeedbackPlayer(androidContext()) }
    single<Dictionary>(named("en")) { TrieDictionary(androidContext()) }
    single<Dictionary>(named("ko")) { KoreanTrieDictionary(androidContext()) }
    single<UserWordStore>(named("en")) {
        SharedPreferencesUserWordStore(androidContext(), SharedPreferencesUserWordStore.Language.EN).apply {
            seedIfNeeded(DefaultSeedWords.EN, get<Config>().minLearnCount)
        }
    }
    single<UserWordStore>(named("ko")) {
        SharedPreferencesUserWordStore(androidContext(), SharedPreferencesUserWordStore.Language.KO).apply {
            seedIfNeeded(DefaultSeedWords.KO, get<Config>().minLearnCount)
        }
    }
    single { SuggestionEngine(get(named("en")), get(named("en")), get<Config>().maxSuggestionCount) }
    single { KoreanSuggestionEngine(get(named("ko")), get(named("ko")), get<Config>().maxSuggestionCount) }
}
