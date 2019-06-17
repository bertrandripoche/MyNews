package com.depuisletemps.mynews.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.depuisletemps.mynews.R;

public class AlertReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "CHANNEL_ID";

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_nytimes_logo)
                .setContentTitle("Titre à changer")
                .setContentText("Texte à changer")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());
    }
}
