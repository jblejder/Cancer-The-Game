package com.kutapps.keyten;


import android.app.Application;

import com.kutapps.keyten.shared.database.Storage;

public class KeytenApp extends Application {

    private Storage storage;

    @Override
    public void onCreate() {
        super.onCreate();

        storage = new Storage();
    }

    public Storage getStorage() {
        return storage;
    }
}
