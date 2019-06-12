package com.depuisletemps.mynews.Controllers.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.depuisletemps.mynews.R;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class SearchArticlesActivity extends AppCompatActivity  implements View.OnClickListener{

    private TextView beginDateButton, endDateButton, queryTerms;
    private CheckBox cbBusiness,cbBooks,cbScience,cbSports,cbTelevision,cbWorld;
    private List<CheckBox> checkBoxesArray;
    private Button searchButton;
    private DatePickerDialog.OnDateSetListener dateSetListenerBegin;
    private DatePickerDialog.OnDateSetListener dateSetListenerEnd;
    private static boolean isBeginDate;
   // private DatePickerDialog.OnDateSetListener dateSetListener;

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

        cbBooks = findViewById(R.id.activity_form_checkbox_books);
        cbBusiness = findViewById(R.id.activity_form_checkbox_business);
        cbScience = findViewById(R.id.activity_form_checkbox_science);
        cbSports = findViewById(R.id.activity_form_checkbox_sports);
        cbTelevision = findViewById(R.id.activity_form_checkbox_television);
        cbWorld = findViewById(R.id.activity_form_checkbox_world);

        checkBoxesArray = Arrays.asList(cbWorld,cbTelevision,cbSports,cbScience,cbBusiness,cbBooks);
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
        this.startActivity(myIntent);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(beginDateButton)) {
            isBeginDate = true;
            displayDatePicker(beginDateButton);
        }
        else if (v.equals(endDateButton)) {
            isBeginDate = false;
            displayDatePicker(endDateButton);
         }
        else if (v.equals(searchButton)) {
            Bundle bundle = generateBundle();
            if (bundle != null) {
                startSearchArticlesResultActivity(bundle);
            } else {
                Toast.makeText(SearchArticlesActivity.this, R.string.terms_warning, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void displayDatePicker(TextView inputDate) {
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
            String date = dayOfMonth + "/" + month + "/" + year;
            if (isBeginDate) {
                beginDateButton.setText(date);
            } else {
                endDateButton.setText(date);}
        }
    };

    private Bundle generateBundle() {
        String terms = queryTerms.getText().toString().trim();

        if (terms.equals("")) {return null;}

        String begin = beginDateButton.getText().toString().trim();
        String end = endDateButton.getText().toString().trim();

        Bundle bundle = new Bundle();
        bundle.putString("terms",terms);
        bundle.putString("begin",begin);
        bundle.putString("end",end);

        for(CheckBox checkBox : checkBoxesArray) {
            String category = checkBox.getText().toString().trim();
            if (checkBox.isChecked()) {
                bundle.putString(category, "checked");
            } else {
                bundle.putString(category, "unchecked");}
        }
        return bundle;
    }
}
