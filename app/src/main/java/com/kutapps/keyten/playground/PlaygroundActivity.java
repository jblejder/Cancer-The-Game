package com.kutapps.keyten.playground;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kutapps.keyten.R;
import com.kutapps.keyten.shared.database.StorageRx;

public class PlaygroundActivity extends AppCompatActivity {

    private static final String TAG = "PlaygroundActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground);

        StorageRx rx = new StorageRx();
        rx.ownershipObservable().subscribe(ownership -> {
            Log.d(TAG, "onCreate: ");
        });
        rx.leaderboardObservable().subscribe(ownerships -> {
            Log.d(TAG, "onCreate: ");
        });
    }
}
