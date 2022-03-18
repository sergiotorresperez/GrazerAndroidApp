package com.sergiotorres.grazer.presentation.userlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sergiotorres.grazer.domain.model.User
import com.sergiotorres.grazer.domain.repository.UserRepository
import com.sergiotorres.grazer.observeForTesting
import com.sergiotorres.grazer.util.TestSchedulersProvider
import com.sergiotorres.grazer.util.TestSingle
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val schedulersProvider = TestSchedulersProvider()
    private val usersRepository: UserRepository = mock()

    private lateinit var viewStateObserver: Observer<UserListViewModel.ViewState>

    private val underTest = UserListViewModel(
        schedulersProvider = schedulersProvider,
        usersRepository = usersRepository
    )

    @Before
    fun setup() {
        viewStateObserver = underTest.getViewState().observeForTesting()
    }

    @Test
    fun `when initialise loads users from repository`() {
        val loadUsersOperation = TestSingle.just<List<User>>(mock())
        whenever(usersRepository.getUsers())
            .thenReturn(loadUsersOperation)

        underTest.initialise()

        loadUsersOperation.assertSubscribed()
    }

    @Test
    fun `when initialise posts loading view state`() {
        whenever(usersRepository.getUsers())
            .thenReturn(Single.never())

        underTest.initialise()

        val expectedViewState = UserListViewModel.ViewState.Loading
        verify(viewStateObserver).onChanged(expectedViewState)
    }

    @Test
    fun `when loading users succeeds posts success view state`() {
        val users: List<User> = mock()
        whenever(usersRepository.getUsers())
            .thenReturn(Single.just(users))

        underTest.initialise()

        val expectedViewState = UserListViewModel.ViewState.Content(users)
        verify(viewStateObserver).onChanged(expectedViewState)
    }

    @Test
    fun `when loading users  fails posts error view state`() {
        val exception: Exception = mock()
        whenever(usersRepository.getUsers())
            .thenReturn(Single.error(exception))

        underTest.initialise()

        val expectedViewState = UserListViewModel.ViewState.LoadError(exception)
        verify(viewStateObserver).onChanged(expectedViewState)
    }
}
