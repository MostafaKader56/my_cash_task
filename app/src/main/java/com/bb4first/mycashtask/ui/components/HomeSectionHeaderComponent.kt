package com.bb4first.mycashtask.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bb4first.mycashtask.R
import com.bb4first.mycashtask.databinding.HomeSectionHeaderComponentBinding

class HomeSectionHeaderComponent(context: Context, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {
    // Inflate the view using ViewBinding
    private var binding: HomeSectionHeaderComponentBinding = HomeSectionHeaderComponentBinding
        .inflate(LayoutInflater.from(context), this)


    private var onClickListener: OnClickListener? = null
    fun setOnViewAllClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    init {
        if (!isInEditMode) {
            orientation = HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            createView(attrs)
        }
    }

    private fun createView(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.HomeSectionHeaderComponent, 0, 0
        )
        val componentText =
            typedArray.getString(R.styleable.HomeSectionHeaderComponent_header_text) ?: ""
        typedArray.recycle()
    }
}
