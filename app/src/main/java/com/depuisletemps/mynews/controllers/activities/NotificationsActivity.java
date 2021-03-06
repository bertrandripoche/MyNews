package com.depuisletemps.mynews.controllers.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.depuisletemps.mynews.models.NotificationSharedPreferences;
import com.depuisletemps.mynews.R;
import com.depuisletemps.mynews.utils.AlertReceiver;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class NotificationsActivity extends AppCompatActivity implements View.OnClickListener {
    private SwitchCompat switchNotification;

    private EditText queryTerms;
    private CheckBox cbArts,cbBooks,cbScience,cbSports,cbTechnology,cbWorld;
    private List<CheckBox> checkBoxesArray;
    private NotificationSharedPreferences notificationSharedPreferences;

    private static final String PREFS_TERMS = "terms";
    private static final String PREFS_ARTS = "arts";
    private static final String PREFS_BOOKS = "books";
    private static final String PREFS_SCIENCE = "science";
    private static final String PREFS_SPORTS = "sports";
    private static final String PREFS_TECHNOLOGY = "technology";
    private static final String PREFS_WORLD = "world";
    private static final String PREFS_SWITCH = "switch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        notificationSharedPreferences = new NotificationSharedPreferences();

        switchNotification = findViewById(R.id.activity_notif_switch);
        queryTerms = findViewById(R.id.activity_notif_input);
        cbArts = findViewById(R.id.activity_form_checkbox_arts);
        cbBooks = findViewById(R.id.activity_form_checkbox_books);
        cbScience = findViewById(R.id.activity_form_checkbox_science);
        cbSports = findViewById(R.id.activity_form_checkbox_sports);
        cbTechnology = findViewById(R.id.activity_form_checkbox_technology);
        cbWorld = findViewById(R.id.activity_form_checkbox_world);
        checkBoxesArray = Arrays.asList(cbWorld,cbArts,cbSports,cbScience,cbTechnology,cbBooks);

        this.configureToolbar();
        this.restoreNotificationSharedPreferences();

        switchNotification.setOnClickListener(this);
        queryTerms.addTextChangedListener(textWatcher);
        cbArts.setOnClickListener(this);
        cbBooks.setOnClickListener(this);
        cbWorld.setOnClickListener(this);
        cbScience.setOnClickListener(this);
        cbSports.setOnClickListener(this);
        cbTechnology.setOnClickListener(this);
    }

    private void restoreNotificationSharedPreferences() {
        // If sharedPreferences exists -meaning a notification exists-, we populate the notification from it
        if (!notificationSharedPreferences.getAllPreferences(this).isEmpty()) {

            if (!Objects.equals(notificationSharedPreferences.getPreferences(this, PREFS_TERMS), "")) {
                String text = notificationSharedPreferences.getPreferences(this, PREFS_TERMS);
                queryTerms.setText(text);
            }
            if (notificationSharedPreferences.getBooleanPreferences(this, PREFS_ARTS)) {
                cbArts.setChecked(true);
            }
            if (notificationSharedPreferences.getBooleanPreferences(this, PREFS_BOOKS)) {
                cbBooks.setChecked(true);
            }
            if (notificationSharedPreferences.getBooleanPreferences(this, PREFS_SCIENCE)) {
                cbScience.setChecked(true);
            }
            if (notificationSharedPreferences.getBooleanPreferences(this, PREFS_SPORTS)) {
                cbSports.setChecked(true);
            }
            if (notificationSharedPreferences.getBooleanPreferences(this, PREFS_TECHNOLOGY)) {
                cbTechnology.setChecked(true);
            }
            if (notificationSharedPreferences.getBooleanPreferences(this, PREFS_WORLD)) {
                cbWorld.setChecked(true);
            }
            if (notificationSharedPreferences.getBooleanPreferences(this, PREFS_SWITCH)) {
                switchNotification.setChecked(true);
            }

            stopAlarm();
            createNotification();
        }
    }

    private void configureToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        // We disable notification switch after each text modification
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (switchNotification.isChecked()) {switchNotification.setChecked(false); stopAlarm();}
        }
    };

    @Override
    public void onClick(View v) {
        // Behavior on switch and checkbox click
        if (v.equals(switchNotification)) {
            if (checkNotificationConditionValidity(queryTerms.getText().toString(), getString(R.string.no_query_item_warning_message), getString(R.string.no_category_warning_message)) && switchNotification.isChecked()) {createNotification();Toast.makeText(this, R.string.notification_created, Toast.LENGTH_LONG).show();}
            if (!switchNotification.isChecked()) {stopAlarm();}
        }

        else if (v.equals(cbArts) ||v.equals(cbBooks) || v.equals(cbScience)||v.equals(cbSports)||v.equals(cbTechnology)||v.equals(cbWorld)) {
            checkNotificationConditionValidity(queryTerms.getText().toString(), getString(R.string.no_query_item_warning_message), getString(R.string.no_category_warning_message));
        }
    }

    public boolean checkNotificationConditionValidity(String queryTerm, String msgQueryTerm, String msgCategory) {
        return checkQueryTermValidity(queryTerm ,msgQueryTerm) && checkCategoriesValidity(checkBoxesArray, msgCategory);
    }

    public boolean checkQueryTermValidity(String queryTerm, String message) {
        if (queryTerm.equals("")) {
            disableNotificationSwitch(message);
            return false;
        }
        return true;
    }

    public boolean checkCategoriesValidity(List<CheckBox> checkBoxesArray, String message) {
        for(CheckBox checkBox : checkBoxesArray) {
            if (checkBox.isChecked()) {return true;}
        }
        disableNotificationSwitch(message);
        return false;
    }

    public void disableNotificationSwitch(String message) {
        if (switchNotification.isChecked()) {
            switchNotification.setChecked(false);
            stopAlarm();
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    public final void createNotification(){
        // When we create notifitication, we also save shared preferences to remember notification parameters
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,1);
        calendar.set(Calendar.MINUTE,12);
        calendar.set(Calendar.SECOND,0);

        startAlarm(calendar);
        saveNotificationSharedPreferences();
    }

    /**
     * This method save the notification parameters via sharedPreferences
     */
    public void saveNotificationSharedPreferences() {
        notificationSharedPreferences.storeNotificationParameters(this, queryTerms.getText().toString(), cbArts.isChecked(), cbBooks.isChecked(), cbScience.isChecked(), cbSports.isChecked(), cbTechnology.isChecked(), cbWorld.isChecked());
    }

    /**
     * This method start the notification via the alertReceiver class and alarmManager
     */
    public void startAlarm(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1, intent, 0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY/*60000*/,pendingIntent);
    }

    /**
     * This method stop the notification and clear the shared preferences
     */
    public void stopAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1, intent, 0);

        alarmManager.cancel(pendingIntent);
        // Every time we stop the alarm, we remove the shared preferences
        notificationSharedPreferences.clearPreferences(this);
    }

}
