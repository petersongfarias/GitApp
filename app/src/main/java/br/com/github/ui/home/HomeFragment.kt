package br.com.github.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import br.com.github.databinding.FragmentHomeBinding
import br.com.github.domain.model.user.BaseUser
import br.com.github.ui.common.changeVisibility
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var userQuery: String

    private lateinit var homeBinding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()
    private val listUserAdapter: ListUserAdapter by lazy { ListUserAdapter(::userItemClicked) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        setupSearch()
        setupAdapter()
        homeViewModel.fetchUsers()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    homeViewModel.loadingStateFlow.collect {
                        showLoading(it)
                    }
                }

                launch {
                    homeViewModel.errorSharedFlow.collect {
                        showError(errorMessage = it)
                        listUserAdapter.submitData(PagingData.empty())
                    }
                }

                launch {
                    homeViewModel.homeStateFlow.collectLatest {
                        when (it) {
                            is HomeState.Nothing -> {}

                            is HomeState.Clear -> {
                                listUserAdapter.submitData(PagingData.empty())
                            }

                            is HomeState.ShowUserList -> {
                                showLoading(false)
                                (it.userList as? PagingData<BaseUser>)?.let { data ->
                                    listUserAdapter.submitData(data)
                                }
                            }

                            is HomeState.ShowUser -> {
                                listUserAdapter.submitData(PagingData.from(listOf(it.user)))
                            }
                        }
                    }
                }

                launch {
//                    homeViewModel.eventSharedFlow.collectLatest {
//                        when (it) {
//                            is EventState.MoveDetailActivity -> {
//                                startDetailActivity(it.user)
//                            }
//                        }
//                    }
                }
            }
        }
    }

    private fun setupSearch() {
        homeBinding.svUser.apply {
            setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        userQuery = query.toString()
                        clearFocus()
                        homeViewModel.fetchUser(userQuery)
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return true
                    }
                })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        homeBinding.apply {
            shimmerHome.changeVisibility(isLoading)
            rvUsers.changeVisibility(!isLoading)
            vErrorView.hide()
        }
    }

    private fun showError(
        errorTitle: String? = null,
        errorMessage: String? = null
    ) {
        homeBinding.apply {
            shimmerHome.changeVisibility(false)
            rvUsers.changeVisibility(false)
            vErrorView.show(errorTitle, errorMessage)
        }
    }

    private fun setupAdapter() {
        with(homeBinding.rvUsers) {
            setHasFixedSize(true)
            adapter = listUserAdapter
        }
    }

    private fun userItemClicked(user: BaseUser) {
//        val intent = Intent(this, DetailUserActivity::class.java)
//        intent.putExtra(DetailUserActivity.EXTRA_USER, user)
//        startActivity(intent)
    }
}
