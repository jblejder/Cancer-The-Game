package com.kutapps.keyten.shared.database;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.firebase.database.DataSnapshot;
import com.kutapps.keyten.shared.database.models.Leaderboard;
import com.kutapps.keyten.shared.database.models.Ownership;

import java.util.List;

class LeaderboardMapper implements Mapper<DataSnapshot, Leaderboard> {

    private Mapper<DataSnapshot, Ownership> ownershipMapper;

    public LeaderboardMapper(Mapper<DataSnapshot, Ownership> ownershipMapper) {
        this.ownershipMapper = ownershipMapper;
    }

    @Override
    public Leaderboard map(DataSnapshot v) {
        Iterable<DataSnapshot> children = v.getChildren();
        List<Ownership> collect = Stream.of(children)
                .map(ownershipMapper::map)
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
}
