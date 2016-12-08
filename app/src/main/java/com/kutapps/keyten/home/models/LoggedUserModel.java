package com.kutapps.keyten.home.models;

import com.google.firebase.auth.FirebaseUser;

public class LoggedUserModel
{
    public String id;
    public String name;
    public String image;

    public LoggedUserModel()
    {

    }

    public LoggedUserModel(FirebaseUser user)
    {
        this.id = user.getUid();
        this.name = user.getDisplayName();
        if (user.getPhotoUrl() != null)
        {
            this.image = user.getPhotoUrl().toString();
        }
    }
}
