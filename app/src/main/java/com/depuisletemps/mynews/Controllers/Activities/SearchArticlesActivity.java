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

import com.depuisletemps.mynews.R;

import java.io.Console;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class SearchArticlesActivity extends AppCompatActivity {

    private TextView beginDate, endDate, queryTerms;
    private CheckBox cbBusiness,cbBooks,cbScience,cbSports,cbTelevision,cbWorld;
    private List<CheckBox> checkBoxesArray;
    private Button searchButton;
    private DatePickerDialog.OnDateSetListener dateSetListenerBegin;
    private DatePickerDialog.OnDateSetListener dateSetListenerEnd;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_articles);
        this.configureToolbar();

        beginDate = (TextView) findViewById(R.id.activity_form_begin);
        endDate = (TextView) findViewById(R.id.activity_form_end);
        queryTerms = (TextView) findViewById(R.id.activity_form_query_term);
        searchButton = (Button) findViewById(R.id.activity_form_search_button);

        cbBooks = (CheckBox) findViewById(R.id.activity_form_checkbox_books);
        cbBusiness = (CheckBox) findViewById(R.id.activity_form_checkbox_business);
        cbScience = (CheckBox) findViewById(R.id.activity_form_checkbox_science);
        cbSports = (CheckBox) findViewById(R.id.activity_form_checkbox_sports);
        cbTelevision = (CheckBox) findViewById(R.id.activity_form_checkbox_television);
        cbWorld = (CheckBox) findViewById(R.id.activity_form_checkbox_world);

        checkBoxesArray = Arrays.asList(cbWorld,cbTelevision,cbSports,cbScience,cbBusiness,cbBooks);

        beginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(SearchArticlesActivity.this,dateSetListenerBegin,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(235,134,4)));
                dialog.show();
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(SearchArticlesActivity.this,dateSetListenerEnd,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(235,134,4)));
                dialog.show();
            }
        });

        dateSetListenerBegin = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + month + "/" + year;
                beginDate.setText(date);
            }
        };

        dateSetListenerEnd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + month + "/" + year;
                endDate.setText(date);
            }
        };

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                String terms = queryTerms.getText() == "" ? "" : queryTerms.getText().toString();
                String begin = beginDate.getText() == "" ? "" : beginDate.getText().toString();
                String end = endDate.getText() == "" ? "" : endDate.getText().toString();
                bundle.putString("terms",terms);
                bundle.putString("begin",begin);
                bundle.putString("end",end);

                for(CheckBox checkBox : checkBoxesArray) {
                    String category = checkBox.getText().toString();
                    if (checkBox.isChecked()) {bundle.putString(category, "checked");} else {bundle.putString(category, "unchecked");}
                }

                if (!terms.equals("")) {startSearchArticlesResultActivity(bundle);}
            }
        });

    }

    private void configureToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void startSearchArticlesResultActivity(Bundle bundle) {
        Intent myIntent = new Intent(SearchArticlesActivity.this,SearchArticlesResultActivity.class);
        myIntent.putExtras(bundle);
        this.startActivity(myIntent);
    }

}
