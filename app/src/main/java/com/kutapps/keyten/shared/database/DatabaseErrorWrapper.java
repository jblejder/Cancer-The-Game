package com.kutapps.keyten.shared.database;

import com.google.firebase.database.DatabaseError;

public class DatabaseErrorWrapper extends Throwable {
    private DatabaseError databaseError;

    public DatabaseErrorWrapper(DatabaseError databaseError) {
        this.databaseError = databaseError;
    }

    public DatabaseError getDatabaseError() {
        return databaseError;
    }
}
