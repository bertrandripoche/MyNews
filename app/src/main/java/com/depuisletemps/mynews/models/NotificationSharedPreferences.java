package com.depuisletemps.mynews.models;

import android.content.Context;

import android.content.SharedPreferences;
import androidx.annotation.Nullable;

import java.util.Map;

public class NotificationSharedPreferences {
    private static final String PREFS_NAME = "mPreferences";

    private static final String PREFS_TERMS = "terms";
    private static final String PREFS_ARTS = "arts";
    private static final String PREFS_BOOKS = "books";
    private static final String PREFS_SCIENCE = "science";
    private static final String PREFS_SPORTS = "sports";
    private static final String PREFS_TECHNOLOGY = "technology";
    private static final String PREFS_WORLD = "world";
    private static final String PREFS_SWITCH = "switch";

    public NotificationSharedPreferences() {
        super();
    }

    public void storeNotificationParameters(Context context, String terms, boolean arts, boolean books, boolean science, boolean sports, boolean technology, boolean world) {
        android.content.SharedPreferences mPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = mPreferences.edit();

        editor.putString(PREFS_TERMS, terms);
        editor.putBoolean(PREFS_ARTS, arts);
        editor.putBoolean(PREFS_BOOKS, books);
        editor.putBoolean(PREFS_SCIENCE, science);
        editor.putBoolean(PREFS_SPORTS, sports);
        editor.putBoolean(PREFS_TECHNOLOGY, technology);
        editor.putBoolean(PREFS_WORLD, world);
        editor.putBoolean(PREFS_SWITCH, true);
        editor.apply();
    }

    @Nullable
    public String getPreferences(Context context, String key) {
        SharedPreferences mPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return mPreferences.getString(key, null);
    }

    public boolean getBooleanPreferences(Context context, String key) {
        SharedPreferences mPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return mPreferences.getBoolean(key, false);
    }

    public Map<String, ?> getAllPreferences(final Context context) {
        SharedPreferences mPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return mPreferences.getAll();
    }

    public void clearPreferences(Context context) {
        android.content.SharedPreferences mPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = mPreferences.edit();

        editor.clear();
        editor.apply();
    }

}
