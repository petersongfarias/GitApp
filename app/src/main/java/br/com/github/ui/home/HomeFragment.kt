package br.com.github.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import br.com.github.NavGraphDirections
import br.com.github.databinding.FragmentHomeBinding
import br.com.github.domain.model.user.BaseUser
import br.com.github.ui.common.changeVisibility
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
            setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        setUserList(PagingData.empty())
                        viewModel.fetchUser(query.toString())
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        val userQuery = query.toString()
                        if (userQuery.isEmpty()) {
                            clearFocus()
                            viewModel.fetchUsers()
                        }
                        return true
                    }
                })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            shimmerHome.changeVisibility(isLoading)
            rvUsers.changeVisibility(!isLoading)
            vErrorView.hide()
        }
    }

    private fun showError(
        errorTitle: String? = null,
        errorMessage: String? = null
    ) {
        binding.apply {
            shimmerHome.changeVisibility(false)
            rvUsers.changeVisibility(false)
            vErrorView.show(errorTitle, errorMessage)
            vErrorView.setRetryClickListener {
                svUser.setQuery("", true)
            }
        }
    }

    private fun setupAdapter() {
        with(binding.rvUsers) {
            setHasFixedSize(true)
            listUserAdapter.addLoadStateListener {
                viewModel.observeLoadState(it)
            }
            adapter = listUserAdapter
        }
    }

    private fun userItemClicked(user: BaseUser) {
        findNavController().navigate(NavGraphDirections.actionGlobalToUserDetailFragment(user))
    }
}
