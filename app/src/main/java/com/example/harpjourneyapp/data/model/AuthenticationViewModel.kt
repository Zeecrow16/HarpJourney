package com.example.harpjourneyapp.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harpjourneyapp.data.User
import com.example.harpjourneyapp.data.repository.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: UserRepository) : ViewModel() {

    private val _authenticated = MutableLiveData<Boolean>()
    val authenticated: LiveData<Boolean> get() = _authenticated

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun signUp(email: String, password: String, role: String) {
        viewModelScope.launch {
            val result = repository.register(email, password, role)
            if (result.isSuccess) {
                _authenticated.postValue(true)
                _errorMessage.postValue(null)
            } else {
                _authenticated.postValue(false)
                _errorMessage.postValue(result.exceptionOrNull()?.localizedMessage ?: "Registration failed.")
            }
        }
    }
}
