package com.depuisletemps.mynews.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTools {

    private final static SimpleDateFormat WRONG_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private final static SimpleDateFormat RIGHT_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    public static Date getToday() {
        return new Date();
    }

    public static String getTodayString() {
        return RIGHT_DATE_FORMAT.format(getToday());
    }

    public static String getYesterdayString() {
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
}
