package com.bb4first.mycashtask.ui.components

import android.content.Context
import android.os.Build
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.bb4first.mycashtask.R
import com.bb4first.mycashtask.databinding.EdittextComponentBinding
import com.bb4first.mycashtask.utlis.Utils.onChange

class EditTextComponent(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    // Inflate the view using ViewBinding
    private var binding: EdittextComponentBinding = EdittextComponentBinding
        .inflate(LayoutInflater.from(context), this)

    var text: String
        get() = binding.edittext.text.toString().trim()
        set(value) = binding.edittext.setText(value)

    var errorMessage: String = ""
        set(value) {
            binding.tvError.apply {
                // We can make it Visible and Invisible instead of Gone and Visible.
                visibility = if (value.isEmpty()) View.GONE else View.VISIBLE
                text = value
            }
            field = value
        }

    init {
        if (!isInEditMode) {
            createView(attrs)
        }
    }

    private fun createView(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.EditTextComponent, 0, 0
        )

        val componentText =
            typedArray.getString(R.styleable.EditTextComponent_component_text) ?: ""
        val componentHint =
            typedArray.getString(R.styleable.EditTextComponent_component_hint) ?: ""
        val componentLabel =
            typedArray.getString(R.styleable.EditTextComponent_component_label) ?: ""
        val componentInputType = typedArray.getInt(
            R.styleable.EditTextComponent_text_inputType,
            TextInputType.TEXT.type
        )
        val autofillHints = typedArray.getString(R.styleable.EditTextComponent_autofillHints)

        typedArray.recycle()

        text = componentText
        binding.label.text = componentLabel
        binding.edittext.hint = componentHint
        binding.edittext.inputType = when (componentInputType) {
            TextInputType.TEXT.type -> InputType.TYPE_CLASS_TEXT
            TextInputType.NUMBER.type -> InputType.TYPE_CLASS_NUMBER
            TextInputType.TEXT_MULTI_LINE.type -> InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_CLASS_TEXT
            TextInputType.EMAIL.type -> InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            TextInputType.PASSWORD.type -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            TextInputType.PHONE.type -> InputType.TYPE_CLASS_PHONE
            TextInputType.CURRENCY.type -> InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            else -> InputType.TYPE_CLASS_TEXT
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.edittext.setAutofillHints(
                *autofillHints?.split(",")?.toTypedArray() ?: emptyArray()
            )
        }

        binding.edittext.onChange {
            if (errorMessage.isNotEmpty())
                errorMessage = ""
        }
    }

    // We can add more customizations here.

    enum class TextInputType(val type: Int) {
        TEXT(0),
        EMAIL(1),
        PASSWORD(2),
        PHONE(3),
        CURRENCY(4),
        TEXT_MULTI_LINE(5),
        NUMBER(6)
    }
}