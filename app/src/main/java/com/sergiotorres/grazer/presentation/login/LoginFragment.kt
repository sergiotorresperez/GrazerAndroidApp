package com.sergiotorres.grazer.presentation.login

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sergiotorres.grazer.R
import com.sergiotorres.grazer.databinding.FragmentLoginBinding
import com.sergiotorres.grazer.presentation.login.LoginViewModel.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    // TODO: extract navigation to coordinator/navigator
    interface Listener {
        fun onNavigateToUserListScreen()
    }

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    // TODO: show loading with a better UX than ProgressDialog
    private val progressDialog: ProgressDialog by lazy {
        ProgressDialog(requireContext()).apply {
            setCancelable(false)
            setTitle(R.string.login_loading)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        setupView()

        with(viewModel) {
            getViewState().observe(viewLifecycleOwner) { onViewStateUpdated(it) }
            getEvens().observe(viewLifecycleOwner) { event -> event.runOnce { onEvent(it) } }
        }
    }

    private fun onEvent(event: LoginViewModel.Event) {
        when (event) {
            LoginViewModel.Event.NavigateToUsersListScreen -> {
                (requireActivity() as Listener).onNavigateToUserListScreen()
            }
        }
    }

    private fun setupView() {
        with(binding) {
            loginSubmit.setOnClickListener {
                viewModel.logIn(
                    email = emailEditText.text.toString(),
                    password = passwordEditText.text.toString()
                )
            }
        }
    }

    private fun onViewStateUpdated(viewState: ViewState) {
        when (viewState) {
            ViewState.Loading -> {
                progressDialog.show()
                binding.passwordTextInputLayout.isErrorEnabled = false
            }
            ViewState.LoginError -> {
                progressDialog.dismiss()
                with(binding.passwordTextInputLayout) {
                    error = getString(R.string.login_failure)
                    isErrorEnabled = true
                }
            }
            ViewState.LoginSuccess -> {
                progressDialog.dismiss()
                binding.passwordTextInputLayout.isErrorEnabled = false
            }
        }
    }
}
