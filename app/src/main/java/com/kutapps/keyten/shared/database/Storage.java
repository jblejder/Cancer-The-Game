package com.kutapps.keyten.shared.database;


import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kutapps.keyten.shared.database.models.Leaderboard;
import com.kutapps.keyten.shared.database.models.Ownership;
import com.kutapps.keyten.shared.helpers.Json;

import java.util.List;

import static com.kutapps.keyten.shared.database.constants.DatabaseFields.OWNERSHIP;
import static com.kutapps.keyten.shared.helpers.Json.toJson;

public class Storage {
    private FirebaseDatabase db;

    public Storage() {
        db = FirebaseDatabase.getInstance();
    }

    public void addOwnership(Ownership model) {
        db.getReference(OWNERSHIP).push().setValue(toJson(model));
    }

    public void listenLeaderboardChange(final LeaderboardListener listener) {
        getInitleaderboardsValue(listener);
        db.getReference(OWNERSHIP).limitToLast(20).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Leaderboard leaderboard = convertToLeaderBoard(dataSnapshot);
                listener.newValue(leaderboard);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.error(databaseError);
            }
        });
    }

    private void getInitleaderboardsValue(LeaderboardListener listener) {
        db.getReference().limitToLast(20).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Leaderboard leaderboard = convertToLeaderBoard(dataSnapshot);
                listener.newValue(leaderboard);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.error(databaseError);
            }
        });
    }

    public void listenOwnershipChange(final OwnershipListener listener) {
        getInitOwnershipValue(listener);
        db.getReference(OWNERSHIP).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                try {
                    listener.newValue(parseModel(dataSnapshot));
                } catch (ParseErrorException ex) {
                    listener.error(ex);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.error(databaseError);
            }
        });
    }

    private void getInitOwnershipValue(final OwnershipListener listener) {
        db.getReference(OWNERSHIP).limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    listener.newValue(parseModel(dataSnapshot));
                } catch (ParseErrorException ex) {
                    listener.error(ex);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.error(databaseError);
            }
        });
    }

    private Ownership parseModel(DataSnapshot dataSnapshot) throws ParseErrorException {
        Object rawValue = dataSnapshot.getValue();
        if (rawValue instanceof String) {
            return Json.fromJson(((String) rawValue), Ownership.class);
        } else {
            throw new ParseErrorException();
        }
    }

    private Leaderboard convertToLeaderBoard(DataSnapshot dataSnapshot) {
        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
        List<Ownership> collect = Stream.of(children)
                .map(dataSnapshot1 -> {
                    try {
                        return parseModel(dataSnapshot1);
                    } catch (ParseErrorException e) {
                        return null;
                    }
                })
                .filterNot(value -> value == null)
                .sorted((o1, o2) -> o2.getDate().compareTo(o1.getDate()))
                .collect(Collectors.toList());
        Leaderboard leaderboard = new Leaderboard();
        for (int i = 0; i < collect.size(); i++) {
            Ownership ownership = collect.get(i);
            if (!leaderboard.containUser(ownership)) {
                leaderboard.add(ownership);
            }
        }
        return leaderboard;
    }

    public interface LeaderboardListener {
        void newValue(Leaderboard model);

        void error(Object error);
    }

    public interface OwnershipListener {
        void newValue(Ownership model);

        void error(Object error);
    }
}
