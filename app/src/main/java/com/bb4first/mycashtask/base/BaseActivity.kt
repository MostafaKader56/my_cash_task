package com.bb4first.mycashtask.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.bb4first.mycashtask.utlis.LanguageConfiguration

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    abstract val bindingFactory: (LayoutInflater) -> VB
    lateinit var binding: VB

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(
            LanguageConfiguration.changeLanguage(
                LanguageConfiguration.getAppLanguage(),
                newBase
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)

        initialization()
        setListeners()
    }

    abstract fun initialization()

    abstract fun setListeners()
}