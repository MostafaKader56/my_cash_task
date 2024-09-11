package com.bb4first.mycashtask.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.bb4first.mycashtask.R
import com.bb4first.mycashtask.databinding.ProgressPlaceHolderBinding

class ProgressIndicatorPlaceholderComponent(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    // Inflate the view using ViewBinding
    private lateinit var binding: ProgressPlaceHolderBinding


    init {
        if (!isInEditMode) {
            createView(attrs)
        }
    }

    private fun createView(attrs: AttributeSet?) {
        binding = ProgressPlaceHolderBinding
            .inflate(android.view.LayoutInflater.from(context), this)

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.ProgressIndicatorPlaceholderComponent, 0, 0
        )

        typedArray.recycle()
    }

    fun startLoading() {
        binding.loader.visibility = View.VISIBLE
    }

    fun stopLoading() {
        this.visibility = View.GONE
    }
}