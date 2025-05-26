package com.example.harpjourneyapp

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.harpjourneyapp.data.repository.LessonRequestRepository
import com.example.harpjourneyapp.data.repository.QuestionsRepository
import com.example.harpjourneyapp.data.repository.StudentProfileRepository
import com.example.harpjourneyapp.data.repository.TutorProfileRepository
import com.example.harpjourneyapp.data.repository.UserRepository
import com.example.harpjourneyapp.presentation.screens.edit.StudentEditDetailsViewModel
import com.example.harpjourneyapp.presentation.screens.edit.TutorEditDetailsViewModel
import com.example.harpjourneyapp.presentation.screens.student.FindTutorViewModel
import com.example.harpjourneyapp.presentation.screens.student.PractiseTheoryViewModel
import com.example.harpjourneyapp.presentation.screens.student.StudentHomePageViewModel
import com.example.harpjourneyapp.presentation.screens.student.StudentProfileViewModel
import com.example.harpjourneyapp.presentation.screens.tutor.MarkTestViewModel
import com.example.harpjourneyapp.presentation.screens.tutor.TutorHomePageViewModel
import com.example.harpjourneyapp.presentation.screens.tutor.TutorProfileViewModel
import com.example.harpjourneyapp.presentation.screens.tutor.ViewLessonViewModel

object AppViewModelProvider {

    //lateinit var userRepository: UserRepository
    lateinit var studentProfileRepository: StudentProfileRepository
    lateinit var tutorProfileRepository: TutorProfileRepository
    lateinit var lessonRequestRepository: LessonRequestRepository
    lateinit var questionsRepository: QuestionsRepository

    val Factory: ViewModelProvider.Factory = viewModelFactory {

        initializer {
            StudentEditDetailsViewModel(studentProfileRepository)
        }
        initializer {
            TutorEditDetailsViewModel(tutorProfileRepository)
        }
        initializer {
            FindTutorViewModel(tutorProfileRepository)
        }
        initializer {
            PractiseTheoryViewModel(questionsRepository)
        }
        initializer {
            StudentHomePageViewModel(lessonRequestRepository)
        }
        initializer {
            StudentProfileViewModel(studentProfileRepository)
        }
        initializer {
            MarkTestViewModel(questionsRepository)
        }
        initializer {
            TutorHomePageViewModel(lessonRequestRepository)
        }
        initializer {
            TutorProfileViewModel(tutorProfileRepository)
        }
        initializer {
            ViewLessonViewModel(lessonRequestRepository)
        }

    }
}