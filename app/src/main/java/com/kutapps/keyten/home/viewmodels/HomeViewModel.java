package com.kutapps.keyten.home.viewmodels;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.kutapps.keyten.R;
import com.kutapps.keyten.home.adapters.binding.ColorModel;
import com.kutapps.keyten.home.models.LoggedUserModel;
import com.kutapps.keyten.main.viewmodels.MainViewModel;
import com.kutapps.keyten.shared.constants.State;
import com.kutapps.keyten.shared.database.Storage;
import com.kutapps.keyten.shared.database.StorageRx;
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
    public final ObservableField<State> state;
    public final ObservableList<Ownership> recentOwners;

    public final ObservableField<ColorModel> backgroundColor;

    public final ObservableField<String> first = new ObservableField<>();
    public final ObservableField<String> second = new ObservableField<>();
    public final ObservableField<String> third = new ObservableField<>();

    private final StorageRx storage = new StorageRx();

    {
        state = new ObservableField<>(Init);
        backgroundColor = new ObservableField<>(ColorModel.res(R.color.notenLight));
        recentOwners = new ObservableArrayList<>();
    }

    public HomeViewModel(MainViewModel rootModel) {
        user = rootModel.user;

        initDatabase();
    }

    private void initDatabase() {
        storage.leaderboardObservable().subscribe(leaderboard -> {
            recentOwners.clear();
            recentOwners.addAll(leaderboard);
        });
        storage.ownershipObservable().subscribe(ownership -> {
            if (ownership != null) {
                if ((Objects.equals(ownership.getUser().id, user.get().id))) {
                    state.set(Mine);
                } else {
                    state.set(NotMine);
                }
            } else {
                state.set(NotMine);
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
