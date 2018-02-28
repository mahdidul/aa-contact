package com.nostratech.mahdi.androidannotations.util;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.nostratech.mahdi.androidannotations.model.source.ContactDatabase;

/**
 * Created by Mahdi on 01/03/18.
 * Part of AndroidAnnotations
 */

public class DbProvider {
    public ContactDatabase getDb(Context context) {
        return Room.databaseBuilder(context, ContactDatabase.class, "contact.db")
                .fallbackToDestructiveMigration()
                .build();
    }
}
