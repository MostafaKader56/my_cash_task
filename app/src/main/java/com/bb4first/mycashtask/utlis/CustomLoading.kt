package com.bb4first.mycashtask.utlis

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import com.bb4first.mycashtask.R
import com.bb4first.mycashtask.databinding.LayoutProgressDialogBinding

class CustomLoading(context: Context) {

    private lateinit var dialog: Dialog
    private var view: View

    init {
        val binding = LayoutProgressDialogBinding.inflate(LayoutInflater.from(context))

        view = binding.root
        if (view.windowToken == null) {
            binding.cpBgView.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorLoadingBackground
                )
            ) //Background Color
            dialog = Dialog(context, R.style.CustomProgressBarTheme)
            dialog.setContentView(view)
            dialog.setCancelable(false)
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

    fun isLoading(): Boolean = dialog.isShowing

}