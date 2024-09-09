package com.bb4first.mycashtask.utlis

import android.content.Context
import java.util.Locale
import com.bb4first.mycashtask.utlis.SharedPreferencesModule.PREF_APP_LANGUAGE
import com.bb4first.mycashtask.enums.AppLanguage


object LanguageConfiguration {
    fun changeLanguage(language: String, context: Context): Context {
        setAppLanguage(language)
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val config = resources.configuration
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }

    fun setAppLanguage(language: String) {
        SharedPreferencesModule.setStringValue(PREF_APP_LANGUAGE, language)
    }

    fun getAppLanguage(): String {
        var language: String =
            SharedPreferencesModule.getStringValue(PREF_APP_LANGUAGE, getDeviceLanguage())
        language = when (language) {
            "العربية", AppLanguage.ARABIC.suffix -> {
                AppLanguage.ARABIC.suffix
            }

            AppLanguage.ENGLISH.suffix -> {
                AppLanguage.ENGLISH.suffix
            }

            else -> {
                AppLanguage.ENGLISH.suffix
            }
        }
        return language
    }

    fun getAppLanguageEnumObject(): AppLanguage {
        return when (SharedPreferencesModule.getStringValue(
            PREF_APP_LANGUAGE,
            getDeviceLanguage()
        )) {
            "العربية", AppLanguage.ARABIC.suffix -> {
                AppLanguage.ARABIC
            }

            AppLanguage.ENGLISH.suffix -> {
                AppLanguage.ENGLISH
            }

            else -> {
                AppLanguage.ENGLISH
            }
        }
    }

    private fun getDeviceLanguage(): String {
        return Locale.getDefault().language
    }
}
