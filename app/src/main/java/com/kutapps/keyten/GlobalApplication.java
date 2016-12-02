package com.kutapps.keyten;


import android.app.Application;

import com.kutapps.keyten.shared.database.DatabaseHelper;

public class GlobalApplication extends Application
{
    DatabaseHelper databaseHelper;

    @Override
    public void onCreate()
    {
        super.onCreate();
        databaseHelper = new DatabaseHelper();
        databaseHelper.doSmth();
    }
}
