package com.kutapps.keyten.shared.database;

import static com.kutapps.keyten.shared.database.constants.DatabaseFields.OWNERSHIP;
import static com.kutapps.keyten.shared.helpers.Json.toJson;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.kutapps.keyten.shared.database.models.Leaderboard;
import com.kutapps.keyten.shared.database.models.Ownership;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class StorageRx implements IStorageRx {

    private static final String TAG = "StorageRx";

    private BehaviorSubject<Ownership>   ownershipSubject;
    private BehaviorSubject<Leaderboard> leaderboardSubject;

    private OwnershipDatabaseListener   ownershipDatabaseListener;
    private LeaderboardDatabaseListener leaderboardDatabaseListener;

    private FirebaseDatabase db;

    public StorageRx() {
        db = FirebaseDatabase.getInstance();
    }

    public Observable<Ownership> ownershipObservable() {
        if (ownershipSubject != null && !ownershipSubject.hasThrowable()) {
            return ownershipSubject;
        }
        ownershipSubject = BehaviorSubject.create();
        ownershipDatabaseListener = new OwnershipDatabaseListener(new OwnershipMapper(),
                ownershipSubject);
        createQueryWithLimit(1).addChildEventListener(ownershipDatabaseListener);
        return ownershipSubject;
    }

    public Observable<Leaderboard> leaderboardObservable() {
        if (leaderboardSubject != null && !leaderboardSubject.hasThrowable()) {
            return leaderboardSubject;
        }
        leaderboardSubject = BehaviorSubject.create();
        leaderboardDatabaseListener = new LeaderboardDatabaseListener(
                new LeaderboardMapper(new OwnershipMapper()), leaderboardSubject);
        createQueryWithLimit(20).addValueEventListener(leaderboardDatabaseListener);
        return leaderboardSubject;
    }

    private Query createQueryWithLimit(int limit) {
        return db.getReference(OWNERSHIP).limitToLast(limit);
    }

    public void addOwnership(Ownership ownership) {
        db.getReference(OWNERSHIP).push().setValue(toJson(ownership));
    }
}
