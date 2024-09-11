package com.bb4first.mycashtask.ui.components

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.bb4first.mycashtask.R
import com.bb4first.mycashtask.databinding.ErrorPlaceholderComponentBinding

class ErrorPlaceholderComponent(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    // Inflate the view using ViewBinding
    private lateinit var binding: ErrorPlaceholderComponentBinding

    init {
        if (!isInEditMode) {
            createView(attrs)
        }
    }

    private fun createView(attrs: AttributeSet?) {
        binding = ErrorPlaceholderComponentBinding
            .inflate(android.view.LayoutInflater.from(context), this)

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.ErrorPlaceholderComponent, 0, 0
        )

        typedArray.recycle()

    }

    fun setOnRetryClickListener(onClickListener: OnClickListener) {
        binding.btnRetry.setOnClickListener(onClickListener)
    }

    fun setError(error: String) {
        binding.txtError.text = error
    }
}