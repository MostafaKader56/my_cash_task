package com.bb4first.mycashtask.nerwork

import com.bb4first.mycashtask.MyCashTaskApplication
import com.bb4first.mycashtask.utlis.Constants
import com.bb4first.mycashtask.utlis.LanguageConfiguration
import com.bb4first.mycashtask.utlis.SharedPreferencesModule
import com.bb4first.mycashtask.utlis.SharedPreferencesModule.PREF_APP_TOKEN
import com.bb4first.mycashtask.utlis.Utils
import com.bb4first.mycashtask.utlis.Utils.getAppCodeVersion
import com.bb4first.mycashtask.utlis.Utils.getDeviceName
import com.bb4first.mycashtask.utlis.Utils.getOurDeviceId

object RequestHeaders {
    fun getHeaders() : Map<String, String> {
        val map = HashMap<String, String>()
        val appLanguage = LanguageConfiguration.getAppLanguage()
        val appVersion = MyCashTaskApplication.instance.getAppCodeVersion()
        val deviceName = MyCashTaskApplication.instance.getDeviceName()
        val deviceId = MyCashTaskApplication.instance.getOurDeviceId()
        val deviceAndroidVersion = Utils.currentVersion()
        val token = SharedPreferencesModule.getStringValue(PREF_APP_TOKEN, "")
        // This customization as the backend request
        map[Constants.ACCEPT] = "application/json"
        map[Constants.AUTHORIZATION] = "Bearer $token"
        map[Constants.ACCEPT_LANGUAGE] = appLanguage
        map[Constants.APP_VERSION] = appVersion
        map[Constants.DEVICE_NAME] = deviceName
        map[Constants.DEVICE_ID] = deviceId
        map[Constants.MOBILE_VERSION] = deviceAndroidVersion
        map[Constants.PLATFORM] = "Android"
        return map
    }
}






