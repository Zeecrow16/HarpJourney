package com.example.harpjourneyapp.presentation.screens.tutor

import androidx.lifecycle.ViewModel
import com.example.harpjourneyapp.enum.HarpType
import com.example.harpjourneyapp.enum.Specialisation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TutorProfileViewModel : ViewModel() {

    private val _bio = MutableStateFlow("")
    val bio: StateFlow<String> get() = _bio
    private val _specialisation = MutableStateFlow<HarpType?>(null)
    val specialisation: StateFlow<HarpType?> get() = _specialisation
    val harpTypeOptions = HarpType.entries
    private val _selectedSpecialisations = MutableStateFlow<List<Specialisation>>(emptyList())
    val selectedSpecialisations: StateFlow<List<Specialisation>> get() = _selectedSpecialisations
    private val _selectedDates = MutableStateFlow<List<Long>>(emptyList())
    val selectedDates: StateFlow<List<Long>> get() = _selectedDates


    fun onSpecialisationChange(newSpecialisation: HarpType) {
        _specialisation.value = newSpecialisation
    }


    fun onBioChange(newBio: String) {
        _bio.value = newBio
    }

    fun onSpecialisationsChange(newSpecialisations: List<Specialisation>) {
        _selectedSpecialisations.value = newSpecialisations
    }

    val specialisationOptions = Specialisation.entries

    fun addOrRemoveDate(dateMillis: Long) {
        val current = _selectedDates.value.toMutableList()
        if (current.contains(dateMillis)) {
            current.remove(dateMillis)
        } else {
            current.add(dateMillis)
        }
        _selectedDates.value = current
    }

    fun clearSelectedDates() {
        _selectedDates.value = emptyList()
    }
    fun formatMillisToReadableDate(millis: Long): String {
        val sdf = SimpleDateFormat("DD MMM yyyy", Locale.getDefault())
        return sdf.format(Date(millis))
    }


}