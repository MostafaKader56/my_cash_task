package com.bb4first.mycashtask.ui.auth

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.bb4first.mycashtask.R
import com.bb4first.mycashtask.base.BaseFragment
import com.bb4first.mycashtask.base.StartInflation
import com.bb4first.mycashtask.databinding.FragmentLoginBinding
import com.bb4first.mycashtask.enums.ErrorViewType
import com.bb4first.mycashtask.nerwork.YjahzResource
import com.bb4first.mycashtask.ui.home.HomeActivity
import com.bb4first.mycashtask.utlis.Utils
import com.bb4first.mycashtask.utlis.Utils.isEmailValid
import com.bb4first.mycashtask.utlis.Utils.toast
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

            btnLogin.setOnClickListener {
                onLoginClicked()
            }
        }
    }

    private fun onLoginClicked() {
        val email = binding.etEmail.text
        val password = binding.etPassword.text
        var isThereError = false

        if (email.isBlank()) {
            binding.etEmail.errorMessage = getString(R.string.enter_email)
            isThereError = true
        } else if (!email.isEmailValid()) {
            binding.etEmail.errorMessage = getString(R.string.email_not_valid)
            isThereError = true
        }

        if (password.isEmpty()) {
            binding.etPassword.errorMessage = getString(R.string.enter_password)
            isThereError = true
        } else if (password.length < 8) {
            binding.etPassword.errorMessage =
                getString(R.string.password_should_be_8_characters_at_least)
            isThereError = true
        }

        if (isThereError)
            return
        loginViewModel.login(email = email, password = password, deviceToken = "")
    }

    override fun initializeViewModel() {
        getLoginResponseObserver()
    }

    private fun getLoginResponseObserver() {
        loginViewModel.loginUserResponseLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is YjahzResource.Failure -> {
                    (it.error ?: getString(R.string.something_went_wrong_message)).toast()
                }

                YjahzResource.Loading -> {

                }

                is YjahzResource.Success -> {
                    val intent = Intent(requireActivity(), HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }
    }

    override fun showErrorView(networkError: ErrorViewType, resourceId: Int) {
    }
}