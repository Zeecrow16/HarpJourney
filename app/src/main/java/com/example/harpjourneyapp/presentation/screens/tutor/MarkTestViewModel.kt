package com.example.harpjourneyapp.presentation.screens.tutor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harpjourneyapp.data.SubmittedTest
import com.example.harpjourneyapp.data.repository.QuestionsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MarkTestViewModel(
    private val questionsRepository: QuestionsRepository = QuestionsRepository()
) : ViewModel() {

    private val _submittedTests = MutableStateFlow<List<SubmittedTest>>(emptyList())
    val submittedTests: StateFlow<List<SubmittedTest>> get() = _submittedTests

    fun fetchAllSubmittedTests() {
        viewModelScope.launch {
            Log.d("MarkTestViewModel", "Fetching all submitted tests")
            try {
                val tests = questionsRepository.getAllSubmittedTests()
                _submittedTests.value = tests
                Log.d("MarkTestViewModel", "Fetched ${tests.size} submitted tests.")
            } catch (e: Exception) {
                Log.e("MarkTestViewModel", "Failed to fetch submitted tests", e)
            }
        }
    }
}

