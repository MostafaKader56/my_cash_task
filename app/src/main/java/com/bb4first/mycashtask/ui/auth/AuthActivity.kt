package com.bb4first.mycashtask.ui.auth

import android.view.LayoutInflater
import com.bb4first.mycashtask.base.BaseActivity
import com.bb4first.mycashtask.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>() {
    override val bindingFactory: (LayoutInflater) -> ActivityAuthBinding
        get() = ActivityAuthBinding::inflate

    override fun initialization() {}

    override fun setListeners() {}
}