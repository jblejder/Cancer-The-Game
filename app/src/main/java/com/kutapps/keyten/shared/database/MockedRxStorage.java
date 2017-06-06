package com.kutapps.keyten.shared.database;

import com.annimon.stream.Stream;
import com.kutapps.keyten.home.models.LoggedUserModel;
import com.kutapps.keyten.shared.database.models.Leaderboard;
import com.kutapps.keyten.shared.database.models.Ownership;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class MockedRxStorage implements IStorageRx {

    private final Map<String, LoggedUserModel> users                 = new HashMap<>();
    private       int                          ownershipCounter      = 0;
    private final PublishSubject<Ownership>    ownershipObservable   = PublishSubject.create();
    private final PublishSubject<Leaderboard>  leaderboardObservable = PublishSubject.create();

    {
        LoggedUserModel mateusz = new LoggedUserModel();
        mateusz.id = "cycki";
        mateusz.image =
                "https://lh3.googleusercontent"
                        + ".com/-uGXV4IoKMXo/AAAAAAAAAAI/AAAAAAAAB54/QlFdoACYfyY/s60-p-rw-no"
                        + "/photo.jpg";
        mateusz.name = "Wielkie Cycki";

        LoggedUserModel kuba = new LoggedUserModel();
        kuba.id = "kutapps";
        kuba.image =
                "https://lh3.googleusercontent"
                        + ".com/-nbK3xUC78mw/AAAAAAAAAAI/AAAAAAAAAI0/_CbYAh55ciw/s60-p-rw-no"
                        + "/photo.jpg";
        kuba.name = "Jakub Bajer";


        LoggedUserModel patryk = new LoggedUserModel();
        patryk.id = "pmieszal";
        patryk.image =
                "https://lh3.googleusercontent"
                        + ".com/-UTUdInSMimo/AAAAAAAAAAI/AAAAAAAALbI/FIP8QQfmKro/s60-p-rw-no"
                        + "/photo.jpg";
        patryk.name = "Pan Patryk";


        LoggedUserModel piter = new LoggedUserModel();
        piter.id = "piter";
        piter.image =
                "https://lh3.googleusercontent"
                        + ".com/-DVbeM7mn6uo/AAAAAAAAAAI/AAAAAAAAAFU/hEdvlfKibvw/s36-p-k-rw-no"
                        + "/photo.jpg";
        piter.name = "piter";


        LoggedUserModel kubapi = new LoggedUserModel();
        kubapi.id = "kubapi";
        kubapi.image =
                "https://lh3.googleusercontent"
                        + ".com/-6K2o_iM4pXU/AAAAAAAAAAI/AAAAAAAAAsU/s_x3z3B3eCs/s60-p-rw-no"
                        + "/photo.jpg";
        kubapi.name = "Kubapi, ale nie mowimy o pazzingu";

        users.put(mateusz.id, mateusz);
        users.put(kuba.id, kuba);
        users.put(patryk.id, patryk);
        users.put(kubapi.id, kubapi);
        users.put(piter.id, piter);

        Observable.interval(2, TimeUnit.SECONDS).subscribe(aLong -> {
            ownershipObservable.onNext(createOwnership(aLong));
            leaderboardObservable.onNext(createLeaderboards());
        });
    }

    @Override
    public Observable<Leaderboard> leaderboardObservable() {
        return leaderboardObservable;
    }

    @Override
    public Observable<Ownership> ownershipObservable() {

        return ownershipObservable;
    }

    @Override
    public void addOwnership(Ownership ownership) {
        users.put(ownership.getUserId(), ownership.getUser());
        leaderboardObservable.onNext(createLeaderboards());
        ownershipObservable.onNext(ownership);
    }

    private Leaderboard createLeaderboards() {
        List<LoggedUserModel> loggedUserModels = new ArrayList<>(users.values());
        Collections.shuffle(loggedUserModels);

        return new Leaderboard(Stream.of(loggedUserModels).indexed().map(
                loggedUserModelIntPair -> new Ownership(DateTime.now().minusMinutes
                        (loggedUserModelIntPair.getFirst()),
                        loggedUserModelIntPair.getSecond())).toList());
    }

    private Ownership createOwnership(Long aLong) {
        return new Ownership(DateTime.now().withMillis(aLong), new ArrayList<>(users.values()).get(
                ownershipCounter++ % users.size()));
    }
}
