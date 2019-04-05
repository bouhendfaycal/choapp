package com.google.firebase.quickstart.database.models;

import android.content.res.Resources;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Entry extends DataSupport {

    private int mHour;
    private int mMinute;
    private int mYear ;
    private int mMonth;
    private int mDay;
    private int Creneau;
    private float TauxGlycemie;
    private float PressionArterielle;
    private float Acetone;
    private String Note;
    private int time;

    public Entry() {
        mMinute = Calendar.getInstance().get(Calendar.MINUTE);
        mHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        mMonth = Calendar.getInstance().get(Calendar.MONTH);
        mYear = Calendar.getInstance().get(Calendar.YEAR);
        /*Creneau = 0;
        TauxGlycemie = 0;
        PressionArterielle = 0;
        Acetone = 0;*/
        Note = "";
    }

    //region Getters

    public int getCreneau() {
        return Creneau;
    }

    public int getCreneauFormated() {
        return Creneau;
    }

    public float getTauxGlycemie() {
        return TauxGlycemie;
    }

    public float getPressionArterielle() {
        return PressionArterielle;
    }

    public float getAcetone() {
        return Acetone;
    }

    public String getNote() {
        return Note;
    }

    public int getmHour() {
        return mHour;
    }

    public int getmMinute() {
        return mMinute;
    }

    public int getmYear() {
        return mYear;
    }

    public int getmMonth() {
        return mMonth;
    }

    public int getmDay() {
        return mDay;
    }
    //endregion

    //region Setters

    public void setCreneau(int creneau) {
        Creneau = creneau;
    }

    public void setTauxGlycemie(float tauxGlycemie) {
        TauxGlycemie = tauxGlycemie;
    }

    public void setPressionArterielle(float pressionArterielle) {
        PressionArterielle = pressionArterielle;
    }

    public void setAcetone(float acetone) {
        Acetone = acetone;
    }

    public void setNote(String note) {
        Note = note;
    }

    public void setDateTime(int year, int month, int day, int hour, int minute) {
        mYear = year;
        mMonth = month;
        mDay = day;
        mHour = hour;
        mMinute = minute;
    }

    public String getTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, mHour);
        calendar.set(Calendar.MINUTE, mMinute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, mDay);
        calendar.set(Calendar.MONTH, mMonth);
        calendar.set(Calendar.YEAR, mYear);
        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy 'a' k:mm");
        return  format.format(calendar.getTime());
    }
    public int getTimeUnix() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, mHour);
        calendar.set(Calendar.MINUTE, mMinute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, mDay);
        calendar.set(Calendar.MONTH, mMonth);
        calendar.set(Calendar.YEAR, mYear);
        return (int) (calendar.getTimeInMillis() / 1000L);
    }

    @Override
    public long getBaseObjId() {
        return super.getBaseObjId();
    }

//endregion

    //TODO : create db stuff

    //TODO : create Creneau list

    //TODO : CRUD workflow
}
