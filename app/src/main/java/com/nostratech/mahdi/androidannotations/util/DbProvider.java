package com.nostratech.mahdi.androidannotations.util;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.nostratech.mahdi.androidannotations.model.source.ContactDatabase;

public class DbProvider {
    public ContactDatabase getDb(Context context) {
        return Room.databaseBuilder(context, ContactDatabase.class, "contact.db")
                .fallbackToDestructiveMigration()
                .build();
    }
}
