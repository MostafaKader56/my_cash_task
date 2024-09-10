package com.bb4first.mycashtask.ui.home

import android.content.Intent
import androidx.fragment.app.viewModels
import com.bb4first.mycashtask.base.BaseFragment
import com.bb4first.mycashtask.base.StartInflation
import com.bb4first.mycashtask.databinding.FragmentMainBinding
import com.bb4first.mycashtask.enums.ErrorViewType
import com.bb4first.mycashtask.utlis.LanguageConfiguration
import com.bb4first.mycashtask.utlis.LanguageSelectionDialog
import com.bb4first.mycashtask.viewmodel.home.MainViewModel

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {
    override val inflate: StartInflation<FragmentMainBinding>
        get() = FragmentMainBinding::inflate

    private val mainViewModel: MainViewModel by viewModels()

    override fun getInjectViewModel(): MainViewModel = mainViewModel

    override fun initialization() {
    }

    override fun listeners() {

        val dialog = LanguageSelectionDialog(
            requireContext()
        ) {
            LanguageConfiguration.changeLanguage(it.suffix, requireContext())
            val intent = Intent(requireActivity(), HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }
        binding.toolbar.setOnClickListener {
            dialog.show()
        }
    }

    override fun initializeViewModel() {
    }

    override fun showErrorView(networkError: ErrorViewType, resourceId: Int) {
    }

}