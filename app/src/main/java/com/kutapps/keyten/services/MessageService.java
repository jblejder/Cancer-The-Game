package com.kutapps.keyten.services;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessageService extends FirebaseMessagingService
{
    private static final String TAG = "FirebaseService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "onMessageReceived: came");
        new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(getBaseContext(), "przyszlo" +
                " powiadomienie", Toast.LENGTH_SHORT).show());
    }
}