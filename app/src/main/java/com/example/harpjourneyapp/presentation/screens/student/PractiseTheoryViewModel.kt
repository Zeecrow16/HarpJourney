package com.example.harpjourneyapp.presentation.screens.student

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harpjourneyapp.data.HarpQuestions
import com.example.harpjourneyapp.data.repository.QuestionsRepository
import com.example.harpjourneyapp.data.repository.StudentProfileRepository
import com.example.harpjourneyapp.enum.SkillLevel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PractiseTheoryViewModel(
    private val questionsRepository: QuestionsRepository = QuestionsRepository(),
    private val studentProfileRepository: StudentProfileRepository = StudentProfileRepository()
) : ViewModel() {

    private val _questions = MutableStateFlow<List<HarpQuestions>>(emptyList())
    val questions: StateFlow<List<HarpQuestions>> get() = _questions

    private val _skillLevel = MutableStateFlow<SkillLevel>(SkillLevel.BEGINNER)
    val skillLevel: StateFlow<SkillLevel> get() = _skillLevel

    private val _selectedAnswers = MutableStateFlow<List<String?>>(emptyList())
    val selectedAnswers: StateFlow<List<String?>> get() = _selectedAnswers

    private val _showResults = MutableStateFlow(false)
    val showResults: StateFlow<Boolean> get() = _showResults

    fun getUserSkillLevel(uid: String) {
        viewModelScope.launch {
            try {
                // Log the initial UID value for debugging
                Log.d("PractiseTheoryViewModel", "Fetching skill level for user with UID: $uid")

                // Check if the UID is null or empty
                if (uid.isNullOrEmpty()) {
                    Log.e("PractiseTheoryViewModel", "Error: User UID is null or empty.")
                    _skillLevel.value = SkillLevel.BEGINNER
                    loadAllQuestions()
                    return@launch
                }

                // Retrieve the user skill level from the repository
                val userSkillLevel = studentProfileRepository.getUserSkillLevel(uid)

                // Log the retrieved skill level
                Log.d("PractiseTheoryViewModel", "Fetched user skill level: $userSkillLevel")

                // Set the skill level in the ViewModel and load questions
                _skillLevel.value = userSkillLevel
                loadAllQuestions()
            } catch (e: Exception) {
                // Log any exceptions that occur during the process
                Log.e("PractiseTheoryViewModel", "Error fetching user skill level for UID: $uid", e)

                // Set a default skill level if an error occurs
                _skillLevel.value = SkillLevel.BEGINNER
                loadAllQuestions()
            }
        }
    }


    private fun loadAllQuestions() {
        viewModelScope.launch {
            try {
                val allQuestions = questionsRepository.getAllQuestions()
                _questions.value = allQuestions.take(3)
                _selectedAnswers.value = List(allQuestions.size) { null }
                matchSkillLevel()
            } catch (e: Exception) {
                _questions.value = emptyList()
            }
        }
    }

    private fun matchSkillLevel() {
        viewModelScope.launch {
            try {
                val filteredQuestions = _questions.value.filter { question ->
                    question.skill_level.equals(_skillLevel.value.name, ignoreCase = true)
                }

                _questions.value = filteredQuestions.take(3)
            } catch (e: Exception) {
                _questions.value = emptyList()
            }
        }
    }



    fun submitAnswers() {
        _showResults.value = true
    }

    fun cancelResults() {
        _showResults.value = false
    }

    fun updateSelectedAnswer(index: Int, answer: String?) {
        val updatedAnswers = _selectedAnswers.value.toMutableList()
        updatedAnswers[index] = answer
        _selectedAnswers.value = updatedAnswers
    }
}
