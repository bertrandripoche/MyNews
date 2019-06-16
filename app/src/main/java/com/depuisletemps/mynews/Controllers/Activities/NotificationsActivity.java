package com.depuisletemps.mynews.Controllers.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.depuisletemps.mynews.R;
import com.depuisletemps.mynews.Utils.AlertReceiver;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity implements View.OnClickListener {
    private SwitchCompat switchNotification;
    private EditText queryTerms;
    private CheckBox cbArts,cbBooks,cbScience,cbSports,cbTechnology,cbWorld;
    private List<CheckBox> checkBoxesArray;

    private static final String CHANNEL_ID = "CHANNEL_ID";
    private int NOTIFICATION_ID  = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        this.configureToolbar();

        switchNotification = findViewById(R.id.activity_notif_switch);
        queryTerms = findViewById(R.id.activity_notif_input);
        cbArts = findViewById(R.id.activity_form_checkbox_arts);
        cbBooks = findViewById(R.id.activity_form_checkbox_books);
        cbScience = findViewById(R.id.activity_form_checkbox_science);
        cbSports = findViewById(R.id.activity_form_checkbox_sports);
        cbTechnology = findViewById(R.id.activity_form_checkbox_technology);
        cbWorld = findViewById(R.id.activity_form_checkbox_world);
        checkBoxesArray = Arrays.asList(cbWorld,cbArts,cbSports,cbScience,cbTechnology,cbBooks);

        switchNotification.setOnClickListener(this);
        queryTerms.addTextChangedListener(textWatcher);
        cbArts.setOnClickListener(this);
        cbBooks.setOnClickListener(this);
        cbBooks.setOnClickListener(this);
        cbScience.setOnClickListener(this);
        cbSports.setOnClickListener(this);
        cbTechnology.setOnClickListener(this);
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

            if (checkNotificationConditionValidity()) {
                Toast.makeText(getBaseContext(), "Notif valide", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getBaseContext(), "Notif INVALIDE", Toast.LENGTH_LONG).show();
                //if (switchNotification.isChecked()) {switchNotification.setChecked(false);}
            }
        }
    };

    @Override
    public void onClick(View v) {

        if (v.equals(switchNotification)) {
            Toast.makeText(this, "Enable notif", Toast.LENGTH_LONG).show();
            if (checkNotificationConditionValidity()) {createNotification();}
        }

        else if (v.equals(cbArts) ||v.equals(cbBooks) || v.equals(cbScience)||v.equals(cbSports)||v.equals(cbTechnology)||v.equals(cbWorld)) {
            checkNotificationConditionValidity();
        }
    }

    private boolean checkNotificationConditionValidity() {
        if (!checkQueryTermValidity()) {return false;}
        if (!checkCategoriesValidity()) {return false;}
        return true;
    }

    private boolean checkQueryTermValidity() {
        if (queryTerms.getText().toString().equals("")) {
            disableNotificationSwitch(getString(R.string.no_query_item_warning_message));
            return false;
        }
        return true;
    }

    private boolean checkCategoriesValidity() {
        for(CheckBox checkBox : checkBoxesArray) {
            if (checkBox.isChecked()) {return true;}
        }
        disableNotificationSwitch(getString(R.string.no_category_warning_message));
        return false;
    }

    private void disableNotificationSwitch(String message) {
        if (switchNotification.isChecked()) {
            switchNotification.setChecked(false);
            stoptAlarm();
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    private final void createNotification(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,1);
        calendar.set(Calendar.MINUTE,20);
        calendar.set(Calendar.SECOND,0);

        startAlarm(calendar);
    }

    private void startAlarm(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,NOTIFICATION_ID, intent, 0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),60000,pendingIntent);
    }

    private void stoptAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,NOTIFICATION_ID, intent, 0);

        alarmManager.cancel(pendingIntent);
    }

}
