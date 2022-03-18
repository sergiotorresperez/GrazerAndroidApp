package com.sergiotorres.grazer.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sergiotorres.grazer.domain.repository.AuthRepository
import com.sergiotorres.grazer.presentation.SchedulersProvider
import com.sergiotorres.grazer.presentation.util.OneOffEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val schedulersProvider: SchedulersProvider,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _events = MutableLiveData<OneOffEvent<LoginViewModel.Event>>()
    fun getEvens(): LiveData<OneOffEvent<LoginViewModel.Event>> = _events

    private val _viewState = MutableLiveData<ViewState>()
    fun getViewState(): LiveData<ViewState> = _viewState

    private val disposables = CompositeDisposable()


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun logIn(email: String, password: String) {
        authRepository.logIn(email = email, password = password)
            .subscribeOn(schedulersProvider.io())
            .doOnSubscribe {
                _viewState.postValue(ViewState.Loading)
            }
            .subscribe(
                {
                    _events.postValue(OneOffEvent(Event.NavigateToUsersListScreen))
                    _viewState.postValue(ViewState.LoginSuccess)
                },
                {
                    Timber.e("Error observing recently viewed items", it)
                    _viewState.postValue(ViewState.LoginError)
                },
            )
            .also {
                disposables.add(it)
            }
    }

    sealed class Event {
        object NavigateToUsersListScreen : Event()
    }

    sealed class ViewState {
        object Loading : ViewState()
        object LoginSuccess : ViewState()
        object LoginError : ViewState()
    }
}