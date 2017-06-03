package com.kutapps.keyten.home.viewmodels;

import static com.kutapps.keyten.shared.constants.State.Error;
import static com.kutapps.keyten.shared.constants.State.Init;
import static com.kutapps.keyten.shared.constants.State.Mine;
import static com.kutapps.keyten.shared.constants.State.NotMine;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.kutapps.keyten.R;
import com.kutapps.keyten.home.adapters.binding.ColorModel;
import com.kutapps.keyten.home.models.LoggedUserModel;
import com.kutapps.keyten.main.viewmodels.MainActivityViewModel;
import com.kutapps.keyten.shared.constants.State;
import com.kutapps.keyten.shared.database.IStorageRx;
import com.kutapps.keyten.shared.database.models.Ownership;

import org.joda.time.DateTime;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class HomeFragmentViewModel {
    public final static String TAG = HomeFragmentViewModel.class.getSimpleName();

    public final ObservableField<LoggedUserModel> user;
    public final ObservableField<State>           state;
    public final ObservableList<Ownership>        recentOwners;

    public final  ObservableField<ColorModel> backgroundColor;
    private final IStorageRx                  storage;


    {
        state = new ObservableField<>(Init);
        backgroundColor = new ObservableField<>(ColorModel.res(R.color.notenLight));
        recentOwners = new ObservableArrayList<>();
    }

    @Inject
    public HomeFragmentViewModel(MainActivityViewModel rootModel, IStorageRx storage) {
        user = rootModel.user;
        this.storage = storage;

        initDatabase();
    }

    private void initDatabase() {
        storage.leaderboardObservable().observeOn(AndroidSchedulers.mainThread()).subscribe
                (leaderboard -> {
                    recentOwners.clear();
                    recentOwners.addAll(leaderboard);
                });
        storage.ownershipObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(
                ownership -> {
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
