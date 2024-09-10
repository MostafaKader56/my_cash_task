package com.bb4first.mycashtask.ui.home

import androidx.fragment.app.viewModels
import com.bb4first.mycashtask.base.BaseFragment
import com.bb4first.mycashtask.base.StartInflation
import com.bb4first.mycashtask.databinding.FragmentMainBinding
import com.bb4first.mycashtask.enums.ErrorViewType
import com.bb4first.mycashtask.viewmodel.home.MainViewModel

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {
    override val inflate: StartInflation<FragmentMainBinding>
        get() = FragmentMainBinding::inflate

    private val mainViewModel: MainViewModel by viewModels()

    override fun getInjectViewModel(): MainViewModel = mainViewModel

    override fun initialization() {
    }

    override fun listeners() {
    }

    override fun initializeViewModel() {
    }

    override fun showErrorView(networkError: ErrorViewType, resourceId: Int) {
    }

}