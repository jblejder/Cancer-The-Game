package com.kutapps.keyten.shared.database.models;


import com.google.gson.annotations.JsonAdapter;
import com.kutapps.keyten.shared.adapters.gson.DateTimeTypeAdapter;

import org.joda.time.DateTime;

public class KeytenModel
{
    public boolean  value;
    @JsonAdapter(DateTimeTypeAdapter.class)
    public DateTime date;
    public String   user; // TODO: (jb-02.12.2016) user type can be changed

    public KeytenModel()
    {
        //no-op
    }

    public KeytenModel(boolean value, DateTime date, String user)
    {
        this.value = value;
        this.date = date;
        this.user = user;
    }
}
