package com.bb4first.mycashtask.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import com.bb4first.mycashtask.R
import com.bb4first.mycashtask.enums.ErrorViewType
import com.bb4first.mycashtask.utlis.CustomLoading
import com.bb4first.mycashtask.utlis.Utils
import com.bb4first.mycashtask.utlis.Utils.logout
import com.bb4first.mycashtask.utlis.Utils.showAlert


typealias StartInflation<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding, MV : BaseViewModel> : Fragment() {

    protected abstract val inflate: StartInflation<VB>
    private var _binding: VB? = null
    val binding get() = _binding!!

    private val viewModel: MV by lazy {
        getInjectViewModel()
    }

    abstract fun getInjectViewModel(): MV

    protected var viewHasNotificationIcon = false

    var viewGroup: ViewGroup? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewGroup = container
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    abstract fun initialization()

    abstract fun listeners()

    abstract fun initializeViewModel()

    private val customLoading: CustomLoading by lazy {
        CustomLoading(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewModel()
        getNetWorkConnectionError()
        initialization()
        listeners()

        observeBaseViewModelObservers()
    }

    private fun observeBaseViewModelObservers() {
        observerLoadingStatue()
        observerForceLogin()
        observerForceUpdateApp()
    }

    private fun observerForceUpdateApp() {
        viewModel.forceUpdateAppLiveData.observe(viewLifecycleOwner) {
            requireActivity().showAlert(
                title = getString(R.string.update_application),
                message = it ?: getString(R.string.message_update_application),
                canCancel = false
            ) {
                Utils.openAppOnGooglePlay(requireActivity())
            }
        }
    }

    private fun observerForceLogin() {
        viewModel.forceLoginLiveData.observe(viewLifecycleOwner) {
            requireActivity().logout()
        }
    }

    private fun observerLoadingStatue() {
        viewModel.startLoadingLiveData.observe(viewLifecycleOwner) {
            if (it) {
                customLoading.show()
            } else {
                customLoading.dismiss()
            }
            trackLoadingStatus(it)
        }
    }

    open fun trackLoadingStatus(status: Boolean) {}

    open fun getNetWorkConnectionError() {}

    abstract fun showErrorView(networkError: ErrorViewType, resourceId: Int)

    fun getErrorMessage(message: String?): String {
        return if (message.isNullOrEmpty()) {
            getString(R.string.something_went_wrong_message)
        } else {
            message
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}