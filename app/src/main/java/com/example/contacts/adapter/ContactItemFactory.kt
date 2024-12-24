package com.example.contacts.adapter

import com.example.contacts.Contact

class ContactItemFactory {
    fun createItem(contact: Contact, checkBoxVisibility: Boolean): ContactItem {
        return ContactItem(
            id = contact.id,
            firstname = contact.firstname,
            lastname = contact.lastname,
            phoneNumber = contact.phoneNumber,
            checkBoxVisibility = checkBoxVisibility
        )
    }

    fun createItems(contacts: List<Contact>, checkBoxVisibility: Boolean = false): List<ContactItem> {
        return contacts.map {
            createItem(it, checkBoxVisibility)
        }
    }
}