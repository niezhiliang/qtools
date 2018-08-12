package com.liumapp.qtools.date;

import java.util.Calendar;
import java.util.Date;

/**
 * @author liumapp
 * @file DateTool.java
 * @email liumapp.com@gmail.com
 * @homepage http://www.liumapp.com
 * @date 2018/8/12
 */
public class DateTool {

    /**
     * return a new date according to year number
     * @param year year number
     * @return end date
     */
    public static Date getEndDateByYearNumber(int year) {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        date = calendar.getTime();
        return date;
    }


}