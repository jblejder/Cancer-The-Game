package com.kutapps.keyten.shared.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

public class OnSubscribeSingleValue<T> implements ObservableOnSubscribe<T> {

    private Query query;
    private Mapper<DataSnapshot, T> mapper;

    public OnSubscribeSingleValue(Query query, Mapper<DataSnapshot, T> mapper) {
        this.query = query;
        this.mapper = mapper;
    }

    @Override
    public void subscribe(@NonNull ObservableEmitter<T> e) throws Exception {
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                e.onNext(mapper.map(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                e.onError(new IllegalStateException(databaseError.getMessage()));
            }
        });
    }
}
