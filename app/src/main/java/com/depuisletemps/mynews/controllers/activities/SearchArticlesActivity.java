package com.depuisletemps.mynews.controllers.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.depuisletemps.mynews.R;
import com.depuisletemps.mynews.utils.DateTools;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SearchArticlesActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView beginDateButton, endDateButton, queryTerms;
    private CheckBox cbArts,cbBooks,cbScience,cbSports,cbTechnology,cbWorld;
    private List<CheckBox> checkBoxesArray;
    private Button searchButton;

    private static boolean isBeginDate;

    public static final String BEGIN_DATE = "beginDate";
    public static final String END_DATE = "endDate";
    public static final String TERMS = "terms";
    public static final String MISSING_INFO = "missingInfo";
    public static final String DATE_INVERSION = "dateInversion";
    public static final String FUTURE_DATE = "futureDate";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_articles);

        this.configureToolbar();

        beginDateButton = findViewById(R.id.activity_form_begin);
        endDateButton = findViewById(R.id.activity_form_end);
        queryTerms = findViewById(R.id.activity_form_query_term);
        searchButton = findViewById(R.id.activity_form_search_button);

        beginDateButton.setOnClickListener(this);
        endDateButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);

        cbArts = findViewById(R.id.activity_form_checkbox_arts);
        cbBooks = findViewById(R.id.activity_form_checkbox_books);
        cbScience = findViewById(R.id.activity_form_checkbox_science);
        cbSports = findViewById(R.id.activity_form_checkbox_sports);
        cbTechnology = findViewById(R.id.activity_form_checkbox_technology);
        cbWorld = findViewById(R.id.activity_form_checkbox_world);

        checkBoxesArray = Arrays.asList(cbWorld,cbBooks,cbSports,cbScience,cbTechnology,cbBooks);
    }

    @Override
    public void onClick(View v) {
        try {
            if (v.equals(beginDateButton)) {
                isBeginDate = true;
                displayDatePicker();
            }
            else if (v.equals(endDateButton)) {
                isBeginDate = false;
                displayDatePicker();
             }
            else if (v.equals(searchButton)) {
                    checkDate();
                    if (isFormValid()) {
                        Bundle bundle = generateBundle();
                        startSearchArticlesResultActivity(bundle);
                    } else {
                        displayWarningMessage(MISSING_INFO);
                    }

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void configureToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void startSearchArticlesResultActivity(Bundle bundle) {
        Intent myIntent = new Intent(SearchArticlesActivity.this,SearchArticlesResultActivity.class);
        myIntent.putExtras(bundle);
        startActivity(myIntent);
    }

    private void displayDatePicker() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(SearchArticlesActivity.this,dateSetListener,year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(235,134,4)));
        dialog.show();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String date = dayOfMonth + "/" + (month +1)  + "/" + year;
            if (isBeginDate) {
                beginDateButton.setText(date);
            } else {
                endDateButton.setText(date);
            }
            try {checkDate(); isDateValid();} catch (ParseException e) {e.printStackTrace();}
        }
    };

    private Bundle generateBundle() {
        Bundle bundle = new Bundle();
        String terms = queryTerms.getText().toString().trim();
        bundle.putString(TERMS,terms);
        String beginDate = beginDateButton.getText().toString().trim();
        bundle.putString(BEGIN_DATE, beginDate);
        String endDate = endDateButton.getText().toString().trim();
        bundle.putString(END_DATE, endDate);

        for(CheckBox checkBox : checkBoxesArray) {
            String category = checkBox.getText().toString().trim();
            if (checkBox.isChecked()) {
                bundle.putString(category, "checked");
            } else {
                bundle.putString(category, "unchecked");
            }
        }
        return bundle;
    }

    private boolean isFormValid() throws ParseException {
        boolean isFormValid = (isCheckBoxesValid() && isSearchTermValid() && isDateValid())? true: false;
        return isFormValid;
    }

    private boolean isCheckBoxesValid() {
            for(CheckBox checkBox : checkBoxesArray) {
                if (checkBox.isChecked()) {return true;}
            }
            return false;
        }

    private boolean isSearchTermValid() {
        String terms = queryTerms.getText().toString().trim();
            if (terms.equals("")) {return false;} else {return true;}
        }

    private boolean isDateValid() throws ParseException {
        String begin = beginDateButton.getText().toString().trim();
        String end = endDateButton.getText().toString().trim();

        if (!begin.equals("") && !end.equals("")) {
            Date beginDate = DateTools.getDate(begin);
            Date endDate = DateTools.getDate(end);

            if (beginDate.after(endDate)) {
                beginDateButton.setText("");
                endDateButton.setText("");
                displayWarningMessage(DATE_INVERSION);
                return false;
            }
        }

        return true;
    }

    private void checkDate() throws ParseException {
        String begin = beginDateButton.getText().toString().trim();
        String end = endDateButton.getText().toString().trim();

        if (!begin.equals("")) {
            Date beginDate = DateTools.getDate(begin);
            if (beginDate.after(DateTools.getToday())) {
                beginDateButton.setText("");
                displayWarningMessage(FUTURE_DATE);
            }
        }
        if (!end.equals("")) {
            Date endDate = DateTools.getDate(end);
            if (endDate.after(DateTools.getToday())) {
                endDateButton.setText("");
                displayWarningMessage(FUTURE_DATE);
            }
        }
    }

    private void displayWarningMessage(String errorType) {
        String warningText = "";
        if (errorType == "futureDate" || errorType == "dateInversion" || errorType == "missingInfo") {
            switch (errorType) {
                case "futureDate" :
                    warningText = getString(R.string.form_warning_futur_date);
                    break;
                case "dateInversion" :
                    warningText = getString(R.string.form_warning_inversion_date);
                    break;
                case "missingInfo" :
                    warningText = getString(R.string.form_warning_missing_info);
                    break;
            }

            Spannable centeredText = new SpannableString(warningText);
            centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                    0, warningText.length() - 1,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            Toast.makeText(SearchArticlesActivity.this, centeredText, Toast.LENGTH_LONG).show();
        }
    }
}
