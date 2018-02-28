package com.nostratech.mahdi.androidannotations.model.source;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nostratech.mahdi.androidannotations.model.Contact;

import java.util.List;

/**
 * Created by Mahdi on 01/03/18.
 * Part of AndroidAnnotations
 */

@Dao
public abstract class ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(Contact contact);

    @Update
    public abstract void update(Contact contact);

    @Query("SELECT * FROM contact WHERE id=:id")
    public abstract Contact getContact(long id);

    @Query("SELECT * FROM contact")
    public abstract List<Contact> getContacts();
}
