package com.example.harpjourneyapp.presentation.screens.student


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harpjourneyapp.data.HarpQuestions
import com.example.harpjourneyapp.data.repository.QuestionsRepository
import com.example.harpjourneyapp.enum.SkillLevel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PractiseTheoryViewModel(private val questionRepository: QuestionsRepository = QuestionsRepository()) : ViewModel() {

    private val _questions = MutableStateFlow<List<HarpQuestions>>(emptyList())
    val questions: StateFlow<List<HarpQuestions>> get() = _questions

    fun loadQuestions(skillLevel: SkillLevel) {
        viewModelScope.launch {
            try {
                val fetchedQuestions = questionRepository.getAllQuestions()
                _questions.value = fetchedQuestions.take(3)
            } catch (e: Exception) {
                _questions.value = emptyList()
            }
        }
    }
}