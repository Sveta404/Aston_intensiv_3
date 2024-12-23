package com.example.contacts

class ContactsRepository {
    private val contacts = mutableListOf<Contact>()

    init {
        (0..99).map {
            Contact(
                id = it,
                firstname = firstName.random(),
                lastname = lastName.random(),
                phoneNumber = "+$it 950 611 66 13"
            )
        }.let { contacts.addAll(it) }
    }

    fun getContacts(): List<Contact> {
        return contacts
    }

    fun addContact(firstname: String, lastname: String, phoneNumber: String) {
        contacts.add(
            Contact(
                id = contacts.lastIndex + 1,
                firstname = firstname,
                lastname = lastname,
                phoneNumber = phoneNumber
            )
        )
    }

    fun deleteContact(id: Int) {
        contacts.removeIf {
            it.id == id
        }
    }

    fun getContact(id: Int): Contact? {
        return contacts.firstOrNull {
            it.id == id
        }
    }

    fun editContact(contact: Contact) {
        val idX = contacts.indexOfFirst {
            it.id == contact.id
        }
        contacts.set(idX, contact)
    }

    companion object {
        val firstName = listOf(
            "Светлана",
            "Анна",
            "Екатерина",
            "Мария",
            "Ольга",
            "Ирина",
            "Дарья",
            "Татьяна",
            "Елена",
            "Наталья",
            "Виктор",
            "Александр",
            "Дмитрий",
            "Сергей",
            "Андрей",
            "Михаил",
            "Владимир",
            "Иван",
            "Антон",
            "Константин"
        )

        val lastName = listOf(
            "Шмидт",
            "Штерн",
            "Шварц",
            "Волк",
            "Заяц",
            "Лебедь",
            "Чайка",
            "Мицкевич",
            "Сенкевич",
            "Коваленко",
            "Петренко",
            "Галиченко",
            "Кличко",
            "Бабич",
            "Зингер",
            "Левитан",
            "Вассерман",
            "Акопян",
            "Бабаян",
            "Овсепян"
        )
    }
}
