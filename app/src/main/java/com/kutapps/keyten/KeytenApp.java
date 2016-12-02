package com.kutapps.keyten;


import android.app.Application;

import com.kutapps.keyten.shared.MessagesHelper;
import com.kutapps.keyten.shared.database.DatabaseHelper;

public class KeytenApp extends Application
{
    private static KeytenApp instance;

    private DatabaseHelper databaseHelper;
    private MessagesHelper messagesHelper;

    @Override
    public void onCreate()
    {
        super.onCreate();

        instance = this;

        messagesHelper = new MessagesHelper();
        databaseHelper = new DatabaseHelper();
    }

    public static DatabaseHelper getDatabaseHelper()
    {
        return instance.databaseHelper;
    }

    public static MessagesHelper getMessagesHelper()
    {
        return instance.messagesHelper;
    }
}
