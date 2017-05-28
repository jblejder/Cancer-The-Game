package com.kutapps.keyten.shared.database.models;

import com.google.gson.annotations.JsonAdapter;
import com.kutapps.keyten.home.models.LoggedUserModel;
import com.kutapps.keyten.shared.adapters.gson.DateTimeTypeAdapter;

import org.joda.time.DateTime;

public class Ownership {
    @JsonAdapter(DateTimeTypeAdapter.class)
    private DateTime date;
    private LoggedUserModel user;

    public Ownership(DateTime date, LoggedUserModel user) {
        this.date = date;
        this.user = user;
    }

    public DateTime getDate() {
        return date;
    }

    public LoggedUserModel getUser() {
        return user;
    }
}
