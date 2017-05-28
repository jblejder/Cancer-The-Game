package com.kutapps.keyten.home.viewmodels;

import android.databinding.ObservableField;
import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.kutapps.keyten.home.adapters.binding.ColorModel;
import com.kutapps.keyten.home.models.LoggedUserModel;
import com.kutapps.keyten.main.viewmodels.MainViewModel;
import com.kutapps.keyten.shared.constants.State;
import com.kutapps.keyten.shared.database.Storage;
import com.kutapps.keyten.shared.database.models.KeytenModel;
import com.kutapps.keyten.shared.database.models.Ownership;
import com.kutapps.keyten.shared.helpers.ColorGenerator;

import org.joda.time.DateTime;

public class HomeViewModel {
    public final static String TAG = HomeViewModel.class.getSimpleName();

    public final ObservableField<LoggedUserModel> user;
    public final ObservableField<State> state;
    public final ObservableField<KeytenModel> keytenModel;

    public final ObservableField<ColorModel> backgroundColor;

    private final Storage storage;


    {
        state = new ObservableField<>(State.Init);
        backgroundColor = new ObservableField<>(ColorModel.res(state.get().getColorLight()));
        keytenModel = new ObservableField<>();
    }

    public HomeViewModel(MainViewModel rootModel) {
        storage = rootModel.getStorage();
        user = rootModel.user;

        initDatabase();
    }

    private void initDatabase() {
        storage.listenOwnershipChange(new Storage.OwnershipListener<KeytenModel>() {
            @Override
            public void newValue(KeytenModel model) {
                if (model != null) {
                    keytenModel.set(model);
//                    state.set(model.value ? State.Keyten : State.Noten);

                    ColorModel colorModel;
                    if (state.get() == State.Keyten) {
                        colorModel = ColorModel.color(ColorGenerator.getColor(model.user.name));
                    } else {
                        colorModel = ColorModel.res(state.get().getColorLight());
                    }
                    backgroundColor.set(colorModel);
                } else {
                    state.set(State.Noten);
                }
            }

            @Override
            public void error(DatabaseError error) {
                Log.e(TAG, error.getMessage() + ": " + error.getDetails());
            }
        });
    }

    public void setKeyten() {
        State newState;
        switch (state.get()) {
            case Noten:
                newState = State.Keyten;
                break;
            case Keyten:
            case Init:
            default:
                newState = State.Noten;
        }

        storage.addOwnership(new Ownership(DateTime.now(), user.get()));
    }

}

