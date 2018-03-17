package com.nostratech.mahdi.androidannotations.listcontact;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nostratech.mahdi.androidannotations.R;
import com.nostratech.mahdi.androidannotations.addeditcontact.AddEditContactActivity_;
import com.nostratech.mahdi.androidannotations.model.source.ContactDao;
import com.nostratech.mahdi.androidannotations.util.DbProvider;
import com.nostratech.mahdi.androidannotations.util.SimpleDividerItemDecoration;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_list_contact)
public class ListContactActivity extends AppCompatActivity {

    @ViewById
    RecyclerView recycler;

    private ContactDao contactDao;
    private ListContactAdapter adapter;

    @Click
    void fab() {
        AddEditContactActivity_.intent(this).start();
    }

    @AfterViews
    void init() {
        contactDao = new DbProvider().getDb(this).contactDao();
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(new SimpleDividerItemDecoration(this));
        initAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initAdapter();
    }

    @Background
    void initAdapter() {
        adapter = new ListContactAdapter(
                ListContactActivity.this, contactDao.getContacts());
        bindViews();
    }

    @UiThread
    void bindViews() {
        recycler.setAdapter(adapter);
    }
}
