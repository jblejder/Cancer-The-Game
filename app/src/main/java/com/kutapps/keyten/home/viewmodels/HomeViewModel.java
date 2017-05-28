package com.kutapps.keyten.home.viewmodels;

import android.databinding.ObservableField;
import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.kutapps.keyten.R;
import com.kutapps.keyten.home.adapters.binding.ColorModel;
import com.kutapps.keyten.home.models.LoggedUserModel;
import com.kutapps.keyten.main.viewmodels.MainViewModel;
import com.kutapps.keyten.shared.constants.State;
import com.kutapps.keyten.shared.database.DatabaseHelper;
import com.kutapps.keyten.shared.database.models.KeytenModel;

public class HomeViewModel {
    public final static String TAG = HomeViewModel.class.getSimpleName();

    public final ObservableField<LoggedUserModel> user;
    public final ObservableField<State>           state;
    public final ObservableField<KeytenModel>     keytenModel;

    public final ObservableField<ColorModel> backgroundColor;

    private final DatabaseHelper databaseHelper;
    //private final MessagesHelper messagesHelper;


    {
        state = new ObservableField<>(State.Init);
        backgroundColor = new ObservableField<>(ColorModel.res(R.color.notenLight));
        keytenModel = new ObservableField<>();
    }

    public HomeViewModel(MainViewModel rootModel, DatabaseHelper databaseHelper) {
        user = rootModel.user;
        this.databaseHelper = databaseHelper;
        //messagesHelper = KeytenApp.getMessagesHelper();

        //messagesHelper.registerForKeytens();
        initDatabase();
    }

    private void initDatabase() {
        databaseHelper.listenKeytenChange(new DatabaseHelper.DbListener<KeytenModel>() {
            @Override
            public void newValue(KeytenModel model) {
                if (model != null) {
                    keytenModel.set(model);
                    state.set(model.value ? State.Mine : State.NotMine);

                    ColorModel colorModel;
//                    if (state.get() == State.Mine) {
//                        colorModel = ColorModel.color(ColorGenerator.getColor(model.user.name));
//                    } else {
//                        colorModel = ColorModel.res(state.get().getColorLight());
//                    }
//                    backgroundColor.set(colorModel);
                } else {
                    state.set(State.NotMine);
                }
            }

            @Override
            public void error(DatabaseError error) {
                state.set(State.Error);
                Log.e(TAG, error.getMessage() + ": " + error.getDetails());
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
                newState = State.NotMine;
                break;
            case Init:
                newState = State.Init;
                break;
            case Error:
            default:
                newState = State.Error;
        }

        state.set(newState);
        KeytenModel model = new KeytenModel(newState == State.Mine, user.get());


        //databaseHelper.setKeyten(model);
    }

}

