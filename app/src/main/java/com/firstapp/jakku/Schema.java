package com.firstapp.jakku;

import static java.sql.Types.NULL;

import android.os.Handler;
import android.os.Looper;

import java.sql.Array;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Schema {
//study hours are between 8am and 5pm

    private static int study_frequency;
    private static int study_duration;
    private static int training_frequency;
    private static int training_duration;
    private static String today;


    /**
     * Calls end combines study and trainings schedule
     * @param button_day the day for that the schedule should be valid
     * @return the finished String containing the schedule for the chosen day
     * @author Christina Meisoll
     */
    public static String schedulemaker(String button_day){
        StringBuilder schema=new StringBuilder();
        int[] study_time = new int[2];
        int[] training_time = new int[2];

        int offset[] = {0,5,2,7};

        study_time = set_hours_and_minutes(study_duration);
        training_time = set_hours_and_minutes(training_duration);


        schema.append(button_day+": \n");
        schema.append(make_study_string(study_frequency,study_time));
        schema.append(choose_training_string(training_frequency, training_time, button_day));

        return schema.toString();
    }

    /**
     * Coordinates and chooses whether a training is to be scheduled for the given day
     * @param frequency How often the user wnt to train
     * @param time how many hours and minutes the user want to train
     * @param day the day for that training should be scheduled
     * @return Stringbuilder containing the text for the training schedule
     * @author Christina Meisoll
     */
    private static StringBuilder choose_training_string(int frequency, int[] time, String day){
        StringBuilder temp= new StringBuilder();
        if((frequency==1) && day.equals("Sat")){
            temp.append(make_training_string(time, day));
        }else if((frequency==2) && (day.equals("Tue")|| day.equals("Thu"))){
            temp.append(make_training_string(time, day));
        }else if((frequency==3) && (day.equals("Mon")|| day.equals("Wed")|| day.equals("Fri"))){
            temp.append(make_training_string(time, day));
        }
        return temp;
    }

    /**
     * Creates a Stringbuilder containing the training schedule for that day
     * @param time how long the user wants to study each time
     * @param day the day for which the schedule is valid
     * @return Stringbuilder containing the text for the training schedule
     * @author Christina Meisoll
     */
    private static StringBuilder make_training_string(int[] time, String day){
        StringBuilder temp= new StringBuilder();
        if (time[0] > 0 || time[1] > 0) {
            String start2 = (8 + 10) + ":00";
            String end2;
            if (time[1] == 0) {
                end2 = (8 + 10 + time[0]) + ":00";
            } else {
                end2 = (8 + 10 + time[0]) + ":" + time[1];
            }
            if(day.equals(today) && MainActivity.where_to_train!=null){
                temp.append(start2 + " - " + end2 +" "+MainActivity.where_to_train +" training\n");
            }else{
                temp.append(start2 + " - " + end2 +" training\n");
            }
        }
        return temp;
    }

    /**
     * Creates a Stringbuilder containing the study schedule
     * @param frequency how often the user want to study per day
     * @param time how long the user wants to study each time
     * @return Stringbuilder containing the text for the study schedule
     * @author Christina Meisoll
     */
    private static StringBuilder make_study_string(int frequency, int[] time){
        StringBuilder temp= new StringBuilder();
        int offset[] = {0,5,2,7};
        for (int i=0; i<frequency; i++){
            String start1=(8+offset[i])+":00";
            String end1;
            if (time[1] ==0){
                end1=(8+offset[i]+time[0])+":00";
            }else{
                end1=(8+offset[i]+time[0])+":"+time[1];
            }
            if (i!=2) {
                temp.append(start1 + " - " + end1 + " studytime\n");
            }else {
                temp.insert(temp.indexOf("13"), start1 + " - " + end1 + " studytime\n");
            }
        }
        return temp;
    }



    /**
     * Sets todays day of the week in form of the english 3-letter abbreviation, for example Tue for Tuesday.
     * @author Chrisitna Meisoll
     */
    static void set_weekday(){
        Calendar calendar = Calendar.getInstance();
        today = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.forLanguageTag("en_us"));
    }

    /**
     * Sets the users study preferences for scheduling
     * @param freq How often the user entered they want to study
     * @param dur How long the user entered they want study each time
     * @author Christina Meisoll
     */
    public static void set_studypref(int freq, int dur){
        study_frequency=freq;
        study_duration=dur*15;
    }

    /**
     * Sets the users training preferences for scheduling
     * @param freq How often the user entered they want to train
     * @param dur How long the user entered they want train each time
     * @author Christina Meisoll
     */
    public static void set_trainingpref(int freq, int dur){
        training_frequency=freq;
        training_duration=dur*30;
    }

    /**
     * Converts minutes to an array of hours and minutes
     * @param duration, the total time in minutes
     * @return an int[] where [0] gives amount of hours and [1] gives the amount of minutes
     * @author Christina Meisoll
     */
    private static int[] set_hours_and_minutes(int duration){
        int h=0;
        int[] temp=new int[2];
        while (duration >= 60){
            h++;
            duration-=60;
        }
        temp[0]=h;
        temp[1]=duration;
        return temp;
    }

    static String getToday(){
        return today;
    }
    public static int get_study_amount(){return study_frequency;}
    public static int get_study_duration(){return study_duration;}
    public static int get_training_amount(){return training_frequency;}
    public static int get_training_duration(){return training_duration;}






}