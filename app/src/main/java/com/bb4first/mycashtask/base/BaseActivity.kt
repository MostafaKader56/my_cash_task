package com.bb4first.mycashtask.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding
import com.bb4first.mycashtask.R
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
        enableEdgeToEdge()

        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initialization()
        setListeners()
    }

    abstract fun initialization()

    abstract fun setListeners()
}