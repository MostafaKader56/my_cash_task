package com.bb4first.mycashtask.utlis

import android.content.Context
import android.graphics.Typeface
import androidx.appcompat.widget.AppCompatTextView

object AppFontHelper {

    private var defaultFont: Typeface? = null

    fun setDefaultFont(font: Typeface?) {
        defaultFont = font
    }

    fun applyDefaultFont(context: Context, textView: AppCompatTextView) {
        defaultFont?.let {
            textView.typeface = it
        }
    }
}
