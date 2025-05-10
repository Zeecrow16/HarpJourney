package com.example.harpjourneyapp.presentation.screens.tutor

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harpjourneyapp.data.LessonRequests
import com.example.harpjourneyapp.data.repository.LessonRequestRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class ViewLessonViewModel(
    private val repository: LessonRequestRepository = LessonRequestRepository()
) : ViewModel() {

    var requests by mutableStateOf<List<LessonRequests>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    init {
        loadRequests()
    }

    fun loadRequests() {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        isLoading = true

        viewModelScope.launch {
            try {
                requests = repository.getPendingRequestsForTutor(currentUserId)
            } catch (e: Exception) {
                Log.e("LessonRequestsVM", "Failed to load requests: ${e.localizedMessage}")
            } finally {
                isLoading = false
            }
        }
    }

    fun updateRequestStatus(requestId: String, newStatus: String) {
        viewModelScope.launch {
            try {
                repository.updateLessonStatusAndAssignUsers(requestId, newStatus)
                requests = requests.filterNot { it.id == requestId }
            } catch (e: Exception) {
                Log.e("LessonRequestsVM", "Update failed: ${e.localizedMessage}")
            }
        }
    }
}
