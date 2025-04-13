package com.example.harpjourneyapp.presentation.screens.student
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harpjourneyapp.data.HarpQuestions
import com.example.harpjourneyapp.data.QuestionsRepository
import com.example.harpjourneyapp.enum.SkillLevel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PractiseTheoryViewModel:  ViewModel() {

    private val _questions = mutableStateOf<List<HarpQuestions>>(emptyList())
    val questions: State<List<HarpQuestions>> = _questions

    fun loadQuestions(level: SkillLevel) {
        _questions.value = QuestionsRepository().getRandomQuestions(level)
    }
}


