package com.kutapps.keyten.shared;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.kutapps.keyten.shared.constants.MessagesTopics;
import com.kutapps.keyten.shared.database.models.KeytenModel;

import java.util.UUID;

public class MessagesHelper
{
    private FirebaseMessaging firebaseMessaging;

    public MessagesHelper()
    {
        this.firebaseMessaging = FirebaseMessaging.getInstance();
    }

    public void sendKeytenMessage(KeytenModel model)
    {
        RemoteMessage build = new RemoteMessage.Builder(MessagesTopics.to.KEYTEN_TOPIC).addData
                ("title", "KEYTEN!").addData("body", "ja wzołem").setMessageId(UUID.randomUUID()
                .toString()).build();
        firebaseMessaging.send(build);
    }

    public void registerForKeytens()
    {
        firebaseMessaging.subscribeToTopic(MessagesTopics.KEYTEN_TOPIC);
    }
}
