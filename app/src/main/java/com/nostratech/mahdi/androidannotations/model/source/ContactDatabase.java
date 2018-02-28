package com.nostratech.mahdi.androidannotations.model.source;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.nostratech.mahdi.androidannotations.model.Contact;

/**
 * Created by Mahdi on 01/03/18.
 * Part of AndroidAnnotations
 */

@Database(version = 1, entities = {Contact.class})
public abstract class ContactDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();
}
