package com.kutapps.keyten.shared.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kutapps.keyten.R;

public abstract class AccountActivity extends AppCompatActivity implements GoogleApiClient
        .OnConnectionFailedListener
{
    private static final String TAG = AccountActivity.class.getSimpleName();

    private   FirebaseAuth.AuthStateListener mAuthListener;
    protected FirebaseAuth                   mAuth;
    protected GoogleApiClient                mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null)
            {
                onUserLogged(user);
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            }
            else
            {
                onUserLoggedOut();
                Log.d(TAG, "onAuthStateChanged:signed_out");
            }
        };
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions
                .DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi
                (Auth.GOOGLE_SIGN_IN_API, gso).build();
    }

    protected abstract void onUserLogged(FirebaseUser user);

    protected abstract void onUserLoggedOut();


    @Override
    public void onStart()
    {
        mAuth.addAuthStateListener(mAuthListener);
        super.onStart();
    }

    @Override
    public void onStop()
    {
        if (mAuthListener != null)
        {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        super.onStop();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        Log.e(TAG, "onConnectionFailed" + connectionResult);
    }
}
