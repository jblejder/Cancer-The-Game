package com.kutapps.keyten.shared.database;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseHelper
{
    private DatabaseReference reference;

    public DatabaseHelper()
    {
        reference = FirebaseDatabase.getInstance().getReference("keyten");
    }

    public void doSmth()
    {
        reference.setValue(true);
    }
}
