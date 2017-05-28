package com.kutapps.keyten;


import android.app.Application;

import com.kutapps.keyten.shared.MessagesHelper;
import com.kutapps.keyten.shared.database.DatabaseHelper;

public class KeytenApp extends Application {
    private DatabaseHelper databaseHelper;
    private MessagesHelper messagesHelper;

    @Override
    public void onCreate() {
        super.onCreate();


        messagesHelper = new MessagesHelper();
        databaseHelper = new DatabaseHelper();
    }

    public DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

    public MessagesHelper getMessagesHelper() {
        return messagesHelper;
    }
}
