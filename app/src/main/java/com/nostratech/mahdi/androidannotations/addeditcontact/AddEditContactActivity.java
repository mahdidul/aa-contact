package com.nostratech.mahdi.androidannotations.addeditcontact;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.nostratech.mahdi.androidannotations.R;
import com.nostratech.mahdi.androidannotations.model.Contact;
import com.nostratech.mahdi.androidannotations.model.source.ContactDao;
import com.nostratech.mahdi.androidannotations.util.DbProvider;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_add_edit_contact)
public class AddEditContactActivity extends AppCompatActivity {

    private ContactDao contactDao;

    public static final int CODE_EDIT_CONTACT = 555;

    @ViewById
    TextInputLayout firstNameLayout;
    @ViewById
    TextInputEditText firstName;
    @ViewById
    TextInputLayout lastNameLayout;
    @ViewById
    TextInputEditText lastName;
    @ViewById
    TextInputLayout phoneNumberLayout;
    @ViewById
    TextInputEditText phoneNumber;
    @ViewById
    TextInputLayout homeAddressLayout;
    @ViewById
    TextInputEditText homeAddress;

    @Extra
    int code;
    @Extra
    long contactId;

    @Click
    void fab() {
        if (contactIsValid()) saveContact(bindRequest());
    }

    @AfterViews
    void init() {
        contactDao = new DbProvider().getDb(this).contactDao();
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

    @Background
    void bindViews() {
        Contact contact = contactDao.getContact(contactId);
        firstName.setText(contact.firstName);
        lastName.setText(contact.lastName);
        phoneNumber.setText(contact.phoneNumber);
        homeAddress.setText(contact.homeAddress);
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

    @Background
    void saveContact(Contact contact) {
        if (code == CODE_EDIT_CONTACT) contactDao.update(contact);
        else contactDao.insert(contact);
        showSaved();
    }

    @UiThread
    void showSaved() {
        Toast.makeText(AddEditContactActivity.this, "Contact saved", Toast.LENGTH_SHORT).show();
        AddEditContactActivity.this.finish();
    }
}
