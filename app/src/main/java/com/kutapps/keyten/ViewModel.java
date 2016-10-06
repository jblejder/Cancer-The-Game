package com.kutapps.keyten;

import android.databinding.ObservableField;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class ViewModel {

    private final ObservableField<State> state = new ObservableField<>(State.Init);
    private DatabaseReference reference;

    ViewModel() {
        reference = FirebaseDatabase.getInstance().getReference(Const.Date);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value != null) {
                    DateTime dateTime = DateTimeFormat.forPattern(Const.DateFormat)
                            .parseDateTime(value);
                    update(dateTime);
                } else {
                    state.set(State.Noten);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void update(DateTime date) {
        DateTime now = DateTime.now();
        int current = now.getDayOfYear();
        int dayOfYear = date.getDayOfYear();
        if (current == dayOfYear) {
            int hourOfDay = date.getHourOfDay();
            state.set(State.Keyten);
        }
    }

    public ObservableField<State> getState() {
        return state;
    }

    public void setKeyten() {
        reference.setValue(DateTime.now().toString(Const.DateFormat));
    }
}
