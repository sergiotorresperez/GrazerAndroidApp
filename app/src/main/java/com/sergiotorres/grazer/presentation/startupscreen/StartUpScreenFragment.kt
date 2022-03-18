package com.sergiotorres.grazer.presentation.startupscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sergiotorres.grazer.databinding.FragmentStartupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartUpScreenFragment : Fragment() {

    companion object {
        fun newInstance() = StartUpScreenFragment()
    }

    // TODO: extract navigation to coordinator/navigator
    interface Listener {
        fun onNavigateToLoginScreen()
        fun onNavigateToUserListScreen()
    }

    private val viewModel: StartUpScreenViewModel by viewModels()
    private lateinit var binding: FragmentStartupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartupBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            getEvens().observe(viewLifecycleOwner) { event -> event.runOnce { onEvent(it) } }
            initialise()
        }
    }

    private fun onEvent(event: StartUpScreenViewModel.Event) {
        when (event) {
            StartUpScreenViewModel.Event.NavigateToLoginScreen -> {
                (requireActivity() as Listener).onNavigateToLoginScreen()
            }
            StartUpScreenViewModel.Event.NavigateToUsersListScreen -> {
                (requireActivity() as Listener).onNavigateToUserListScreen()
            }
        }
    }
}
