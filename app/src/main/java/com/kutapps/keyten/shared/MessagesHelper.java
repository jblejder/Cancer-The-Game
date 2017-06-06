package com.kutapps.keyten.shared;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.kutapps.keyten.shared.constants.FirebaseMessageConst;
import com.kutapps.keyten.shared.database.models.KeytenModel;

public class MessagesHelper {
    private FirebaseMessaging firebaseMessaging;

    public MessagesHelper() {
        this.firebaseMessaging = FirebaseMessaging.getInstance();
    }

    public void sendKeytenMessage(KeytenModel model) {
        RemoteMessage build = new RemoteMessage.Builder(FirebaseMessageConst.KEYTEN_TOPIC)
                .addData("title", "KEYTEN!").addData("body", "ja wzołem").build();
        firebaseMessaging.send(build);
    }

    public void registerForKeytens() {
        firebaseMessaging.subscribeToTopic(FirebaseMessageConst.KEYTEN_TOPIC);
    }
}
