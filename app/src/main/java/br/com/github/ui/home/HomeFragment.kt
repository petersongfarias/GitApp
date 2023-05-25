package br.com.github.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import br.com.github.NavGraphDirections
import br.com.github.databinding.FragmentHomeBinding
import br.com.github.domain.model.user.BaseUser
import br.com.github.ui.common.customView.LoadingStateAdapter
import br.com.github.ui.common.extensions.changeVisibility
import br.com.github.ui.common.extensions.getErrorState
import br.com.github.ui.common.extensions.hideKeyBoard
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    private val listUserAdapter: ListUserAdapter by lazy { ListUserAdapter(::userItemClicked) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStop() {
        super.onStop()
        activity?.hideKeyBoard()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupObservers()
        setupSearch()
        viewModel.fetchUsers()
    }

    private fun setupObservers() = with(viewModel) {
        userDetailFailureEvent.observe(viewLifecycleOwner) {
            showError(errorMessage = it)
        }

        userListFailureEvent.observe(viewLifecycleOwner) {
            showError(errorMessage = it)
        }

        userDetailSuccessEvent.observe(viewLifecycleOwner) {
            setUserList(PagingData.from(listOf(it)))
        }

        userListSuccessEvent.observe(viewLifecycleOwner) {
            setUserList(it as PagingData<BaseUser>)
        }

        isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setUserList(pagingData: PagingData<BaseUser>) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    listUserAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun setupSearch() {
        binding.svUser.apply {
            searchStartedListener = {
                setUserList(PagingData.empty())
                viewModel.fetchUser(it)
            }

            continuousInputChangedListener = {
                if (it.isEmpty()) {
                    viewModel.fetchUsers()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            shimmerHome.shimmer.changeVisibility(isLoading)
            rvUsers.changeVisibility(!isLoading)
            vErrorView.hide()
        }
    }

    private fun showError(
        errorTitle: String? = null,
        errorMessage: String? = null
    ) {
        binding.apply {
            shimmerHome.shimmer.changeVisibility(false)
            rvUsers.changeVisibility(false)
            vErrorView.show(errorTitle, errorMessage)
            vErrorView.setRetryClickListener {
                svUser.clear()
            }
        }
    }

    private fun setupAdapter() {
        with(binding.rvUsers) {
            setHasFixedSize(true)
            listUserAdapter.addLoadStateListener {
                it.getErrorState()?.let { errorState ->
                    viewModel.updateFetchUsersFailure(errorState)
                }

                viewModel.updateLoadingState(it.source.refresh is LoadState.Loading)
            }
            adapter = listUserAdapter.withLoadStateHeaderAndFooter(
                LoadingStateAdapter(),
                LoadingStateAdapter()
            )
        }
    }

    private fun userItemClicked(user: BaseUser) {
        findNavController().navigate(NavGraphDirections.actionGlobalToUserDetailFragment(user))
    }
}
