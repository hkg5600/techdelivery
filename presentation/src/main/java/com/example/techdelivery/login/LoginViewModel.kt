package com.example.techdelivery.login

import android.util.Patterns
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.core.domain.session.LoginUseCase
import com.example.core.utils.Event
import com.example.core.utils.Result
import com.example.core.utils.execute
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
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

    private val _loginError = MutableLiveData<Event<String>>()
    val loginError : LiveData<Event<String>>
        get() = _loginError

    private val _navigateToMain = MutableLiveData<Event<Unit>>()
    val navigateToMain : LiveData<Event<Unit>>
        get() = _navigateToMain

    private val _loading = MutableLiveData<Boolean>(false)
    val loading : LiveData<Boolean>
        get() = _loading

    private val emailChangeCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            _loginButtonEnabled.value =   Patterns.EMAIL_ADDRESS.matcher(email.get() ?: "").matches()
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
            _loading.value = true
            loginUseCase(token).execute {
                when (it) {
                    is Result.Success -> navigateToMain()
                    is Result.Error -> _loginError.value = Event(it.exception.message ?: "Unknown")
                }
            }
            _loading.value = false
        }
    }

    fun navigateToMain() {
        _navigateToMain.value = Event(Unit)
    }

    override fun onCleared() {
        super.onCleared()
        email.removeOnPropertyChangedCallback(emailChangeCallback)
    }
}