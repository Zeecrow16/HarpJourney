//package com.example.harpjourneyapp
//
//import android.app.Application
//import com.example.harpjourneyapp.data.Contact
//import com.example.harpjourneyapp.data.ContactRepository
//import com.example.harpjourneyapp.data.InMemoryRepository
//import java.util.UUID
//
//class ContactApplication : Application() {
//    companion object {
//        private lateinit var contactInMemoryRepository: ContactRepository<Contact>
//
//        fun getRepository(): ContactRepository<Contact> = contactInMemoryRepository
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        contactInMemoryRepository = InMemoryRepository()
//        addSampleRecordsToRepository()
//    }
//
//    private fun addSampleRecordsToRepository() {
//        contactInMemoryRepository.insert(
//            Contact(UUID.randomUUID(), "firstname1", "surname1", "johns@email.com")
//        )
//        contactInMemoryRepository.insert(
//            Contact(UUID.randomUUID(), "firstname2", "surname2", "janed@email.com")
//        )
//        contactInMemoryRepository.insert(
//            Contact(UUID.randomUUID(), "firstname3", "surname3", "user@email.com")
//        )
//    }
//}
