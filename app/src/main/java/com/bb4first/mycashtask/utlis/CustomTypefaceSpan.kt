package com.bb4first.mycashtask.utlis

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.TypefaceSpan

// This class used to set custom font to part of texts
class CustomTypefaceSpan(family: String, type: Typeface) : TypefaceSpan(family) {
    private var newType: Typeface = type

    override fun updateDrawState(ds: TextPaint) {
        applyCustomTypeFace(ds, newType)
    }

    override fun updateMeasureState(paint: TextPaint) {
        applyCustomTypeFace(paint, newType)
    }

    private fun applyCustomTypeFace(paint: Paint, tf: Typeface) {
        val oldStyle: Int
        val old: Typeface? = paint.typeface
        oldStyle = if (old == null) 0 else old.style
        val fake = oldStyle and tf.style.inv()
        if (fake and Typeface.BOLD != 0) {
            paint.isFakeBoldText = true
        }
        if (fake and Typeface.ITALIC != 0) {
            paint.textSkewX = -0.25f
        }
        paint.typeface = tf
    }
}