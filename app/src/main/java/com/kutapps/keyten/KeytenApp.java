package com.kutapps.keyten;


import android.app.Application;

import com.kutapps.keyten.shared.database.DatabaseHelper;

public class KeytenApp extends Application
{
    private static KeytenApp instance;

    private DatabaseHelper databaseHelper;

    @Override
    public void onCreate()
    {
        super.onCreate();

        instance = this;

        databaseHelper = new DatabaseHelper();
    }

    public static DatabaseHelper getDatabaseHelper()
    {
        return instance.databaseHelper;
    }
}
