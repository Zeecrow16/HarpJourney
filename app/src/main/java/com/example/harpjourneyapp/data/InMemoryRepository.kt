package com.example.harpjourneyapp.data

interface ContactRepository <T>{
    fun findAll(): List<T>
    fun findById(index: Int): T
    fun insert(newContact: T)
    fun delete(contactToDelete: T)
    fun edit(selectedContactToEdit: T)
}
class InMemoryRepository (private val contactsRepository: MutableList<Contact> = ArrayList())
    : ContactRepository<Contact> {
    override fun findAll(): MutableList<Contact>{
        return contactsRepository
    }
    override fun findById(index: Int): Contact{
        return contactsRepository[index]
    }
    override fun insert(newContact: Contact){
        contactsRepository.add(newContact)
    }
    override fun delete(contactToDelete: Contact){
        contactsRepository.remove(contactToDelete)
    }
    override fun edit(selectedContactToEdit: Contact){

        for (contact in contactsRepository.iterator()){
            if (contact.id==selectedContactToEdit.id){
                contact.firstName = selectedContactToEdit.firstName
                contact.surname = selectedContactToEdit.surname
                contact.email = selectedContactToEdit.email
            }
        }
    }
}