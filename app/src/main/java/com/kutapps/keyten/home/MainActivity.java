package com.kutapps.keyten.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kutapps.keyten.R;
import com.kutapps.keyten.databinding.ActivityMainBinding;
import com.kutapps.keyten.home.dialogs.UserDialogFragment;
import com.kutapps.keyten.home.dialogs.callbacks.IUserDialogCallback;
import com.kutapps.keyten.home.models.LoggedUserModel;
import com.kutapps.keyten.login.LoginActivity;
import com.kutapps.keyten.shared.activities.AccountActivity;

public class MainActivity extends AccountActivity implements IUserDialogCallback
{
    private static final String DIALOG_TAG = "dialogtag";
    private static final String TAG        = MainActivity.class.getSimpleName();
    HomeViewModel       viewModel;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewModel = new HomeViewModel();
        binding.setModel(viewModel);
    }

    @Override
    protected void onUserLogged(FirebaseUser user)
    {
        viewModel.user.set(new LoggedUserModel(user));
        Toast.makeText(this, user.getDisplayName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onUserLoggedOut()
    {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void onClickUser(View view)
    {
        new UserDialogFragment().show(getSupportFragmentManager(), DIALOG_TAG);
    }

    @Override
    public void onClickLogout()
    {
        FirebaseAuth.getInstance().signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
    }
}
