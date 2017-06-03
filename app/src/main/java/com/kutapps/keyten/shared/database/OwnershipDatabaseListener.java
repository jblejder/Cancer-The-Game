package com.kutapps.keyten.shared.database;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.kutapps.keyten.shared.database.models.Ownership;

import io.reactivex.Observer;

public class OwnershipDatabaseListener implements ChildEventListener {

    private Mapper<DataSnapshot, Ownership> mapper;
    private Observer<Ownership>             observer;

    public OwnershipDatabaseListener(Mapper<DataSnapshot, Ownership> mapper,
            Observer<Ownership> observer) {
        this.mapper = mapper;
        this.observer = observer;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        observer.onNext(mapper.map(dataSnapshot));
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        //no-op
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        //no-op
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        //no-op
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        observer.onError(new DatabaseErrorWrapper(databaseError));
    }
}
