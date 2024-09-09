package com.bb4first.mycashtask.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bb4first.mycashtask.ui.auth.AuthActivity
import com.bb4first.mycashtask.ui.home.HomeActivity
import com.bb4first.mycashtask.utlis.SharedPreferencesModule
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoutingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        // Keep the splash screen visible for this Activity.
        splashScreen.setKeepOnScreenCondition { true }

        openNextActivity()
    }

    private fun openNextActivity() {
        if (SharedPreferencesModule.isLoggedIn()){
            startActivity(Intent(this@RoutingActivity, HomeActivity::class.java))
        }
        else{
            startActivity(Intent(this@RoutingActivity, AuthActivity::class.java))
        }
        finish()
    }
}