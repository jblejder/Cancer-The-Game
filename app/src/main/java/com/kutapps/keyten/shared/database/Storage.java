package com.kutapps.keyten.shared.database;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.kutapps.keyten.shared.database.models.Ownership;
import com.kutapps.keyten.shared.helpers.Json;

import static com.kutapps.keyten.shared.database.constants.DatabaseFields.OWNERSHIP;
import static com.kutapps.keyten.shared.helpers.Json.toJson;

public class Storage {
    private FirebaseDatabase db;

    public Storage() {
        db = FirebaseDatabase.getInstance();
    }

    public void addOwnership(Ownership model) {
        db.getReference(OWNERSHIP).push().setValue(toJson(model));
    }

    public void listenOwnershipChange(final OwnershipListener listener) {
        db.getReference(OWNERSHIP).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                try {
                    listener.newValue(parseModel(dataSnapshot));
                } catch (ParseErrorException ex) {
                    listener.error(ex);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.error(databaseError);
            }
        });
    }

    private Ownership parseModel(DataSnapshot dataSnapshot) throws ParseErrorException {
        Object rawValue = dataSnapshot.getValue();
        if (rawValue instanceof String) {
            return Json.fromJson(((String) rawValue), Ownership.class);
        } else {
            throw new ParseErrorException();
        }
    }

    public interface OwnershipListener {
        void newValue(Ownership model);

        void error(Object error);
    }
}
