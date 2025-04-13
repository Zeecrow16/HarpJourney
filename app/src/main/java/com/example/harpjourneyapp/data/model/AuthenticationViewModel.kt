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
}
