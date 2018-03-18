package com.nostratech.mahdi.androidannotations.listcontact;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostratech.mahdi.androidannotations.R;
import com.nostratech.mahdi.androidannotations.addeditcontact.AddEditContactActivity_;
import com.nostratech.mahdi.androidannotations.model.Contact;

import java.util.List;

public class ListContactAdapter extends RecyclerView.Adapter<ListContactAdapter.ContactViewHolder> {

    private final Context context;
    private final List<Contact> contactList;

    public ListContactAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        final Contact contact = contactList.get(position);
        holder.bindView(contact);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout placeholder;
        TextView fullName;

        ContactViewHolder(View itemView) {
            super(itemView);
            placeholder = itemView.findViewById(R.id.contactPlaceholder);
            fullName = itemView.findViewById(R.id.fullName);
        }

        @SuppressLint("SetTextI18n")
        void bindView(final Contact contact) {
            fullName.setText(contact.firstName + " " + contact.lastName);
            placeholder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddEditContactActivity_.intent(context)
                            .extra("code", AddEditContactActivity_.CODE_EDIT_CONTACT)
                            .extra("contactId", contact.id)
                            .start();
                }
            });
        }
    }
}
