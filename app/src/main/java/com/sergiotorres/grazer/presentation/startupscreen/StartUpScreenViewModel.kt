package com.sergiotorres.grazer.presentation.startupscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sergiotorres.grazer.domain.repository.AuthRepository
import com.sergiotorres.grazer.presentation.util.OneOffEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class StartUpScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _events = MutableLiveData<OneOffEvent<Event>>()
    fun getEvens(): LiveData<OneOffEvent<Event>> = _events

    private val disposables = CompositeDisposable()

    fun initialise() {
        if (!authRepository.isUserLoggedIn()) {
            _events.postValue(OneOffEvent(Event.NavigateToLoginScreen))
        } else {
            _events.postValue(OneOffEvent(Event.NavigateToUsersListScreen))
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    sealed class Event {
        object NavigateToLoginScreen : Event()
        object NavigateToUsersListScreen : Event()
    }
}
