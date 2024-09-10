package com.bb4first.mycashtask.ui.auth

import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.bb4first.mycashtask.R
import com.bb4first.mycashtask.base.BaseFragment
import com.bb4first.mycashtask.base.StartInflation
import com.bb4first.mycashtask.databinding.FragmentLoginBinding
import com.bb4first.mycashtask.enums.ErrorViewType
import com.bb4first.mycashtask.utlis.Utils
import com.bb4first.mycashtask.viewmodel.auth.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
    override val inflate: StartInflation<FragmentLoginBinding>
        get() = FragmentLoginBinding::inflate

    private val loginViewModel: LoginViewModel by viewModels()

    override fun getInjectViewModel(): LoginViewModel = loginViewModel

    override fun initialization() {
    }

    override fun listeners() {
        binding.apply {
            txtGoToSignUp.setOnClickListener {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_loginFragment_to_signUpFragment)
            }

            txtForgotPassword.setOnClickListener {
                Utils.notImplemented()
            }
        }
    }

    override fun initializeViewModel() {
    }

    override fun showErrorView(networkError: ErrorViewType, resourceId: Int) {
    }
}