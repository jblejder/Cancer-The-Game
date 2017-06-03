package com.kutapps.keyten.main.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kutapps.keyten.R;
import com.kutapps.keyten.databinding.ActivityMainBinding;
import com.kutapps.keyten.home.fragments.HomeFragment;
import com.kutapps.keyten.home.models.LoggedUserModel;
import com.kutapps.keyten.login.fragments.LoginFragment;
import com.kutapps.keyten.main.activities.callbacks.IMainActivityCallback;
import com.kutapps.keyten.main.viewmodels.MainActivityViewModel;
import com.kutapps.keyten.shared.activities.AccountActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AccountActivity implements IMainActivityCallback ,
        HasSupportFragmentInjector{
    private static final int    RC_SIGN_IN = 665;
    private static final String TAG        = MainActivity.class.getSimpleName();
    @Inject
    MainActivityViewModel model;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setModel(model);
    }

    @Override
    protected void onUserLogged(FirebaseUser user) {
        model.user.set(new LoggedUserModel(user));
        if (!isFinishing()) {
            loadFragment(HomeFragment.newInstance(), false);
            Toast.makeText(this, user.getDisplayName(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onUserLoggedOut() {
        model.user.set(null);
        if (!isFinishing()) {
            loadFragment(LoginFragment.newInstance(), false);
        }
    }

    @Override
    public void onClickLogout() {
        FirebaseAuth.getInstance().signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
    }

    @Override
    public MainActivityViewModel getModel() {
        return model;
    }

    @Override
    public void onLoginWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected int getFragmentLayoutResource() {
        return binding.container.getId();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
        }
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }
}
