package com.bb4first.mycashtask.utlis

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import com.bb4first.mycashtask.R
import com.bb4first.mycashtask.databinding.DialogLanguageSelectionBinding
import com.bb4first.mycashtask.enums.AppLanguage

class LanguageSelectionDialog(
    context: Context,
    private val onLanguageSelected: (lang: AppLanguage) -> Unit
) {

    private lateinit var dialog: Dialog
    private var view: View

    init {
        val binding = DialogLanguageSelectionBinding.inflate(LayoutInflater.from(context))

        view = binding.root
        dialog.setContentView(view)
        dialog.setCancelable(true)

        val radioGroup: RadioGroup = binding.radioGroupLanguage
        val btnConfirm: Button = binding.btnConfirm

        btnConfirm.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            val selectedLanguage = when (selectedId) {
                R.id.radioEnglish -> AppLanguage.ENGLISH
                R.id.radioArabic -> AppLanguage.ARABIC
                else -> return@setOnClickListener
            }
            if (LanguageConfiguration.getAppLanguageEnumObject() != selectedLanguage) {
                onLanguageSelected(selectedLanguage)
            }
            dialog.dismiss()
        }
    }

    fun show() {
        if (!dialog.isShowing) {
            dialog.show()
        }
    }

    fun dismiss() {
        dialog.dismiss()
    }

    fun isShowing(): Boolean = dialog.isShowing
}
