package com.nostratech.mahdi.androidannotations.listcontact;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nostratech.mahdi.androidannotations.R;
import com.nostratech.mahdi.androidannotations.addeditcontact.AddEditContactActivity;
import com.nostratech.mahdi.androidannotations.util.DbProvider;
import com.nostratech.mahdi.androidannotations.model.Contact;
import com.nostratech.mahdi.androidannotations.model.source.ContactDao;
import com.nostratech.mahdi.androidannotations.util.SimpleDividerItemDecoration;

import java.util.List;

public class ListContactActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContactDao contactDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contact);

        contactDao = new DbProvider().getDb(this).contactDao();
        recyclerView = findViewById(R.id.recycler);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListContactActivity.this, AddEditContactActivity.class));
            }
        });
    }

    private void bindViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        new bindContacts().execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindViews();
    }

    @SuppressLint("StaticFieldLeak")
    class bindContacts extends AsyncTask<Void, Void, List<Contact>> {

        @Override
        protected List<Contact> doInBackground(Void... params) {
            return contactDao.getContacts();
        }

        @Override
        protected void onPostExecute(List<Contact> contact) {
            ListContactAdapter adapter = new ListContactAdapter(ListContactActivity.this, contact);
            recyclerView.setAdapter(adapter);
        }
    }
}
