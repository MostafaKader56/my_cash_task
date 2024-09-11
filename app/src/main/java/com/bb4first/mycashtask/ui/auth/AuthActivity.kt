package com.bb4first.mycashtask.ui.auth

import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import com.bb4first.mycashtask.base.BaseActivity
import com.bb4first.mycashtask.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity() : BaseActivity<ActivityAuthBinding>() {
    override val bindingFactory: (LayoutInflater) -> ActivityAuthBinding
        get() = ActivityAuthBinding::inflate

    override val enableEdgeToEdgeToThisActivity: Boolean = true

    override fun initialization() {

    }

    override fun setListeners() {
        handleOnBackPressed()
    }

    private fun handleOnBackPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val navController = Navigation.findNavController(binding.activityAuth)
                if (!navController.popBackStack()) {
                    // If no fragments in the back stack, finish the activity
                    finish()
                }
            }
        })
    }
}