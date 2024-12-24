package com.example.contacts

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.adapter.ContactAdapter
import com.example.contacts.adapter.ContactItem
import com.example.contacts.adapter.ContactItemFactory

class MainActivity : ComponentActivity() {

    private val checkedItems = mutableSetOf<Int>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var deleteButton: ImageButton
    private lateinit var addButton: Button
    private lateinit var cancelButton: Button
    private lateinit var removeButton: Button

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private val contactItemFactory = ContactItemFactory()

    private val adapter = ContactAdapter(
        onCheck = { id, isChecked ->
            if (isChecked) {
                checkedItems.add(id)
            } else {
                checkedItems.remove(id)
            }
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

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        deleteButton = findViewById(R.id.deleteButton)
        addButton = findViewById(R.id.addButton)
        cancelButton = findViewById(R.id.cancelButton)
        removeButton = findViewById(R.id.removeButton)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        renderContacts(contactItemFactory.createItems(contactsRepository.getContacts()))

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

                renderContacts(contactItemFactory.createItems(contactsRepository.getContacts()))
            }
        }

        addButton.setOnClickListener {
            val intent = Intent(this, ContactFormActivity::class.java)
            resultLauncher.launch(intent)
        }

        deleteButton.setOnClickListener {
            renderContacts(contactItemFactory.createItems(contactsRepository.getContacts(), true))
            enterEditMode(true)
        }

        cancelButton.setOnClickListener {
            renderContacts(contactItemFactory.createItems(contactsRepository.getContacts(), false))
            enterEditMode(false)
            checkedItems.clear()
        }

        removeButton.setOnClickListener {
            contactsRepository.deleteContacts(checkedItems)
            renderContacts(contactItemFactory.createItems(contactsRepository.getContacts(), false))
            enterEditMode(false)
        }
    }

    private fun enterEditMode(editMode: Boolean) {
        cancelButton.isVisible = editMode
        removeButton.isVisible = editMode
        addButton.isVisible = !editMode

    }

    private fun renderContacts(contacts: List<ContactItem>) {
        adapter.items = contacts
        adapter.notifyDataSetChanged()
    }
}
