package com.example.harpjourneyapp

import android.app.Application
import android.util.Log
import com.example.harpjourneyapp.data.repository.LessonRequestRepository
import com.example.harpjourneyapp.data.repository.QuestionsRepository
import com.example.harpjourneyapp.data.repository.StudentProfileRepository
import com.example.harpjourneyapp.data.repository.TutorProfileRepository
import com.google.firebase.FirebaseApp

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
        Log.d("MyApp", "FirebaseApp initialized: ${FirebaseApp.getInstance()}")

        AppViewModelProvider.studentProfileRepository = StudentProfileRepository()
        AppViewModelProvider.tutorProfileRepository = TutorProfileRepository()
        AppViewModelProvider.questionsRepository = QuestionsRepository()
        AppViewModelProvider.lessonRequestRepository = LessonRequestRepository()
    }
}