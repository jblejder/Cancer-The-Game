package com.kutapps.keyten.main.activities.callbacks;

import com.kutapps.keyten.main.viewmodels.MainViewModel;

public interface IMainActivityCallback
{
    void onClickLogout();

    MainViewModel getModel();

    void onLoginWithGoogle();
}
