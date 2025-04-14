package com.example.harpjourneyapp.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harpjourneyapp.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val repository: UserRepository) : ViewModel() {
    private val _isRegistered = MutableLiveData<Boolean>()
    val isRegistered: LiveData<Boolean> get() = _isRegistered
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val _loginResult = MutableLiveData<Result<String>>()
    val loginResult: LiveData<Result<String>> get() = _loginResult

    fun signUp(email: String, password: String, role: String) {
        viewModelScope.launch {
            val result = repository.register(email, password, role)
            _isRegistered.postValue(result.isSuccess)
        }
    }

    fun fetchUserRole(onRoleFetched: (String?) -> Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            FirebaseFirestore.getInstance().collection("users")
                .document(it.uid)
                .get()
                .addOnSuccessListener { document ->
                    val role = document.getString("role")
                    onRoleFetched(role)
                }
                .addOnFailureListener {
                    onRoleFetched(null)
                }
        } ?: onRoleFetched(null)
    }


    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val userId = result.user?.uid ?: return@addOnSuccessListener
                firestore.collection("users").document(userId)
                    .get()
                    .addOnSuccessListener { doc ->
                        val role = doc.getString("role")
                        if (role != null) {
                            _loginResult.postValue(Result.success(role))
                        } else {
                            _loginResult.postValue(Result.failure(Exception("Role not found")))
                        }
                    }
                    .addOnFailureListener {
                        _loginResult.postValue(Result.failure(it))
                    }
            }
            .addOnFailureListener {
                _loginResult.postValue(Result.failure(it))
            }
    }
}
