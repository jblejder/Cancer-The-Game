package com.kutapps.keyten.home;

import android.databinding.ObservableField;

import com.google.firebase.database.DatabaseReference;
import com.kutapps.keyten.home.models.LoggedUserModel;
import com.kutapps.keyten.shared.constants.State;

import org.joda.time.DateTime;

public class HomeViewModel
{
    private final ObservableField<State> state = new ObservableField<>(State.Init);
    public final ObservableField<LoggedUserModel> user;

    private DatabaseReference reference;
    
    {
        user = new ObservableField<>();
    }


    HomeViewModel()
    {
        //        reference = FirebaseDatabase.getInstance().getReference(Const.Date);
        //        reference.addValueEventListener(new ValueEventListener() {
        //            @Override
        //            public void onDataChange(DataSnapshot dataSnapshot) {
        //                String value = dataSnapshot.getValue(String.class);
        //                if (value != null) {
        //                    DateTime dateTime = DateTimeFormat.forPattern(Const.DateFormat)
        //                            .parseDateTime(value);
        //                    update(dateTime);
        //                } else {
        //                    state.set(State.Noten);
        //                }
        //            }
        //
        //            @Override
        //            public void onCancelled(DatabaseError databaseError) {
        //
        //            }
        //        });
    }

    private void update(DateTime date)
    {
        //        DateTime now = DateTime.now();
        //        int current = now.getDayOfYear();
        //        int dayOfYear = date.getDayOfYear();
        //        if (current == dayOfYear) {
        //            int hourOfDay = date.getHourOfDay();
        //            state.set(State.Keyten);
        //        }
    }

    public ObservableField<State> getState()
    {
        return state;
    }

    public void setKeyten()
    {
        //        reference.setValue(DateTime.now().toString(Const.DateFormat));
    }
}
