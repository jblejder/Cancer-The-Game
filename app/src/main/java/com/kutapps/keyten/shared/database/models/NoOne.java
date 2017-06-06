package com.kutapps.keyten.shared.database.models;

import org.joda.time.DateTime;

public class NoOne extends Ownership {

    public NoOne() {
        super(DateTime.now(), null);
    }
}
