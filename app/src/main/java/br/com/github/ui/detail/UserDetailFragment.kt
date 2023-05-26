package br.com.github.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.github.R
import br.com.github.databinding.FragmentUserDetailBinding
import br.com.github.domain.model.user.UserDetailModel
import br.com.github.ui.common.customView.hide
import br.com.github.ui.common.customView.show
import br.com.github.ui.common.extensions.changeVisibility
import br.com.github.ui.common.extensions.loadImage
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class UserDetailFragment : Fragment() {

    private val listRepositoryAdapter: ListRepositoryAdapter by lazy { ListRepositoryAdapter() }
    private val args: UserDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentUserDetailBinding
    private val toolbar by lazy { binding.toolbar }
    private val viewModel: UserDetailViewModel by viewModel {
        parametersOf(args.user)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupToolBar()
        setupAdapter()
    }

    private fun setupObservers() = with(viewModel) {
        user.observe(viewLifecycleOwner) {
            fetchUser(it.login)
        }

        isRepositoriesLoading.observe(viewLifecycleOwner) {
            binding.vUserDetailStateView.hide()
            binding.pbLoading.changeVisibility(it)
        }

        userRepositoriesEmptyEvent.observe(viewLifecycleOwner) {
            setRepositoriesStateView(
                title = getString(R.string.default_error_title),
                message = getString(R.string.default_empty_message),
                animationName = getString(R.string.animation_empty)
            )
        }

        userRepositoriesFailureEvent.observe(viewLifecycleOwner) {
            setRepositoriesStateView(
                title = getString(R.string.default_error_title),
                message = it,
                animationName = getString(R.string.animation_error)
            )
        }

        userRepositoriesSuccessEvent.observe(viewLifecycleOwner) {
            listRepositoryAdapter.submitList(it)
        }

        userDetailFailureEvent.observe(viewLifecycleOwner) {
            binding.vUserDetailStateView.show(errorMessage = it)
        }

        userDetailSuccessEvent.observe(viewLifecycleOwner) {
            viewModel.fetchUserRepositories(it.login)
            setupDetail(it)
        }
    }

    private fun setupDetail(userDetail: UserDetailModel) = with(binding) {
        vUserDetailStateView.hide()
        ivAvatar.loadImage(userDetail.avatarUrl.orEmpty())
        tvName.text = userDetail.name
        tvUsername.text = userDetail.login
        tvLocation.text = userDetail.location
        tvFollowers.text =
            String.format(getString(R.string.value_with_followers, userDetail.followers ?: 0))
        tvFollowing.text =
            String.format(getString(R.string.value_with_following, userDetail.following ?: 0))
    }

    private fun setupToolBar() {
        toolbar.title = getString(R.string.user_detail_title)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setupAdapter() {
        with(binding.rvUsers) {
            setHasFixedSize(true)
            adapter = listRepositoryAdapter
        }
    }

    private fun setRepositoriesStateView(
        title: String? = null,
        message: String? = null,
        animationName: String? = null
    ) {
        binding.vUserDetailStateView.setState(
            title,
            message,
            animationName
        )
    }
}
