package com.example.android.svapliquid.Activity.Ordin.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Andorid on 14/11/2017.
 */

public class DateData {
    int year;
    int month;
    int day;

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public DateData() {
        this(0,0,0);
    }
    public DateData(int year, int month, int day) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
    public DateData(String[] strings) {
        setDate(strings);
    }

    public String getDate() {
        return year+"/"+month+"/"+day;
    }
    public void setDate(int year, int month, int day) {
        this.day = day;
        this.year = year;
        this.month = month;
    }
    public void setDateIt(String dateIt) throws ParseException{
        isDateIt(dateIt);
        String[] strings = dateIt.split("/");
        setDate(strings[2], strings[1], strings[0]);
    }
    public void isDateIt(String dateIt) throws ParseException{
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
        sf.setLenient(false);
        sf.parse(dateIt);
    }

    public boolean isSet() {
        return !(year==0 && month==0 && day==0);
    }

    public void setDate(String[] strings) {
        setDate(strings[0], strings[1], strings[2]);
    }
    private void setDate(String year, String month, String day) {
        this.year = Integer.parseInt(year);
        this.month = Integer.parseInt(month);
        this.day = Integer.parseInt(day);
    }

    public String getDateIt() {
        return getDay()+"/"+getMonth()+"/"+getYear();
    }
}
