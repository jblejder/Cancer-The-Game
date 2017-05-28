package com.kutapps.keyten.main.viewmodels;

import android.databinding.ObservableField;

import com.kutapps.keyten.home.models.LoggedUserModel;
import com.kutapps.keyten.shared.database.Storage;

public class MainViewModel {
    private static final String TAG = MainViewModel.class.getSimpleName();

    public final ObservableField<LoggedUserModel> user;
    private Storage storage;

    {
        user = new ObservableField<>();
    }

    public MainViewModel(Storage storage) {
        this.storage = storage;
    }

    public Storage getStorage() {
        return storage;
    }
}
