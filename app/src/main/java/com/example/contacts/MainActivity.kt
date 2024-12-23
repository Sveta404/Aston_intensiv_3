package com.example.contacts

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.adapter.ContactAdapter

class MainActivity : ComponentActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: Button

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private val adapter = ContactAdapter(
        onDeleteContacts = { id ->
            contactsRepository.deleteContact(id)
            renderContacts(contactsRepository.getContacts())
        },
        onClickContact = { id ->
            val contact = contactsRepository.getContact(id)
            contact?.let {
                val intent = Intent(this, ContactFormActivity::class.java)
                intent.putExtra("id", it.id)
                intent.putExtra("firstname", it.firstname)
                intent.putExtra("lastname", it.lastname)
                intent.putExtra("phoneNumber", it.phoneNumber)
                resultLauncher.launch(intent)
            }
        }
    )

    private val contactsRepository = ContactsRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        addButton = findViewById(R.id.addButton)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        adapter.items = contactsRepository.getContacts()

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val id = requireNotNull(result.data?.getIntExtra("id", -1))
                val firstname = requireNotNull(result.data?.getStringExtra("firstname"))
                val lastname = requireNotNull(result.data?.getStringExtra("lastname"))
                val phoneNumber = requireNotNull(result.data?.getStringExtra("phoneNumber"))

                if (id == -1) {
                    contactsRepository.addContact(firstname, lastname, phoneNumber)
                } else {
                    contactsRepository.editContact(Contact(id, firstname, lastname, phoneNumber))
                }

                renderContacts(contactsRepository.getContacts())
            }
        }

        addButton.setOnClickListener {
            val intent = Intent(this, ContactFormActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    private fun renderContacts(contacts: List<Contact>) {
        adapter.items = contacts
        adapter.notifyDataSetChanged()
    }
}
