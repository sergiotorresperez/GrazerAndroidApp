package com.sergiotorres.grazer.presentation.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.sergiotorres.grazer.R
import com.sergiotorres.grazer.databinding.FragmentUserListBinding
import com.sergiotorres.grazer.domain.model.User
import com.sergiotorres.grazer.presentation.userlist.UserListViewModel.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : Fragment() {

    companion object {
        fun newInstance() = UserListFragment()
    }

    private val viewModel: UserListViewModel by viewModels()
    private lateinit var binding: FragmentUserListBinding

    private val glideRequestManager: RequestManager by lazy { Glide.with(this) }
    private val userListAdapter: UserListAdapter by lazy {
        UserListAdapter(
            glideRequestManager = glideRequestManager
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()

        with(viewModel) {
            getViewState().observe(viewLifecycleOwner) { onViewStateUpdated(it) }
            initialise()
        }
    }

    private fun setupView() {
        binding.userListRecyclerView.apply {
            adapter = userListAdapter
            val lm = LinearLayoutManager(requireContext())
            layoutManager = lm
            addItemDecoration(DividerItemDecoration(requireContext(), lm.orientation))
        }
    }

    private fun onViewStateUpdated(viewState: ViewState) {
        when (viewState) {
            ViewState.Loading -> showLoadingContent()
            is ViewState.Content -> showContent(viewState.users)
            is ViewState.LoadError -> showContentError()
        }
    }

    private fun showLoadingContent() {
        binding.userListLoadingFlipper.showLoading()
    }

    private fun showContent(
        users: List<User>
    ) {
        binding.userListLoadingFlipper.showContent()
        userListAdapter.submitList(users)
    }

    private fun showContentError() {
        binding.userListLoadingFlipper.setErrorViewTitle(getString(R.string.users_load_error))
        binding.userListLoadingFlipper.showError()
    }

}
