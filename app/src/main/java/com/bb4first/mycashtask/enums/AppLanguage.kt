package com.bb4first.mycashtask.enums

import com.bb4first.mycashtask.R

enum class AppLanguage(
    val suffix: String,
    val suffixCapital: String,
    val nameInCurrentLanguageRes: Int,
    val nameInLanguage: String
) {
    ARABIC("ar", "AR", R.string.language_arabic, "العربية"),
    ENGLISH("en", "EN", R.string.language_english, "English")
}