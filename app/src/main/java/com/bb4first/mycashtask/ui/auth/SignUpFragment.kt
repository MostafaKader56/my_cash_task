package com.bb4first.mycashtask.ui.auth

import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.bb4first.mycashtask.R
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
    }

    override fun listeners() {
        binding.apply {
            login.setOnClickListener {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_signUpFragment_to_loginFragment)
            }
        }
    }

    override fun initializeViewModel() {
    }

    override fun showErrorView(networkError: ErrorViewType, resourceId: Int) {
    }
}