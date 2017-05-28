package com.kutapps.keyten.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.kutapps.keyten.R;
import com.kutapps.keyten.main.activities.MainActivity;
import com.kutapps.keyten.shared.database.Storage;
import com.kutapps.keyten.shared.database.models.Ownership;

/**
 * Implementation of App Widget functionality.
 */
public class KeytenWidgetProvider extends AppWidgetProvider {
    static void update(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds,
                       Ownership model) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_root);

//        int src = model.value ? R.drawable.widget_background_keyten : R.drawable.widget_background_noten;
//        views.setImageViewResource(R.id.widget_image, src);
        appWidgetManager.updateAppWidget(appWidgetIds, views);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent
                .FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.widget_image, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        new Storage().listenOwnershipChange(new Storage.OwnershipListener() {
            @Override
            public void newValue(Ownership model) {
                update(context, appWidgetManager, appWidgetIds, model);
            }

            @Override
            public void error(Object error) {
                throw new UnsupportedOperationException("nie ma bledow!");
            }
        });
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

