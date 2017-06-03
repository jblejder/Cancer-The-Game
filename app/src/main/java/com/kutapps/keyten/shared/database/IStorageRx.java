package com.kutapps.keyten.shared.database;

import com.kutapps.keyten.shared.database.models.Leaderboard;
import com.kutapps.keyten.shared.database.models.Ownership;

import io.reactivex.Observable;

public interface IStorageRx {
    Observable<Leaderboard> leaderboardObservable();

    Observable<Ownership> ownershipObservable();

    void addOwnership(Ownership ownership);
}
