package com.sergiotorres.grazer.presentation.startupscreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sergiotorres.grazer.domain.repository.AuthRepository
import com.sergiotorres.grazer.observeForTesting
import com.sergiotorres.grazer.presentation.util.OneOffEvent
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class StartUpScreenViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val authRepository: AuthRepository = mock()

    private lateinit var eventsObserver: Observer<OneOffEvent<StartUpScreenViewModel.Event>>

    private val underTest = StartUpScreenViewModel(
        authRepository = authRepository
    )

    @Before
    fun setup() {
        eventsObserver = underTest.getEvens().observeForTesting()
    }

    @Test
    fun `when init and not logged in post navigate to login screen event`() {
        whenever(authRepository.isUserLoggedIn())
            .thenReturn(false)

        underTest.initialise()

        verify(eventsObserver).onChanged(OneOffEvent(StartUpScreenViewModel.Event.NavigateToLoginScreen))
    }

    @Test
    fun `when init and logged in post navigate to users screen event`() {
        whenever(authRepository.isUserLoggedIn())
            .thenReturn(true)

        underTest.initialise()

        verify(eventsObserver).onChanged(OneOffEvent(StartUpScreenViewModel.Event.NavigateToUsersListScreen))
    }
}
