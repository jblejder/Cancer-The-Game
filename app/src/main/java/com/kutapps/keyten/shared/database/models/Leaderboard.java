package com.kutapps.keyten.shared.database.models;

import com.kutapps.keyten.home.models.LoggedUserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Leaderboard extends ArrayList<Ownership> {

    public Leaderboard() {
    }

    public Leaderboard(List<Ownership> collect) {
        super(collect);
    }

    public boolean containUser(Ownership ownership) {
        LoggedUserModel user = ownership.getUser();
        if (user.id == null) {
            return false;
        }
        for (Ownership item : this) {
            LoggedUserModel itemUser = item.getUser();
            if (Objects.equals(user.id, itemUser.id)) {
                return true;
            }
        }
        return false;
    }
}
