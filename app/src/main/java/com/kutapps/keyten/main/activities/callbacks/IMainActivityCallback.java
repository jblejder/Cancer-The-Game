package com.kutapps.keyten.main.activities.callbacks;

import com.kutapps.keyten.main.viewmodels.MainActivityViewModel;

public interface IMainActivityCallback {
    void onClickLogout();

    MainActivityViewModel getModel();

    void onLoginWithGoogle();
}
