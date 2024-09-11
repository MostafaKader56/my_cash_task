package com.bb4first.mycashtask.ui.home

import android.view.LayoutInflater
import com.bb4first.mycashtask.base.BaseActivity
import com.bb4first.mycashtask.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override val bindingFactory: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate

    override val enableEdgeToEdgeToThisActivity: Boolean = false

    override fun initialization() {
    }

    override fun setListeners() {
    }

}