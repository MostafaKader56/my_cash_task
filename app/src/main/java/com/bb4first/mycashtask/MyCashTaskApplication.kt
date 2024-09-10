package com.bb4first.mycashtask

import android.app.Application
import androidx.core.content.res.ResourcesCompat
import com.bb4first.mycashtask.utlis.AppFontHelper
import com.bb4first.mycashtask.utlis.LanguageConfiguration
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyCashTaskApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppFontHelper.setDefaultFont(
            ResourcesCompat.getFont(
                this, when (LanguageConfiguration.getAppLanguage()) {
                    "ar" -> R.font.cairo_font_family
                    else -> R.font.tt_commons_font_family
                }
            )
        )

        instance = this
    }

    companion object {
        lateinit var instance: MyCashTaskApplication
            private set
    }

}