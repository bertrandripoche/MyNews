package com.depuisletemps.mynews.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTools {

    private final static SimpleDateFormat WRONG_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private final static SimpleDateFormat RIGHT_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    public static Date getToday() throws ParseException {
        return new Date();
    }

    public static String getDateStringFromString (String myDate) throws ParseException {
        Date date = WRONG_DATE_FORMAT.parse(myDate);
        return RIGHT_DATE_FORMAT.format(date);
    }

    public static Date getDate(String myDate) throws ParseException {
        return WRONG_DATE_FORMAT.parse(myDate);
    }
}
