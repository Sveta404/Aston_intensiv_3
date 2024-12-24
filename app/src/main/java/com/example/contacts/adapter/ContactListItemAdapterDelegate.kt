package com.example.contacts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.R
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class ContactListItemAdapterDelegate(
    private val onClickContact: (id: Int) -> Unit,
    private val onCheck: (id: Int, isChecked: Boolean) -> Unit
) : AbsListItemAdapterDelegate<ContactItem, Any, ContactListItemAdapterDelegate.ContactViewHolder>() {

    public override fun isForViewType(item: Any, items: List<Any>, position: Int): Boolean {
        return item is ContactItem
    }

    public override fun onCreateViewHolder(parent: ViewGroup): ContactViewHolder {
        return ContactViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        )
    }

    override fun onBindViewHolder(item: ContactItem, vh: ContactViewHolder, payloads: List<Any>) {
        vh.firstname.setText(item.firstname)
        vh.lastname.setText(item.lastname)
        vh.phoneNumber.setText(item.phoneNumber)
        vh.itemView.setOnClickListener {
            onClickContact.invoke(item.id)
        }
        vh.checkBox.isVisible = item.checkBoxVisibility
        vh.checkBox.setOnCheckedChangeListener { _, isChecked ->
            onCheck(item.id, isChecked)
        }
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var firstname: TextView = itemView.findViewById<View>(R.id.firstnameTextView) as TextView
        var lastname: TextView = itemView.findViewById<View>(R.id.lastnameTextView) as TextView
        var phoneNumber: TextView = itemView.findViewById<View>(R.id.phoneTextView) as TextView
        var checkBox: CheckBox = itemView.findViewById<View>(R.id.CheckBox) as CheckBox
    }
}