package com.ly.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String createDate() {
        String result = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-D");
            result = simpleDateFormat.format(new Date()).substring(0,10);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
