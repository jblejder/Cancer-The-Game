package com.kutapps.keyten.home;

import android.databinding.ObservableField;

import com.google.firebase.database.DatabaseError;
import com.kutapps.keyten.KeytenApp;
import com.kutapps.keyten.shared.constants.State;
import com.kutapps.keyten.shared.database.DatabaseHelper;
import com.kutapps.keyten.shared.database.models.KeytenModel;

import org.joda.time.DateTime;

public class HomeViewModel
{
    private static final String                 TAG   = "HomeViewModel";
    private final        ObservableField<State> state = new ObservableField<>(State.Init);
    private DatabaseHelper helper;

    HomeViewModel()
    {
        helper = KeytenApp.getDatabaseHelper();
        helper.listenKeytenChange(new DatabaseHelper.DbListener<KeytenModel>()
        {
            @Override
            public void newValue(KeytenModel model)
            {
                if (model != null)
                {
                    state.set(model.value ? State.Keyten : State.Noten);
                }
                else
                {
                    state.set(State.Noten);
                }
            }

            @Override
            public void error(DatabaseError error)
            {

            }
        });
    }

    public ObservableField<State> getState()
    {
        return state;
    }

    public void setKeyten()
    {
        KeytenModel model = new KeytenModel(state.get() != State.Keyten, DateTime.now(), "JA");
        helper.setKeyten(model);
    }
}
