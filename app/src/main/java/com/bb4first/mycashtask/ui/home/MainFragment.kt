package com.bb4first.mycashtask.ui.home

import android.content.Intent
import android.location.Location
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bb4first.mycashtask.R
import com.bb4first.mycashtask.base.BaseFragment
import com.bb4first.mycashtask.base.StartInflation
import com.bb4first.mycashtask.databinding.FragmentMainBinding
import com.bb4first.mycashtask.enums.ErrorViewType
import com.bb4first.mycashtask.nerwork.YjahzResource
import com.bb4first.mycashtask.ui.auth.AuthActivity
import com.bb4first.mycashtask.utlis.LanguageConfiguration
import com.bb4first.mycashtask.utlis.LanguageSelectionDialog
import com.bb4first.mycashtask.utlis.SharedPreferencesModule
import com.bb4first.mycashtask.utlis.Utils
import com.bb4first.mycashtask.utlis.Utils.toast
import com.bb4first.mycashtask.viewmodel.home.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {
    override val inflate: StartInflation<FragmentMainBinding>
        get() = FragmentMainBinding::inflate

    private val mainViewModel: MainViewModel by viewModels()

    override fun getInjectViewModel(): MainViewModel = mainViewModel


    private val homeCategoriesAdapter by lazy { HomeCategoriesAdapter(LanguageConfiguration.getAppLanguageEnumObject()) }
    private val homePopularAdapter by lazy { HomePopularAdapter() }
    private val homeTrendingAdapter by lazy { HomeTrendingAdapter() }

    private fun getMainFragmentFullStatus() {
        mainViewModel.setLoading(true)
        (requireActivity() as HomeActivity).startGetLocation(
            object : HomeActivity.MyCashTaskLocationCallBack {
                override fun onLocationReceived(location: Location) {
                    mainViewModel.setLoading(false)
                    mainViewModel.getBaseHomeCategories()
                    mainViewModel.getTrendingSellers(
                        lat = location.latitude,
                        long = location.longitude
                    )
                    mainViewModel.getPopularSellers(
                        lat = location.latitude,
                        long = location.longitude
                    )
                }

                override fun onFail(error: String) {
                    mainViewModel.setLoading(false)
                    error.toast()
                    mainViewModel.getBaseHomeCategories()
                    mainViewModel.getTrendingSellers()
                    mainViewModel.getPopularSellers()
                }
            },
        )
    }

    override fun initialization() {
        getMainFragmentFullStatus()

        initializeHeaders()
        initializationCategoriesRecycler()
        initializationTrendingRecycler()
        initializationPopularRecycler()
        initializeErrorsPlaceholdersBtns()
    }

    private fun initializeErrorsPlaceholdersBtns() {
        binding.errorRecyclerCategories.setOnRetryClickListener {
            mainViewModel.getBaseHomeCategories()
        }
    }

    private fun initializeHeaders() {
        binding.headerCategories.setOnClickListener {
            Utils.notImplemented()
        }

        binding.headerPopular.setOnClickListener {
            Utils.notImplemented()
        }

        binding.headerTrending.setOnClickListener {
            Utils.notImplemented()
        }
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

    private fun initializationPopularRecycler() {
        binding.recyclerPopular.apply {
            adapter = homePopularAdapter
            layoutManager = LinearLayoutManager(
                requireContext(), RecyclerView.HORIZONTAL, false
            )
        }
    }

    private fun initializationTrendingRecycler() {
        binding.recyclerTrending.apply {
            adapter = homeTrendingAdapter
            layoutManager = LinearLayoutManager(
                requireContext(), RecyclerView.HORIZONTAL, false
            )
        }
    }

    private fun initializationCategoriesRecycler() {
        binding.recyclerCategories.apply {
            adapter = homeCategoriesAdapter
            layoutManager = LinearLayoutManager(
                requireContext(), RecyclerView.HORIZONTAL, false
            )
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
                    homeCategoriesAdapter.itemCount.toString().toast()
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }

    override fun initializeViewModel() {
        getHomeBaseCategoriesObserver()
        getTrendingSellersObserver()
        getPopularSellersObserver()
    }

    private fun getPopularSellersObserver() {
        mainViewModel.getPopularSellersResponseLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is YjahzResource.Failure -> {
                    binding.loaderRecyclerPopular.stopLoading()
                    binding.recyclerPopular.visibility = View.INVISIBLE
                    binding.errorRecyclerPopular.visibility = View.VISIBLE
                    binding.loaderRecyclerPopular.visibility = View.GONE

                    binding.errorRecyclerPopular.setError(
                        it.error ?: getString(R.string.something_went_wrong_message)
                    )
                }

                YjahzResource.Loading -> {
                    binding.recyclerPopular.visibility = View.INVISIBLE
                    binding.errorRecyclerPopular.visibility = View.GONE
                    binding.loaderRecyclerPopular.visibility = View.VISIBLE

                    binding.loaderRecyclerPopular.startLoading()
                }

                is YjahzResource.Success -> {
                    binding.loaderRecyclerPopular.visibility = View.INVISIBLE
                    binding.errorRecyclerPopular.visibility = View.GONE

                    binding.recyclerPopular.visibility = View.VISIBLE
                    homePopularAdapter.submitItems(it.data?.data ?: listOf())
                }
            }
        }
    }

    private fun getTrendingSellersObserver() {
        mainViewModel.getTrendingSellersResponseLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is YjahzResource.Failure -> {
                    binding.loaderRecyclerTrending.stopLoading()
                    binding.recyclerTrending.visibility = View.INVISIBLE
                    binding.errorRecyclerTrending.visibility = View.VISIBLE
                    binding.loaderRecyclerTrending.visibility = View.GONE

                    binding.errorRecyclerTrending.setError(
                        it.error ?: getString(R.string.something_went_wrong_message)
                    )
                }

                YjahzResource.Loading -> {
                    binding.recyclerTrending.visibility = View.INVISIBLE
                    binding.errorRecyclerTrending.visibility = View.GONE
                    binding.loaderRecyclerTrending.visibility = View.VISIBLE

                    binding.loaderRecyclerTrending.startLoading()
                }

                is YjahzResource.Success -> {
                    binding.loaderRecyclerTrending.visibility = View.INVISIBLE
                    binding.errorRecyclerTrending.visibility = View.GONE

                    binding.recyclerTrending.visibility = View.VISIBLE
                    homeTrendingAdapter.submitItems(it.data?.data ?: listOf())
                }
            }
        }
    }

    private fun getHomeBaseCategoriesObserver() {
        mainViewModel.getHomeCategoriesResponseLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is YjahzResource.Failure -> {
                    binding.loaderRecyclerCategories.stopLoading()
                    binding.recyclerCategories.visibility = View.INVISIBLE
                    binding.errorRecyclerCategories.visibility = View.VISIBLE
                    binding.loaderRecyclerCategories.visibility = View.GONE

                    binding.errorRecyclerCategories.setError(
                        it.error ?: getString(R.string.something_went_wrong_message)
                    )
                }

                YjahzResource.Loading -> {
                    binding.recyclerCategories.visibility = View.INVISIBLE
                    binding.errorRecyclerCategories.visibility = View.GONE
                    binding.loaderRecyclerCategories.visibility = View.VISIBLE

                    binding.loaderRecyclerCategories.startLoading()
                }

                is YjahzResource.Success -> {
                    binding.loaderRecyclerCategories.visibility = View.INVISIBLE
                    binding.errorRecyclerCategories.visibility = View.GONE

                    binding.recyclerCategories.visibility = View.VISIBLE
                    homeCategoriesAdapter.submitItems(it.data?.data ?: listOf())
                }
            }
        }
    }

    override fun showErrorView(networkError: ErrorViewType, resourceId: Int) {
    }

}