package com.kutapps.keyten.shared.database;


import com.google.firebase.database.FirebaseDatabase;

public class DatabaseHelper
{
    private FirebaseDatabase database;

    public DatabaseHelper()
    {
        database = FirebaseDatabase.getInstance();
    }
}
