package com.depuisletemps.mynews.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTools {
    @SuppressLint("SimpleDateFormat")
    private final static SimpleDateFormat WRONG_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    @SuppressLint("SimpleDateFormat")
    private final static SimpleDateFormat RIGHT_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    public static Date getToday() {
        return new Date();
    }

    static String getTodayString() {
        return RIGHT_DATE_FORMAT.format(getToday());
    }

    static String getYesterdayString() {
        return RIGHT_DATE_FORMAT.format(yesterday());
    }

    public static String getDateStringFromString (String myDate) throws ParseException {
        Date date = WRONG_DATE_FORMAT.parse(myDate);
        return RIGHT_DATE_FORMAT.format(date);
    }

    public static Date getDate(String myDate) throws ParseException {
        return WRONG_DATE_FORMAT.parse(myDate);
    }

    private static Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    public static String oneYearAgo() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -180);
        return RIGHT_DATE_FORMAT.format(cal.getTime());
    }
}
