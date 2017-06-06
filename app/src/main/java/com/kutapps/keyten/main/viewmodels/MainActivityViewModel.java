package com.kutapps.keyten.main.viewmodels;

import android.databinding.ObservableField;

import com.kutapps.keyten.home.models.LoggedUserModel;
import com.kutapps.keyten.main.di.scopes.MainActivityScope;
import com.kutapps.keyten.shared.database.IStorageRx;

import javax.inject.Inject;

@MainActivityScope
public class MainActivityViewModel {
    private static final String TAG = MainActivityViewModel.class.getSimpleName();

    public final ObservableField<LoggedUserModel> user;
    private      IStorageRx                       storage;

    {
        user = new ObservableField<>();
    }

    @Inject
    public MainActivityViewModel(IStorageRx storage) {
        this.storage = storage;
    }

    public IStorageRx getStorage() {
        return storage;
    }
}
