package com.example.harpjourneyapp.presentation.screens.student

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.harpjourneyapp.enum.HarpType
import com.example.harpjourneyapp.enum.SkillLevel
import com.example.harpjourneyapp.enum.Specialisation

class StudentProfileViewModel : ViewModel() {
    var selectedSkillLevel by mutableStateOf<String?>(null)
    var selectedTags by mutableStateOf<List<String>>(emptyList())

    var skillLevels = mutableStateOf<List<String>>(emptyList())
    var tags = mutableStateOf<List<String>>(emptyList())

    var harpTypes = mutableStateOf<List<HarpType>>(HarpType.values().toList())
    var selectedHarpType by mutableStateOf<HarpType?>(null) // This should be an enum


    fun populateDropdowns() {
        skillLevels.value = SkillLevel.values().map { it.name }

        tags.value = Specialisation.values().map { it.name }
    }

    fun onSkillLevelSelected(skillLevel: String) {
        selectedSkillLevel = skillLevel
    }

    fun onTagSelected(tag: String) {
        if (selectedTags.contains(tag)) {
            selectedTags = selectedTags - tag
        } else {
            selectedTags = selectedTags + tag
        }
    }

    fun onHarpSelected(harpType: HarpType) {
        selectedHarpType = harpType // Use enum directly here
    }

}