package br.com.github.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.github.R
import br.com.github.databinding.FragmentUserDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class UserDetailFragment : Fragment() {

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
    }

    private fun setupObservers() = with(viewModel) {
        user.observe(viewLifecycleOwner) {
            fetchUser(it.login)
        }
    }

    private fun setupToolBar() {
        toolbar.title = getString(R.string.user_detail_title)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }
}
