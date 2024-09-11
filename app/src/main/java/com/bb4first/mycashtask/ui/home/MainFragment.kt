package com.bb4first.mycashtask.ui.home

import android.content.Intent
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import com.bb4first.mycashtask.R
import com.bb4first.mycashtask.base.BaseFragment
import com.bb4first.mycashtask.base.StartInflation
import com.bb4first.mycashtask.databinding.FragmentMainBinding
import com.bb4first.mycashtask.enums.ErrorViewType
import com.bb4first.mycashtask.ui.auth.AuthActivity
import com.bb4first.mycashtask.utlis.LanguageConfiguration
import com.bb4first.mycashtask.utlis.LanguageSelectionDialog
import com.bb4first.mycashtask.utlis.SharedPreferencesModule
import com.bb4first.mycashtask.utlis.Utils
import com.bb4first.mycashtask.viewmodel.home.MainViewModel

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {
    override val inflate: StartInflation<FragmentMainBinding>
        get() = FragmentMainBinding::inflate

    private val mainViewModel: MainViewModel by viewModels()

    override fun getInjectViewModel(): MainViewModel = mainViewModel

    override fun initialization() {

    }

    override fun listeners() {
        binding.apply {
            txtHelloSection.text = getString(
                R.string.main_fragment_hello,
                SharedPreferencesModule.getUserValue()?.name
            )

            val addresses = SharedPreferencesModule.getUserValue()?.addresses
            txtAddress.text =
                if (addresses?.isNotEmpty() == true)
                    addresses[0].address.toString()
                else
                    getString(R.string.no_address_found)

            btnMenu.setOnClickListener {
                showPopupMenu(btnMenu)
            }

            btnBack.setOnClickListener {
                Utils.notImplemented()
            }

            btnCart.setOnClickListener {
                Utils.notImplemented()
            }

            btnAddressForward.setOnClickListener {
                Utils.notImplemented()
            }

            addressSection.setOnClickListener {
                Utils.notImplemented()
            }

            btnSearch.setOnClickListener {
                Utils.notImplemented()
            }

            btnFilter.setOnClickListener {
                Utils.notImplemented()
            }
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        val inflater: MenuInflater = popupMenu.menuInflater
        inflater.inflate(R.menu.main_fragment_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.language -> {
                    LanguageSelectionDialog(
                        requireContext()
                    ) {
                        LanguageConfiguration.changeLanguage(it.suffix, requireContext())
                        val intent = Intent(requireActivity(), HomeActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        requireActivity().finish()
                    }.show()
                    true
                }

                R.id.logout -> {
                    Utils.showAlertDialog(
                        context = requireContext(),
                        dismissible = true,
                        title = getString(R.string.logout),
                        message = getString(R.string.are_you_want_to_confirm_logout),
                        positiveButton = getString(R.string.confirm),
                        negativeButton = getString(R.string.cancel),
                        positiveAction = {
                            SharedPreferencesModule.logout()
                            val intent = Intent(requireActivity(), AuthActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            requireActivity().finish()
                        },
                    )
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }

    override fun initializeViewModel() {
    }

    override fun showErrorView(networkError: ErrorViewType, resourceId: Int) {
    }

}