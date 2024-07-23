package com.org.ultrainstinct.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XDate {
    static SimpleDateFormat formater = new SimpleDateFormat();
   
   public static Date toDate(String date, String pattern) {
    try {
        SimpleDateFormat formater = new SimpleDateFormat(pattern);
        return formater.parse(date);
    } catch (ParseException ex) {
        throw new IllegalArgumentException("Invalid date format: " + date, ex);
    }
}

public static String toString(Date date, String pattern) {
    SimpleDateFormat formater = new SimpleDateFormat(pattern);
    return formater.format(date);
}

public static Date addDays(Date date, long days) {
    Date newDate = new Date(date.getTime() + days * 24 * 60 * 60 * 1000);
    return newDate;
}
}
