package com.kutapps.keyten.home.viewmodels;

import android.databinding.ObservableField;

import com.kutapps.keyten.R;
import com.kutapps.keyten.home.adapters.binding.ColorModel;
import com.kutapps.keyten.home.models.LoggedUserModel;
import com.kutapps.keyten.main.viewmodels.MainViewModel;
import com.kutapps.keyten.shared.constants.State;
import com.kutapps.keyten.shared.database.Storage;
import com.kutapps.keyten.shared.database.models.Ownership;

import org.joda.time.DateTime;

import java.util.Objects;

public class HomeViewModel {
    public final static String TAG = HomeViewModel.class.getSimpleName();

    public final ObservableField<LoggedUserModel> user;
    public final ObservableField<State>           state;

    public final ObservableField<ColorModel> backgroundColor;

    private final Storage storage;


    {
        state = new ObservableField<>(State.Init);
        backgroundColor = new ObservableField<>(ColorModel.res(R.color.notenLight));
    }

    public HomeViewModel(MainViewModel rootModel) {
        storage = rootModel.getStorage();
        user = rootModel.user;

        initDatabase();
    }

    private void initDatabase() {
        storage.listenOwnershipChange(new Storage.OwnershipListener() {
            @Override
            public void newValue(Ownership model) {
                if (model != null) {
                    if ((Objects.equals(model.getUser().id, user.get().id))) {
                        state.set(State.Mine);
                    } else {
                        state.set(State.NotMine);
                    }
                } else {
                    state.set(State.Error);
                }
            }

            @Override
            public void error(Object error) {
                //
            }
        });
    }

    public void toggleState() {
        State newState;
        switch (state.get()) {
            case NotMine:
                newState = State.Mine;
                break;
            case Mine:
                newState = State.Mine;
                break;
            case Init:
                newState = State.Init;
                break;
            case Error:
            default:
                newState = State.Error;
                break;
        }

        if (state.get() != State.Mine && newState == State.Mine) {
            storage.addOwnership(new Ownership(DateTime.now(), user.get()));
        }
    }

}

