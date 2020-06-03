package com.depuisletemps.mynews.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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

    /**
     * This method updates the query variable, used for the query part of the filter
     */
    private void getQueryFromSharedPreferences(Context context) {
        if (!notificationSharedPreferences.getPreferences(context, PREFS_TERMS).equals("")) {
            query = notificationSharedPreferences.getPreferences(context, PREFS_TERMS);
        }
    }

    /**
     * This method updates the filter variable, used for the section part of the filter
     */
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

    /**
     * This method get the reference date for the filter (yesterday and today)
     */
    private void getReferencesDates() {
        begin = DateTools.getYesterdayString();
        end = DateTools.getTodayString();
    }

    /**
     * This method manages the HTTP request to New-York Times to get the articles to display
     */
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

    /**
     * This method creates the notification
     */
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

    /**
     * This method returns the a Map<String, String> needed to create the filters for streams
     * @param query : the query entered by user
     * @param filterQuery : the parameters for the filter
     * @param begin : the begin date
     * @param end : the end date
     * @return a Map<String, String> of name of filter and their value, used for Streams
     */
    private Map<String, String > createFilterForStreams(String query, String filterQuery, String begin, String end) {
        Map<String, String > data = new HashMap<>();
        if (!query.equals("")) {data.put("q",query);}
        if (!filterQuery.equals("")) {data.put("fq",filterQuery);}
        if (!begin.equals("")) {data.put("beginDate",begin);}
        if (!end.equals("")) {data.put("endDate",end);}
        return data;
    }
}
