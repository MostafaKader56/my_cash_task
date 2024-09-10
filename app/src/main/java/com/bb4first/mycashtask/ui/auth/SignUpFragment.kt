package com.bb4first.mycashtask.ui.auth

import androidx.fragment.app.viewModels
import com.bb4first.mycashtask.base.BaseFragment
import com.bb4first.mycashtask.base.StartInflation
import com.bb4first.mycashtask.databinding.FragmentSignUpBinding
import com.bb4first.mycashtask.enums.ErrorViewType
import com.bb4first.mycashtask.viewmodel.auth.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding, SignUpViewModel>() {
    override val inflate: StartInflation<FragmentSignUpBinding>
        get() = FragmentSignUpBinding::inflate

    private val signUpViewModel: SignUpViewModel by viewModels()
    override fun getInjectViewModel(): SignUpViewModel = signUpViewModel

    override fun initialization() {
        TODO("Not yet implemented")
    }

    override fun listeners() {
        TODO("Not yet implemented")
    }

    override fun initializeViewModel() {
        TODO("Not yet implemented")
    }

    override fun showErrorView(networkError: ErrorViewType, resourceId: Int) {
        TODO("Not yet implemented")
    }
}