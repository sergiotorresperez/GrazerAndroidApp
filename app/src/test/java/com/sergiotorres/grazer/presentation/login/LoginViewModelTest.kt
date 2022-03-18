package com.sergiotorres.grazer.presentation.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sergiotorres.grazer.domain.repository.AuthRepository
import com.sergiotorres.grazer.observeForTesting
import com.sergiotorres.grazer.presentation.util.OneOffEvent
import com.sergiotorres.grazer.util.TestCompletable
import com.sergiotorres.grazer.util.TestSchedulersProvider
import io.reactivex.Completable
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val schedulersProvider = TestSchedulersProvider()
    private val authRepository: AuthRepository = mock()

    private lateinit var viewStateObserver: Observer<LoginViewModel.ViewState>
    private lateinit var eventsObserver: Observer<OneOffEvent<LoginViewModel.Event>>

    private val underTest = LoginViewModel(
        schedulersProvider = schedulersProvider,
        authRepository = authRepository
    )

    @Before
    fun setup() {
        viewStateObserver = underTest.getViewState().observeForTesting()
        eventsObserver = underTest.getEvens().observeForTesting()
    }

    @Test
    fun `when login delegates into repository`() {
        val loginOperation = TestCompletable.never()
        whenever(authRepository.logIn("email", "password"))
            .thenReturn(loginOperation)

        underTest.logIn("email", "password")

        loginOperation.assertSubscribed()
    }

    @Test
    fun `when login posts loading view state`() {
        whenever(authRepository.logIn("email", "password"))
            .thenReturn(Completable.never())

        underTest.logIn("email", "password")

        val expectedViewState = LoginViewModel.ViewState.Loading
        verify(viewStateObserver).onChanged(expectedViewState)
    }

    @Test
    fun `when login succeeds posts success view state`() {
        whenever(authRepository.logIn("email", "password"))
            .thenReturn(Completable.complete())

        underTest.logIn("email", "password")

        val expectedViewState = LoginViewModel.ViewState.LoginSuccess
        verify(viewStateObserver).onChanged(expectedViewState)
    }

    @Test
    fun `when login fails posts error view state`() {
        whenever(authRepository.logIn("email", "password"))
            .thenReturn(Completable.error(Exception()))

        underTest.logIn("email", "password")

        val expectedViewState = LoginViewModel.ViewState.LoginError
        verify(viewStateObserver).onChanged(expectedViewState)
    }

    @Test
    fun `when login succeeds posts navigate to users screen event`() {
        whenever(authRepository.logIn("email", "password"))
            .thenReturn(Completable.complete())

        underTest.logIn("email", "password")

        val expectedEvent = LoginViewModel.Event.NavigateToUsersListScreen
        verify(eventsObserver).onChanged(OneOffEvent(expectedEvent))
    }
}
