package com.driftycode.ukweatherforecast.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Abdul Raheem on 19/11/17.
 */

public class Common {


    private static String[] daysArray = new String[]{"SAT", "SUN", "MON", "TUE", "WED", "THUR", "FRI"};


    public static float getKelToCels(float kelvin) {
        System.out.println("kelvin: " + kelvin);
        float celsius = kelvin - 273.15F;
        System.out.println("Celsius: " + Math.round(celsius));
        return Math.round(celsius);
    }

    private Calendar getCalendarObjFromStringDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(dateString);
        System.out.println(sdf.format(date));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public String getWeekFromDateString(String dateString) throws ParseException {
        Calendar weekCal = this.getCalendarObjFromStringDate(dateString);
        int dayOfWeek = weekCal.get(Calendar.DAY_OF_WEEK);
        Date time = weekCal.getTime();
        return daysArray[dayOfWeek];
    }

    public String getTimeFromDateString(String dateString) throws ParseException {
        Calendar timeCal = this.getCalendarObjFromStringDate(dateString);
        Date time = timeCal.getTime();
        return time.getHours() + ":" + time.getMinutes();
    }
}
