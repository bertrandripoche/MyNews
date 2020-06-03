package com.depuisletemps.mynews.controllers.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
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
import java.util.Objects;

public class SearchArticlesActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView beginDateButton, endDateButton, queryTerms;
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

        CheckBox cbArts = findViewById(R.id.activity_form_checkbox_arts);
        CheckBox cbBooks = findViewById(R.id.activity_form_checkbox_books);
        CheckBox cbScience = findViewById(R.id.activity_form_checkbox_science);
        CheckBox cbSports = findViewById(R.id.activity_form_checkbox_sports);
        CheckBox cbTechnology = findViewById(R.id.activity_form_checkbox_technology);
        CheckBox cbWorld = findViewById(R.id.activity_form_checkbox_world);

        checkBoxesArray = Arrays.asList(cbArts, cbBooks, cbScience, cbSports, cbTechnology, cbWorld);
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
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);
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
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.rgb(235,134,4)));
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

    /**
     * This method returns a bundle made from our search parameters
     * @return the Bundle matching the search parameters
     */
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

    /**
     * This method returns a boolean indicating if form is valid
     * @return true if search form is valid
     */
    private boolean isFormValid() throws ParseException {
        return isCheckBoxesValid() && isSearchTermValid() && isDateValid();
    }

    /**
     * This method returns a boolean indicating if at least one checkbox is checked
     * @return true if one checkbox is checked
     */
    private boolean isCheckBoxesValid() {
            for(CheckBox checkBox : checkBoxesArray)
                if (checkBox.isChecked()) return true;
            return false;
        }

    /**
     * This method returns a boolean indicating if at least one character has been written
     * @return true if at least one character has been input
     */
    private boolean isSearchTermValid() {
        String terms = queryTerms.getText().toString().trim();
        return !terms.equals("");
        }

    /**
     * This method returns a boolean indicating if dates are valid
     * @return true if dates are valid
     */
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

    /**
     * This method check that date is not in the future
     * If so, it displays a message and empty the matching field
     */
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

    /**
     * This method write a warning message in a toast
     */
    private void displayWarningMessage(String errorType) {
        String warningText = "";
        if (errorType.equals("futureDate") || errorType.equals("dateInversion") || errorType.equals("missingInfo")) {
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
