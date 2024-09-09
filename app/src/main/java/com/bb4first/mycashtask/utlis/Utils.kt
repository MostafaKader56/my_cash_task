package com.bb4first.mycashtask.utlis

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Typeface
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Log
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import com.bb4first.mycashtask.MyCashTaskApplication
import com.bb4first.mycashtask.R
import com.bb4first.mycashtask.enums.AppLanguage
import com.bb4first.mycashtask.utlis.Utils.logout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.lang.StringBuilder
import java.util.Date
import java.util.Locale


object Utils {
    fun Activity.hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
    }

    fun AutoCompleteTextView.onChange(onChange: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                onChange.invoke(s?.toString() ?: "")
            }

        })
    }

    fun EditText.onChange(onChange: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                onChange.invoke(s?.toString() ?: "")
            }

        })
    }

    fun String.isEmailValid(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    fun Activity.showAlert(
        title: String,
        message: String,
        canCancel: Boolean = true,
        textPositiveButton: String = getString(R.string.okay),
        onClick: (() -> Unit)? = null
    ) {
        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(canCancel)
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(textPositiveButton) { _, _ ->
                onClick?.invoke()
            }
            .show()
    }

    fun goToAppDetailsSettings(activity: Activity) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", activity.packageName, null)
        }
        activity.startActivityForResult(intent, 0)
    }

    fun Activity.screenWidth(): Int {
        val displayMetrics: DisplayMetrics = resources.displayMetrics
        return displayMetrics.widthPixels
    }

    fun Activity.screenHeight(): Int {
        val displayMetrics: DisplayMetrics = resources.displayMetrics
        return displayMetrics.heightPixels
    }

    fun getRegularFont(context: Context): CustomTypefaceSpan {
        val appLanguage = SharedPreferencesModule.getStringValue(
            SharedPreferencesModule.PREF_APP_LANGUAGE,
            AppLanguage.ENGLISH.suffix
        )
        return if (appLanguage == AppLanguage.ENGLISH.suffix) {
            CustomTypefaceSpan("", Typeface.createFromAsset(context.assets, "fonts/" + "sen.ttf"))
        } else {
            CustomTypefaceSpan(
                "",
                Typeface.createFromAsset(context.assets, "fonts/" + "tajawal_regular.ttf")
            )
        }
    }

    fun getRegularFontTypeFace(context: Context): Typeface {
        val appLanguage = SharedPreferencesModule.getStringValue(
            SharedPreferencesModule.PREF_APP_LANGUAGE,
            AppLanguage.ENGLISH.suffix
        )
        return if (appLanguage == AppLanguage.ENGLISH.suffix) {
            Typeface.createFromAsset(context.assets, "fonts/" + "sen.ttf")
        } else {
            Typeface.createFromAsset(context.assets, "fonts/" + "tajawal_regular.ttf")
        }
    }

    fun getBoldFont(context: Context): CustomTypefaceSpan {
        val appLanguage = SharedPreferencesModule.getStringValue(
            SharedPreferencesModule.PREF_APP_LANGUAGE,
            AppLanguage.ENGLISH.suffix
        )
        return if (appLanguage == AppLanguage.ENGLISH.suffix) {
            CustomTypefaceSpan(
                "",
                Typeface.createFromAsset(context.assets, "fonts/" + "sen_bold.ttf")
            )
        } else {
            CustomTypefaceSpan(
                "",
                Typeface.createFromAsset(context.assets, "fonts/" + "tajawal_bold.ttf")
            )
        }
    }

    fun getBoldFontTypeFace(context: Context): Typeface {
        val appLanguage = SharedPreferencesModule.getStringValue(
            SharedPreferencesModule.PREF_APP_LANGUAGE,
            AppLanguage.ENGLISH.suffix
        )
        return if (appLanguage == AppLanguage.ENGLISH.suffix) {
            Typeface.createFromAsset(context.assets, "fonts/" + "sen_bold.ttf")
        } else {
            Typeface.createFromAsset(context.assets, "fonts/" + "tajawal_bold.ttf")
        }
    }

    fun String.englishNumbersFormat(): String {
        var number = this
        number = number.replace("١", "1").replace("٢", "2").replace("٣", "3")
            .replace("٤", "4").replace("٥", "5").replace("٦", "6")
            .replace("٧", "7").replace("٨", "8").replace("٠", "0")
            .replace("٩", "9").replace(",", ".")
        return number
    }

    @Suppress("DEPRECATION")
    fun Context.getAppCodeVersion(): String {
        var pinfo: PackageInfo? = null
        try {
            pinfo = packageManager.getPackageInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return pinfo?.versionName ?: ""
    }

    fun Context.getDeviceName(): String {
        return (Settings.Global.getString(this.contentResolver, "device_name")) ?: ""
    }

    @SuppressLint("HardwareIds")
    fun Context?.getOurDeviceId(): String = try {
        Settings.Secure.getString(this?.contentResolver, Settings.Secure.ANDROID_ID)
    } catch (e: Exception) {
        "FirebaseMessaging.getInstance().token.result"
    }

    fun currentVersion(): String {
        val release = java.lang.Double.parseDouble(
            java.lang.String(Build.VERSION.RELEASE).replaceAll("(\\d+[.]\\d+)(.*)", "$1")
        )
        var codeName = "Unsupported"//below Jelly Bean
        if (release >= 4.1 && release < 4.4) codeName = "Jelly Bean"
        else if (release < 5) codeName = "Kit Kat"
        else if (release < 6) codeName = "Lollipop"
        else if (release < 7) codeName = "Marshmallow"
        else if (release < 8) codeName = "Nougat"
        else if (release < 9) codeName = "Oreo"
        else if (release < 10) codeName = "Pie"
        else if (release >= 10) codeName =
            "Android " + (release.toInt())//since API 29 no more candy code names
        return codeName + " v" + release + ", API Level: " + Build.VERSION.SDK_INT
    }

    fun openWhatsapp(context: Context, phone: String) {
        val url = "https://api.whatsapp.com/send?phone=$phone"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        context.startActivity(i)
    }

    fun makePhoneCall(context: Context, phone: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
        context.startActivity(intent)
    }

    fun openAppOnGooglePlay(activity: Activity) {
        try {
            activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.bb4first.mycashtask")
                )
            )
        } catch (e: Exception) {
            activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.bb4first.mycashtask")
                )
            )
        }
    }

    fun showAlertDialog(
        context: Context,
        dismissible: Boolean = true,
        title: String,
        message: String,
        positiveButton: String? = "OK",
        negativeButton: String? = "Cancel",
        positiveAction: (() -> Unit)? = null,
        negativeAction: (() -> Unit)? = null,
        onDismissListener: (() -> Unit)? = null,
    ) {
        val builder = AlertDialog.Builder(context)

        // Set title and message
        builder.apply {
            setTitle(title)
            setMessage(message)
        }

        // Add positive button
        if (positiveButton != null) {
            builder.setPositiveButton(positiveButton) { dialog, _ ->
                dialog.dismiss()
                positiveAction?.invoke()
            }
        }

        // Add negative button
        if (negativeButton != null) {
            builder.setNegativeButton(negativeButton) { dialog, _ ->
                dialog.dismiss()
                negativeAction?.invoke()
            }
        }

        builder.setOnDismissListener { onDismissListener?.invoke() }

        // Set dismissable behavior
        builder.setCancelable(dismissible)

        // Show the dialog
        builder.create().show()
    }


    fun String.isPhoneNumberValid(): Boolean {
        val regex = Regex("^\\+[0-9]{1,}$")
        return regex.matches(this)
    }


    fun String.isPhoneNumberValidWithoutPlus(): Boolean {
        val regex = Regex("^\\+?[0-9]{1,}$")
        return regex.matches(this)
    }

    fun String.isNationNumberValid(): Boolean {
        val numberRegex = Regex("""^\d{1,}$""")
        return numberRegex.matches(this)
    }

    fun getValidPhoneNumber(text: String): String {
        return if (text.isEmpty()) {
            ""
        } else if (text[0] == '+') {
            text
        } else if (text.length > 3 && text.subSequence(0, 3) == "965") {
            "+$text"
        } else {
            "+965$text"
        }
    }

    fun EditText.isEmailValid(): Boolean {
        val text = this.text.toString().trim()
        return text.isEmailValid()
    }

    fun String.toast() {
        Toast.makeText(MyCashTaskApplication.instance, this, Toast.LENGTH_SHORT).show()
    }

    fun getTimeStampUtc(): Long {
        return System.currentTimeMillis()
    }

    fun doAfterMilliseconds(fn: () -> Unit, delayInMilliSecond: Long) {
        Handler(Looper.getMainLooper())
            .postDelayed({
                fn.invoke()
            }, delayInMilliSecond)
    }

    fun getRandomNumber(): Int {
        return (1..100).random() // Generates a random integer between 1 and 100 (inclusive)
    }

    // Extension function for Int to convert to Dp
    fun Int.toDp(): Float = this / Resources.getSystem().displayMetrics.density

    // Extension function for Dp to convert to Px
    fun Int.toPx(): Float = this * Resources.getSystem().displayMetrics.density

    // Improvement of extension function for Dp to convert to Px
    fun Int.toPx(context: Context): Float = this * context.resources.displayMetrics.density


    // Extension function for Int to convert to Dp
    fun Float.toDp(): Float = this / Resources.getSystem().displayMetrics.density

    // Extension function for Dp to convert to Px
    fun Float.toPx(): Float = this * Resources.getSystem().displayMetrics.density

    fun Number.formatMoney(): String {
        val formattedValue = when (this) {
            is Double -> String.format("%.2f", this)
            is Int -> toString()
            else -> throw IllegalArgumentException("Unsupported type for formatting: ${this::class.simpleName}")
        }

        val isArabic = LanguageConfiguration.getAppLanguage() == AppLanguage.ARABIC.suffix
        val (wholeNumber, decimalPart) = formattedValue.split(
            if (isArabic) "٫" else "."
        )
        val formattedWholeNumber = wholeNumber.reversed().chunked(3)
            .joinTo(StringBuilder(), separator = ",", transform = { it.reversed() })
        val decimalString =
            if (decimalPart == if (isArabic) "٠٠" else "00") "" else ".${decimalPart.trim(if (isArabic) '٠' else '0')}"
        return "$formattedWholeNumber$decimalString"
    }

    fun Long.formatTimestamp(pattern: String = "d/M/yyyy h:mm a"): String {
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return simpleDateFormat.format(Date(this))
    }

    fun Any?.logCat(title: String = "LogCat title") {
        Log.wtf(title, if (this is String) this else this.toString())
    }

    fun Activity.logout(){
        SharedPreferencesModule.logout()
    }
}