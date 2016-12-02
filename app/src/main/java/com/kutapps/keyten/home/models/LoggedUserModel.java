package com.kutapps.keyten.home.models;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;

public class LoggedUserModel
{
    public final String name;
    public final Uri    image;

    public LoggedUserModel(FirebaseUser user)
    {
        this.name = user.getDisplayName();
        this.image = user.getPhotoUrl();
    }
}
