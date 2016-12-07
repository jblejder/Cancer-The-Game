package com.kutapps.keyten.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kutapps.keyten.home.models.LoggedUserModel;
import com.kutapps.keyten.shared.database.DatabaseHelper;
import com.kutapps.keyten.shared.database.models.KeytenModel;

public class WifiStateReceiver extends BroadcastReceiver
{
    public static final String TAG = WifiStateReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent)
    {
        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if (info != null && info.isConnected())
        {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String ssid = wifiInfo.getSSID();
            if (ssid.replace("\"", "").contains("angrynerds"))
            {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseHelper databaseHelper = new DatabaseHelper();
                databaseHelper.setKeyten(new KeytenModel(true, new LoggedUserModel
                        (currentUser)));
            }
        }
    }
}
