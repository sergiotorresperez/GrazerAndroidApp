package com.sergiotorres.grazer.presentation.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sergiotorres.grazer.domain.model.User
import com.sergiotorres.grazer.domain.repository.UserRepository
import com.sergiotorres.grazer.presentation.SchedulersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val schedulersProvider: SchedulersProvider,
    private val usersRepository: UserRepository
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    fun getViewState(): LiveData<ViewState> = _viewState

    private val disposables = CompositeDisposable()

    fun initialise() {
        loadUsers()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    private fun loadUsers() {
        usersRepository.getUsers()
            .doOnSubscribe {
                _viewState.postValue(ViewState.Loading)
            }
            .map {
                ViewState.Content(it)
            }
            .subscribeOn(schedulersProvider.io())
            .subscribe(
                {
                    _viewState.postValue(it)
                },
                {
                    Timber.e("Error observing users", it)
                    _viewState.postValue(ViewState.LoadError(it))
                },
            )
            .also {
                disposables.add(it)
            }
    }

    sealed class ViewState {
        object Loading : ViewState()

        data class Content(
            val users: List<User>
        ) : ViewState()

        data class LoadError(
            val throwable: Throwable
        ) : ViewState()
    }

}
