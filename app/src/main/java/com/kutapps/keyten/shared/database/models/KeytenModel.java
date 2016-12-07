package com.kutapps.keyten.shared.database.models;


import com.google.gson.annotations.JsonAdapter;
import com.kutapps.keyten.home.models.LoggedUserModel;
import com.kutapps.keyten.shared.adapters.gson.DateTimeTypeAdapter;

import org.joda.time.DateTime;

public class KeytenModel
{
    public boolean         value;
    @JsonAdapter(DateTimeTypeAdapter.class)
    public DateTime        date;
    public LoggedUserModel user;

    public KeytenModel()
    {
        //no-op
    }

    public KeytenModel(boolean value, LoggedUserModel user)
    {
        this.value = value;
        this.date = DateTime.now();
        this.user = user;
    }
}
