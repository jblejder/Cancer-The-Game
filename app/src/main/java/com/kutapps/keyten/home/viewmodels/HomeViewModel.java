package com.kutapps.keyten.home.viewmodels;

import android.databinding.ObservableField;
import android.util.Log;

import com.kutapps.keyten.R;
import com.kutapps.keyten.home.adapters.binding.ColorModel;
import com.kutapps.keyten.home.models.LoggedUserModel;
import com.kutapps.keyten.main.viewmodels.MainViewModel;
import com.kutapps.keyten.shared.constants.State;
import com.kutapps.keyten.shared.database.Storage;
import com.kutapps.keyten.shared.database.models.Leaderboard;
import com.kutapps.keyten.shared.database.models.Ownership;

import org.joda.time.DateTime;

import java.util.Objects;

import static com.kutapps.keyten.shared.constants.State.Error;
import static com.kutapps.keyten.shared.constants.State.Init;
import static com.kutapps.keyten.shared.constants.State.Mine;
import static com.kutapps.keyten.shared.constants.State.NotMine;

public class HomeViewModel {
    public final static String TAG = HomeViewModel.class.getSimpleName();

    public final ObservableField<LoggedUserModel> user;
    public final ObservableField<State>           state;

    public final ObservableField<ColorModel> backgroundColor;

    public final ObservableField<String> first = new ObservableField<>();
    public final ObservableField<String> second = new ObservableField<>();
    public final ObservableField<String> third = new ObservableField<>();

    private final Storage storage;


    {
        state = new ObservableField<>(Init);
        backgroundColor = new ObservableField<>(ColorModel.res(R.color.notenLight));
    }

    public HomeViewModel(MainViewModel rootModel) {
        storage = rootModel.getStorage();
        user = rootModel.user;

        initDatabase();
    }

    private void initDatabase() {
        storage.listenLeaderboardChange(new Storage.LeaderboardListener() {
            @Override
            public void newValue(Leaderboard model) {
                if(model.size() > 0) {
                    first.set(model.get(0).getUser().name);
                }
                if(model.size() > 1) {
                    first.set(model.get(1).getUser().name);
                }
                if(model.size() > 2) {
                    first.set(model.get(2).getUser().name);
                }
            }

            @Override
            public void error(Object error) {

            }
        });

        storage.listenOwnershipChange(new Storage.OwnershipListener() {
            @Override
            public void newValue(Ownership model) {
                if (model != null) {
                    if ((Objects.equals(model.getUser().id, user.get().id))) {
                        state.set(Mine);
                    } else {
                        state.set(NotMine);
                    }
                } else {
                    state.set(NotMine);
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
                newState = Mine;
                break;
            case Mine:
                newState = Mine;
                break;
            case Init:
                newState = Init;
                break;
            case Error:
            default:
                newState = Error;
                break;
        }

        if (state.get() != Mine && newState == Mine) {
            storage.addOwnership(new Ownership(DateTime.now(), user.get()));
        }
    }
}
