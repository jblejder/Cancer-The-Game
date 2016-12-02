package com.kutapps.keyten.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kutapps.keyten.R;
import com.kutapps.keyten.databinding.ActivityLoginBinding;
import com.kutapps.keyten.home.MainActivity;
import com.kutapps.keyten.shared.activities.AccountActivity;

public class LoginActivity extends AccountActivity
{
    private static final int    RC_SIGN_IN = 665;
    private static final String TAG        = LoginActivity.class.getSimpleName();

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        binding.signInButton.setSize(SignInButton.SIZE_WIDE);
        binding.signInButton.setColorScheme(SignInButton.COLOR_DARK);
        binding.signInButton.setOnClickListener(this::onClickLogin);
    }

    private void startNextActivity()
    {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct)
    {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if (!task.isSuccessful())
            {
                Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT)
                        .show();
            }
            else
            {
                startNextActivity();
            }
        });
    }

    public void onClickLogin(View v)
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess())
            {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
        }
    }

    @Override
    protected void onUserLogged(FirebaseUser user)
    {
        startNextActivity();
    }

    @Override
    protected void onUserLoggedOut()
    {

    }
}

