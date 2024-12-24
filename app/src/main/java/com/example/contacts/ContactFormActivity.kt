package com.example.contacts

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity

class ContactFormActivity : ComponentActivity() {

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_form)

        firstNameEditText = findViewById(R.id.firstNameEditText)
        lastNameEditText = findViewById(R.id.lastNameEditText)
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText)
        saveButton = findViewById(R.id.saveButton)

        val id = intent.getIntExtra("id", -1)
        val firstname = intent.getStringExtra("firstname")
        val lastname = intent.getStringExtra("lastname")
        val phoneNumber = intent.getStringExtra("phoneNumber")

        if (id != -1) {
            firstNameEditText.setText(requireNotNull(firstname))
            lastNameEditText.setText(requireNotNull(lastname))
            phoneNumberEditText.setText(requireNotNull(phoneNumber))
        }

        saveButton.setOnClickListener {
            val firstname = firstNameEditText.text.toString()
            val lastname = lastNameEditText.text.toString()
            val phoneNumber = phoneNumberEditText.text.toString()

            val resultIntent = Intent()
            resultIntent.putExtra("id", id)
            resultIntent.putExtra("firstname", firstname)
            resultIntent.putExtra("lastname", lastname)
            resultIntent.putExtra("phoneNumber", phoneNumber)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
