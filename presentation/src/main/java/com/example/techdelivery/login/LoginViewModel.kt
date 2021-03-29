package com.example.techdelivery.login

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.core.domain.session.LoginUseCase
import com.example.core.utils.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class LoginViewModel @ViewModelInject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    val reLogin = savedStateHandle.getLiveData<Boolean>("RE_LOGIN")

    val email = ObservableField<String>("")

    private val _loginButtonEnabled = MutableLiveData<Boolean>()
    val loginButtonEnabled : LiveData<Boolean>
        get() = _loginButtonEnabled

    private val _startLoginFlow = MutableLiveData<Event<String>>()
    val startLoginFlow : LiveData<Event<String>>
        get() = _startLoginFlow

    private val emailChangeCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            _loginButtonEnabled.value = !email.get().isNullOrBlank()
        }
    }

    init {
        email.addOnPropertyChangedCallback(emailChangeCallback)
    }

    fun startLoginFlow() {
        _startLoginFlow.value = Event(email.get() ?: "")
    }

    fun login(token: String) {
        viewModelScope.launch {
            loginUseCase(token).collect {
                
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        email.removeOnPropertyChangedCallback(emailChangeCallback)
    }
}