package com.kutapps.keyten.shared.database;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.kutapps.keyten.shared.database.models.Leaderboard;
import com.kutapps.keyten.shared.database.models.Ownership;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

import static com.kutapps.keyten.shared.database.constants.DatabaseFields.OWNERSHIP;

public class StorageRx {

    private static final String TAG = "StorageRx";

    private BehaviorSubject<Ownership> ownershipSubject;
    private BehaviorSubject<Leaderboard> leaderboardSubject;

    private OwnershipDatabaseListener ownershipDatabaseListener;
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
        ownershipDatabaseListener = new OwnershipDatabaseListener(new OwnershipMapper(), ownershipSubject);
        createQueryWithLimit(1).addChildEventListener(ownershipDatabaseListener);
        return ownershipSubject;
    }

    public Observable<Leaderboard> leaderboardObservable() {
        if (leaderboardSubject != null && !leaderboardSubject.hasThrowable()) {
            return leaderboardSubject;
        }
        leaderboardSubject = BehaviorSubject.create();
        leaderboardDatabaseListener = new LeaderboardDatabaseListener(new LeaderboardMapper(new OwnershipMapper()), leaderboardSubject);
        createQueryWithLimit(20).addValueEventListener(leaderboardDatabaseListener);
        return leaderboardSubject;
    }

    private Query createQueryWithLimit(int limit) {
        return db.getReference(OWNERSHIP).limitToLast(limit);
    }
}
