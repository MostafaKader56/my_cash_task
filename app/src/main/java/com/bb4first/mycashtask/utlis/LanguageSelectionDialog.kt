package com.bb4first.mycashtask.utlis

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.bb4first.mycashtask.databinding.DialogLanguageSelectionBinding
import com.bb4first.mycashtask.enums.AppLanguage

class LanguageSelectionDialog(
    context: Context,
    private val onLanguageSelected: (lang: AppLanguage) -> Unit
) {
    private val dialog: Dialog = Dialog(context)
    private val binding: DialogLanguageSelectionBinding =
        DialogLanguageSelectionBinding.inflate(LayoutInflater.from(context))

    init {
        dialog.setContentView(binding.root)
        dialog.setCancelable(true)

        binding.root.setBackgroundColor(context.getColor(android.R.color.white))

        binding.btnArabic.setOnClickListener {
            if (LanguageConfiguration.getAppLanguageEnumObject() != AppLanguage.ARABIC) {
                onLanguageSelected(AppLanguage.ARABIC)
            }
            dialog.dismiss()
        }
        binding.btnEnglish.setOnClickListener {
            if (LanguageConfiguration.getAppLanguageEnumObject() != AppLanguage.ENGLISH) {
                onLanguageSelected(AppLanguage.ENGLISH)
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
