package com.kutapps.keyten.shared.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.kutapps.keyten.shared.database.models.Leaderboard;

import io.reactivex.Observer;

public class LeaderboardDatabaseListener implements ValueEventListener {

    private LeaderboardMapper leaderboardMapper;
    private Observer<Leaderboard> observer;

    public LeaderboardDatabaseListener(LeaderboardMapper leaderboardMapper, Observer<Leaderboard> observer) {
        this.leaderboardMapper = leaderboardMapper;
        this.observer = observer;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        observer.onNext(leaderboardMapper.map(dataSnapshot));
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        observer.onError(new DatabaseErrorWrapper(databaseError));
    }
}
