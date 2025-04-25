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

    private val _skillLevel = MutableStateFlow<SkillLevel>(SkillLevel.BEGINNER)
    val skillLevel: StateFlow<SkillLevel> get() = _skillLevel

    private val _filteredQuestions = MutableStateFlow<List<HarpQuestions>>(emptyList())
    val filteredQuestions: StateFlow<List<HarpQuestions>> get() = _filteredQuestions

    fun fetchUserSkillLevel() {
        viewModelScope.launch {
            try {
                val uid = studentProfileRepository.getCurrentUserUid()
                if (uid.isNullOrEmpty()) {
                    Log.e("PractiseTheoryViewModel", "UID is null or empty.")
                    return@launch
                }

                val level = studentProfileRepository.getUserSkillLevel(uid)
                _skillLevel.value = level
                Log.d("PractiseTheoryViewModel", "User skill level: $level")

            } catch (e: Exception) {
                Log.e("PractiseTheoryViewModel", "Failed to fetch skill level", e)
            }
        }
    }

    fun fetchAllQuestionsAndSkillLevels() {
        viewModelScope.launch {
            try {
                val questions = questionsRepository.getAllQuestions()

                val filteredQuestions = questions.filter { question ->
                    question.skill_level.equals(_skillLevel.value.name, ignoreCase = true)
                }

                _filteredQuestions.value = filteredQuestions.shuffled().take(3)

                Log.d("PractiseTheoryViewModel", "Fetched ${filteredQuestions.size} matching questions, showing 3 random ones.")

            } catch (e: Exception) {
                Log.e("PractiseTheoryViewModel", "Error fetching questions", e)
            }
        }
    }
}
