package com.kutapps.keyten.shared.database.models;


import com.google.gson.annotations.JsonAdapter;
import com.kutapps.keyten.home.models.LoggedUserModel;
import com.kutapps.keyten.shared.adapters.gson.DateTimeTypeAdapter;

import org.joda.time.DateTime;

@Deprecated
public class KeytenModel {
    @JsonAdapter(DateTimeTypeAdapter.class)
    public DateTime        date;
    public LoggedUserModel user;

    @SuppressWarnings("unused")
    public KeytenModel() {
        //no-op
    }

    public KeytenModel(LoggedUserModel user) {
        this.date = DateTime.now();
        this.user = user;
    }
}
