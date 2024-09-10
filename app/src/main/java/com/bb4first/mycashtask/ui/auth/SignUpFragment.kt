package com.bb4first.mycashtask.ui.auth

import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.bb4first.mycashtask.R
import com.bb4first.mycashtask.base.BaseFragment
import com.bb4first.mycashtask.base.StartInflation
import com.bb4first.mycashtask.databinding.FragmentSignUpBinding
import com.bb4first.mycashtask.enums.ErrorViewType
import com.bb4first.mycashtask.nerwork.YjahzResource
import com.bb4first.mycashtask.utlis.Utils.isEmailValid
import com.bb4first.mycashtask.utlis.Utils.isPhoneNumberValid
import com.bb4first.mycashtask.utlis.Utils.isPhoneNumberValidWithoutPlus
import com.bb4first.mycashtask.utlis.Utils.logCat
import com.bb4first.mycashtask.utlis.Utils.toast
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
            txtLogin.setOnClickListener {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_signUpFragment_to_loginFragment)
            }

            btnSignUp.setOnClickListener {
                onSignUpClicked()
            }
        }
    }

    private fun onSignUpClicked() {
        val name = binding.etName.text
        val email = binding.etEmail.text
        val phone = binding.etPhone.text
        val password = binding.etPassword.text
        val confirmPassword = binding.etConfirmPassword.text
        var isThereError = false

        if (name.isBlank()) {
            binding.etName.errorMessage = getString(R.string.enter_name)
            isThereError = true
        } else if (name.length < 2) {
            binding.etName.errorMessage = getString(R.string.very_short_name)
            isThereError = true
        }

        if (email.isBlank()) {
            binding.etEmail.errorMessage = getString(R.string.enter_email)
            isThereError = true
        } else if (!email.isEmailValid()) {
            binding.etEmail.errorMessage = getString(R.string.email_not_valid)
            isThereError = true
        }

        if (phone.isBlank()) {
            binding.etPhone.errorMessage = getString(R.string.enter_phone_number)
            isThereError = true
        } else if (phone.contains('+') && !phone.isPhoneNumberValid()) {
            binding.etPhone.errorMessage = getString(R.string.enter_a_valid_phone)
            isThereError = true
        }
        else if (!phone.contains('+') && !phone.isPhoneNumberValidWithoutPlus()){
            binding.etPhone.errorMessage = getString(R.string.enter_a_valid_phone)
            isThereError = true
        }

        if (password.isEmpty()) {
            binding.etPassword.errorMessage = getString(R.string.enter_password)
            isThereError = true
        } else if (password.length < 8) {
            binding.etPassword.errorMessage =
                getString(R.string.password_should_be_8_characters_at_least)
            isThereError = true
        } else if (password != confirmPassword) {
            binding.etConfirmPassword.errorMessage = getString(R.string.passwords_not_matches)
            isThereError = true
        }

        if (isThereError)
            return
        signUpViewModel.signUp(
            name = name, email = email, password = password, phone = phone, deviceToken = "",
        )
    }

    override fun initializeViewModel() {
        getUserResponseObserver()
    }

    private fun getUserResponseObserver() {
        signUpViewModel.registerUserResponseLiveData.observe(viewLifecycleOwner){
            when(it){
                is YjahzResource.Failure -> {
                }
                YjahzResource.Loading -> {

                }
                is YjahzResource.Success -> {
                    "Success: ${it.data?.phone}".toast()
                    "Success: ${it.data?.phone}".logCat()
                }
            }
        }
    }

    override fun showErrorView(networkError: ErrorViewType, resourceId: Int) {
    }
}