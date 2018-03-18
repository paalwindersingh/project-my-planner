package com.p1694151.myapplication.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Paalwinder on 18/03/18.
 */

public class DateUtils {

    public static String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
    public static String TIME_FORMAT = "hh:mma";

    public static Calendar getCalenderInstance(String timestamp, String dateFormat){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(timestamp));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }
    public static Date getTime(String timestamp, String dateFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        try {
            return sdf.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
