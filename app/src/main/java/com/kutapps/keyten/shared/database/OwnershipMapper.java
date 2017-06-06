package com.kutapps.keyten.shared.database;

import com.google.firebase.database.DataSnapshot;
import com.kutapps.keyten.shared.database.models.Ownership;
import com.kutapps.keyten.shared.helpers.Json;

public class OwnershipMapper implements Mapper<DataSnapshot, Ownership> {
    @Override
    public Ownership map(DataSnapshot v) {
        Object rawValue = v.getValue();
        return Json.fromJson(((String) rawValue), Ownership.class);
    }
}
