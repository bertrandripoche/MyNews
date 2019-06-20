package com.depuisletemps.mynews.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.depuisletemps.mynews.models.NotificationSharedPreferences;
import com.depuisletemps.mynews.models.SectionFirstResponse;
import com.depuisletemps.mynews.R;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class AlertReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "CHANNEL_ID";
    private static final String PREFS_TERMS = "terms";
    private static final String PREFS_ARTS = "arts";
    private static final String PREFS_BOOKS = "books";
    private static final String PREFS_SCIENCE = "science";
    private static final String PREFS_SPORTS = "sports";
    private static final String PREFS_TECHNOLOGY = "technology";
    private static final String PREFS_WORLD = "world";

    private static String notificationTitle;
    private static String notificationText;

    private static String query = "";
    private static String filter = "";
    private static String begin = "";
    private static String end = "";

    private NotificationSharedPreferences notificationSharedPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        notificationSharedPreferences = new NotificationSharedPreferences();
        getQueryFromSharedPreferences(context);
        getFilterQueryFromSharedPreferences(context);
        getReferencesDates();

        this.executeHttpRequestWithRetrofit(context);
    }

    private void getQueryFromSharedPreferences(Context context) {
        if (!notificationSharedPreferences.getPreferences(context, PREFS_TERMS).equals("")) {
            query = notificationSharedPreferences.getPreferences(context, PREFS_TERMS);
        }
    }

    private void getFilterQueryFromSharedPreferences(Context context) {
        if (notificationSharedPreferences.getBooleanPreferences(context, PREFS_ARTS)) {
            filter = (filter.equals(""))? "\"" + PREFS_ARTS + "\"" : filter + " " + "\"" + PREFS_ARTS + "\"";
        }
        if (notificationSharedPreferences.getBooleanPreferences(context, PREFS_BOOKS)) {
            filter = (filter.equals(""))? "\"" + PREFS_BOOKS + "\"" : filter + " " + "\"" + PREFS_BOOKS + "\"";
        }
        if (notificationSharedPreferences.getBooleanPreferences(context, PREFS_SCIENCE)) {
            filter = (filter.equals(""))? "\"" + PREFS_SCIENCE + "\"" : filter + " " + "\"" + PREFS_SCIENCE + "\"";
        }
        if (notificationSharedPreferences.getBooleanPreferences(context, PREFS_SPORTS)) {
            filter = (filter.equals(""))? "\"" + PREFS_SPORTS + "\"" : filter + " " + "\"" + PREFS_SPORTS + "\"";
        }
        if (notificationSharedPreferences.getBooleanPreferences(context, PREFS_TECHNOLOGY)) {
            filter = (filter.equals(""))? "\"" + PREFS_TECHNOLOGY + "\"" : filter + " " + "\"" + PREFS_TECHNOLOGY + "\"";
        }
        if (notificationSharedPreferences.getBooleanPreferences(context, PREFS_WORLD)) {
            filter = (filter.equals(""))? "\"" + PREFS_WORLD + "\"" : filter + " " + "\"" + PREFS_WORLD + "\"";
        }
    }

    private void getReferencesDates() {
        begin = DateTools.getYesterdayString();
        end = DateTools.getTodayString();
    }

    void executeHttpRequestWithRetrofit (Context context) {
        if (!query.equals("") & !filter.equals("")) {
            String filterQuery = "section_name:(\"" + filter + "\")";
            Map<String, String> data = createFilterForStreams(query, filterQuery, begin, end);

            Disposable disposable = NytimesStreams.streamFetchSearch(data).subscribeWith(new DisposableObserver<SectionFirstResponse>() {
                @Override
                public void onNext(SectionFirstResponse results) {
                    Log.e("TAG", "On Next receiver");

                    notificationTitle = "About \"" + query + "\" in " + filter;

                    int nbHitsInt = results.getResponse().getMeta().getHits();
                    String nbHits = Integer.toString(results.getResponse().getMeta().getHits());
                    if (nbHitsInt == 0) {
                        notificationText = "No article ";
                    } else if (nbHitsInt == 1) {
                        notificationText = "A new article ";
                    } else if (nbHitsInt > 1) {
                        notificationText = nbHits + " new articles ";
                    }
                    notificationText = notificationText + " since yesterday";

                    triggerNotification(context);
                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onComplete() {
                }

            });
        }
    }

    private void triggerNotification(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_nytimes_logo)
                .setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());
    }

    private Map<String, String > createFilterForStreams(String query, String filterQuery, String begin, String end) {
        Map<String, String > data = new HashMap<>();
        if (!query.equals("")) {data.put("q",query);}
        if (!filterQuery.equals("")) {data.put("fq",filterQuery);}
        if (!begin.equals("")) {data.put("beginDate",begin);}
        if (!end.equals("")) {data.put("endDate",end);}
        return data;
    }
}
