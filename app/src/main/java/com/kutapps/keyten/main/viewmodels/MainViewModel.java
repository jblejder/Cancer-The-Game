package com.kutapps.keyten.main.viewmodels;

import android.databinding.ObservableField;

import com.kutapps.keyten.home.models.LoggedUserModel;

public class MainViewModel
{
    private static final String TAG = MainViewModel.class.getSimpleName();

    public final  ObservableField<LoggedUserModel> user;

    {
        user = new ObservableField<>();
    }
}
