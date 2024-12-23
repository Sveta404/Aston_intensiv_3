package com.example.contacts.adapter

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class ContactAdapter(
    onDeleteContacts: (id: Int) -> Unit,
    onClickContact: (id: Int) -> Unit
) : ListDelegationAdapter<List<Any>>() {

    init {
        delegatesManager.addDelegate(ContactListItemAdapterDelegate(onDeleteContacts, onClickContact))
    }
}


