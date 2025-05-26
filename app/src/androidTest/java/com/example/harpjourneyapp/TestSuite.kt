package com.example.harpjourneyapp

import com.example.harpjourneyapp.components.BottomNavTest
import com.example.harpjourneyapp.components.CustomAcceptLessonCardTest
import com.example.harpjourneyapp.components.CustomSelectPickerTest
import com.example.harpjourneyapp.components.FindTutorCardTest
import com.example.harpjourneyapp.components.LoginButtonTest
import com.example.harpjourneyapp.components.LoginFieldsTest
import com.example.harpjourneyapp.components.SpecialisationPickerTest
import com.example.harpjourneyapp.components.ViewLessonsTest
import com.example.harpjourneyapp.components.ViewStudentsTest
import com.example.harpjourneyapp.components.profile.CustomBioTest
import com.example.harpjourneyapp.screens.FindTutorScreenTest
import com.example.harpjourneyapp.screens.StudentEditProfileTest
import com.example.harpjourneyapp.screens.StudentHomeScreenTest
import com.example.harpjourneyapp.screens.TutorHomeTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    BottomNavTest::class,
    ViewLessonsTest::class,
    LoginFieldsTest::class,
    LoginButtonTest::class,
    CustomBioTest::class,
    CustomSelectPickerTest::class,
    SpecialisationPickerTest::class,
    FindTutorCardTest::class,
    CustomAcceptLessonCardTest::class,
    ViewStudentsTest::class,
    StudentEditProfileTest::class,
    FindTutorScreenTest::class,
    StudentHomeScreenTest::class,
    TutorHomeTest::class,

)
class TestSuite