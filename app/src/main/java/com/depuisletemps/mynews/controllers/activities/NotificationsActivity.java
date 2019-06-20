package com.depuisletemps.mynews.controllers.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
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

public class NotificationsActivity extends AppCompatActivity implements View.OnClickListener {
    private SwitchCompat switchNotification;

    private EditText queryTerms;
    private CheckBox cbArts,cbBooks,cbScience,cbSports,cbTechnology,cbWorld;
    private List<CheckBox> checkBoxesArray;
    private NotificationSharedPreferences notificationSharedPreferences;

    private static final String CHANNEL_ID = "CHANNEL_ID";

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
        if (!notificationSharedPreferences.getAllPreferences(this).isEmpty()) {

            if (!notificationSharedPreferences.getPreferences(this, PREFS_TERMS).equals("")) {
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
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private TextWatcher textWatcher = new TextWatcher() {

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

        if (v.equals(switchNotification)) {
            if (checkNotificationConditionValidity() && switchNotification.isChecked()) {createNotification();Toast.makeText(this, R.string.notification_created, Toast.LENGTH_LONG).show();}
            if (!switchNotification.isChecked()) {stopAlarm();}
        }

        else if (v.equals(cbArts) ||v.equals(cbBooks) || v.equals(cbScience)||v.equals(cbSports)||v.equals(cbTechnology)||v.equals(cbWorld)) {
            checkNotificationConditionValidity();
        }
    }

    public boolean checkNotificationConditionValidity() {
        if (!checkQueryTermValidity()) {return false;}
        if (!checkCategoriesValidity()) {return false;}
        return true;
    }

    public boolean checkQueryTermValidity() {
        if (queryTerms.getText().toString().equals("")) {
            disableNotificationSwitch(getString(R.string.no_query_item_warning_message));
            return false;
        }
        return true;
    }

    public boolean checkCategoriesValidity() {
        for(CheckBox checkBox : checkBoxesArray) {
            if (checkBox.isChecked()) {return true;}
        }
        disableNotificationSwitch(getString(R.string.no_category_warning_message));
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
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,1);
        calendar.set(Calendar.MINUTE,12);
        calendar.set(Calendar.SECOND,0);

        startAlarm(calendar);
        saveNotificationSharedPreferences();
    }

    public void saveNotificationSharedPreferences() {
        notificationSharedPreferences.storeNotificationParameters(this, queryTerms.getText().toString(), cbArts.isChecked(), cbBooks.isChecked(), cbScience.isChecked(), cbSports.isChecked(), cbTechnology.isChecked(), cbWorld.isChecked());
    }

    public void startAlarm(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1, intent, 0);

       /* if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }*/

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),/*AlarmManager.INTERVAL_DAY*/60000,pendingIntent);
    }

    public void stopAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1, intent, 0);

        alarmManager.cancel(pendingIntent);

        notificationSharedPreferences.clearPreferences(this);
    }

    public EditText getQueryTerms() {
        return queryTerms;
    }

    public void setQueryTerms(EditText queryTerms) {
        this.queryTerms = queryTerms;
    }
}
