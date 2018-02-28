package com.nostratech.mahdi.androidannotations.addeditcontact;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.nostratech.mahdi.androidannotations.R;
import com.nostratech.mahdi.androidannotations.model.Contact;
import com.nostratech.mahdi.androidannotations.model.source.ContactDao;
import com.nostratech.mahdi.androidannotations.util.DbProvider;

public class AddEditContactActivity extends AppCompatActivity {

    private ContactDao contactDao;

    public static final int CODE_EDIT_CONTACT = 555;

    private TextInputLayout firstNameLayout;
    private TextInputEditText firstName;
    private TextInputLayout lastNameLayout;
    private TextInputEditText lastName;
    private TextInputLayout phoneNumberLayout;
    private TextInputEditText phoneNumber;
    private TextInputLayout homeAddressLayout;
    private TextInputEditText homeAddress;

    private int code;
    private long contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        code = getIntent().getIntExtra("code", -1);

        setContentView(R.layout.activity_add_edit_contact);

        contactDao = new DbProvider().getDb(this).contactDao();

        firstNameLayout = findViewById(R.id.firstNameLayout);
        lastNameLayout = findViewById(R.id.lastNameLayout);
        phoneNumberLayout = findViewById(R.id.phoneNumberLayout);
        homeAddressLayout = findViewById(R.id.homeAddressLayout);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phoneNumber = findViewById(R.id.phoneNumber);
        homeAddress = findViewById(R.id.homeAddress);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (contactIsValid()) new saveContact(bindRequest()).execute();
            }
        });

        if (code == CODE_EDIT_CONTACT) bindViews();
        else clearViews();
    }


    private boolean contactIsValid() {
        if (firstName.getText() == null)
            return toastInvalidMessage(firstNameLayout, "First name cannot be empty");
        if (lastName.getText() == null)
            return toastInvalidMessage(lastNameLayout, "Last name cannot be empty");
        if (phoneNumber.getText() == null)
            return toastInvalidMessage(phoneNumberLayout, "Phone number cannot be empty");
        if (homeAddress.getText() == null)
            return toastInvalidMessage(homeAddressLayout, "Home address cannot be empty");
        return true;
    }

    private boolean toastInvalidMessage(TextInputLayout layout, String message) {
        if (layout != null) layout.setError(message);
        else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        return false;
    }

    private void bindViews() {
        contactId = getIntent().getLongExtra("contactId", -1);
        new bindContact(contactId).execute();
    }

    private Contact bindRequest() {
        Contact contact = new Contact();
        if (code == CODE_EDIT_CONTACT) contact.id = contactId;
        contact.firstName = firstName.getText().toString();
        contact.lastName = lastName.getText().toString();
        contact.phoneNumber = phoneNumber.getText().toString();
        contact.homeAddress = homeAddress.getText().toString();
        return contact;
    }

    private void clearViews() {
        firstName.setText("");
        lastName.setText("");
        phoneNumber.setText("");
        homeAddress.setText("");
    }

    @SuppressLint("StaticFieldLeak")
    class bindContact extends AsyncTask<Void, Void, Contact> {
        private final long contactId;

        bindContact(long contactId) {
            this.contactId = contactId;
        }

        @Override
        protected Contact doInBackground(Void... params) {
            return contactDao.getContact(contactId);
        }

        @Override
        protected void onPostExecute(Contact contact) {
            firstName.setText(contact.firstName);
            lastName.setText(contact.lastName);
            phoneNumber.setText(contact.phoneNumber);
            homeAddress.setText(contact.homeAddress);
        }
    }

    @SuppressLint("StaticFieldLeak")
    class saveContact extends AsyncTask<Void, Void, Void> {
        private final Contact contact;

        saveContact(Contact contact) {
            this.contact = contact;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (code == CODE_EDIT_CONTACT) contactDao.update(contact);
            else contactDao.insert(contact);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(AddEditContactActivity.this, "Contact saved", Toast.LENGTH_SHORT).show();
            AddEditContactActivity.this.finish();
        }
    }
}
