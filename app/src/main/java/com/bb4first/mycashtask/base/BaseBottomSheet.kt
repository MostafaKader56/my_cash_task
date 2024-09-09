package com.bb4first.mycashtask.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.bb4first.mycashtask.R
import com.bb4first.mycashtask.utlis.CustomLoading
import com.bb4first.mycashtask.utlis.Utils
import com.bb4first.mycashtask.utlis.Utils.logout
import com.bb4first.mycashtask.utlis.Utils.showAlert
import com.bb4first.mycashtask.base.BaseViewModel
import com.bb4first.mycashtask.base.StartInflation
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheet<T : ViewBinding, MV : BaseViewModel>() : BottomSheetDialogFragment() {

    protected abstract val inflate: StartInflation<T>
    private var _binding: T? = null
    protected val binding get() = _binding!!

    protected abstract fun initialization()
    protected abstract fun listeners()

    private val viewModel: MV by lazy {
        getInjectViewModel()
    }

    abstract fun getInjectViewModel(): MV

    private val customLoading: CustomLoading by lazy {
        CustomLoading(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialization()
        listeners()

        viewModel.startLoadingLiveData.observe(viewLifecycleOwner) {
            if (it) {
                customLoading.show()
            } else {
                customLoading.dismiss()
            }
            trackLoadingStatus(it)
        }

        viewModel.forceLoginLiveData.observe(viewLifecycleOwner) {
            requireActivity().logout()
        }

        viewModel.forceUpdateAppLiveData.observe(viewLifecycleOwner) {
            requireActivity().showAlert(
                title = getString(R.string.update_application),
                message = it?: getString(R.string.message_update_application),
                canCancel = false
            ) {
                Utils.openAppOnGooglePlay(requireActivity())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    open fun trackLoadingStatus(status: Boolean) {}

    fun getErrorMessage(message: String?): String {
        return if (message.isNullOrEmpty()) {
            getString(R.string.something_went_wrong_message)
        } else {
            message
        }
    }
}