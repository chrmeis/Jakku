package com.firstapp.jakku;

import static java.sql.Types.NULL;

import android.os.Handler;
import android.os.Looper;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Schema {
//study hours are between 8am and 5pm

    private static int session_amount;
    private static int session_duration;
    private static int training_amount;
    private static int training_duration;
    private static String today;


    public static String make_schedule(){
        System.out.println("---Schema make_shedule ---");
        StringBuilder schema=new StringBuilder();
        int study_h;
        int study_min;
        int training_h;
        int training_min;


        int offset[] = {0,5,2,7};
//        int offset[] = {0,5,10,2,7};

        study_h=0;
        study_min =session_duration;
        training_h=0;
        training_min =training_duration;
        System.out.println("training_min är:"+training_min);



 //       make_to_hours_and_minutes(study_min, study_h);
 //       System.out.println("Study_h:study_min"+study_h+":"+study_min);
 //       make_to_hours_and_minutes(training_min, training_h);
 //       System.out.println("training_h:training_min"+training_h+":"+training_min);

        while (study_min >= 60){
            study_h++;
            study_min-=60;
        }
        while (training_min >= 60){
            training_h++;
            training_min-=60;
        }

        schema.append("Today:\n");

        for (int i=0; i<session_amount; i++){
            String start1=(8+offset[i])+":00";
            String end1;
            if (study_min ==0){
               end1=(8+offset[i]+study_h)+":00";
            }else{
                end1=(8+offset[i]+study_h)+":"+study_min;
            }
            if (i!=2) {
                schema.append(start1 + " - " + end1 + " studytime\n");
            }else {
                schema.insert(schema.indexOf("13"), start1 + " - " + end1 + " studytime\n");
            }
        }

        if(training_amount==2){
            if(today.equals("Tue")|| today.equals("Thu")) {
                if (training_h>0 || training_min>0) {

                        String start2 = (8 + 10) + ":00";
                        String end2;
                        if (study_min == 0) {
                            end2 = (8 + 10 + study_h) + ":00";
                        } else {
                            end2 = (8 + 10 + study_h) + ":" + study_min;
                        }
                    if (MainActivity.where_to_train==null) {
                        schema.append(start2 + " - " + end2 +" training\n");
                    } else {
                        schema.append(start2 + " - " + end2 + " " + MainActivity.where_to_train + " training\n");
                    }
                    }
            }
        }
        else if(training_amount==3){
            if((today.equals("Mon")|| today.equals("Wed")|| today.equals("Fri"))) {
                if (training_h>0 || training_min>0) {
                    String start2 = (8 + 10) + ":00";
                    String end2;
                    if (training_min == 0) {
                        System.out.println("8 + 10 + study_h: " + (8 + 10 + training_h));
                        end2 = (8 + 10 + training_h) + ":00";
                    } else {
                        end2 = (8 + 10 + training_h) + ":" + training_min;
                    }
                    if (MainActivity.where_to_train==null) {
                        schema.append(start2 + " - " + end2 +" training\n");
                    } else {
                        schema.append(start2 + " - " + end2 + " " + MainActivity.where_to_train + " training\n");
                    }
                }
            }
        }
        else if(training_amount==1) {
            if (today.equals("Sat")) {
                if (training_h > 0 || training_min > 0) {
                    String start2 = (8 + 10) + ":00";
                    String end2;
                    if (training_min == 0) {
                        end2 = (8 + 10 + training_h) + ":00";
                    } else {
                        end2 = (8 + 10 + training_h) + ":" + training_min;
                    }
                    if (MainActivity.where_to_train==null) {
                        schema.append(start2 + " - " + end2 +" training\n");
                    } else {
                        schema.append(start2 + " - " + end2 + " " + MainActivity.where_to_train + " training\n");
                    }
                }
            }
        }
        System.out.println("---End Schema make_schedule---");
            return schema.toString();
    }



    public static void set_studypref(int freq, int dur){
        session_amount=freq;
        session_duration=dur*15;
    }
    public static int get_study_amount(){
        return session_amount;
    };
    public static int get_study_duration(){
        return session_duration;
    };

    public static void set_trainingpref(int freq, int dur){
        training_amount=freq;
        training_duration=dur*30;
        System.out.println("training_duration är: "+training_duration);
    }

    public static int get_training_amount(){
        return training_amount;
    };
    public static int get_training_duration(){
        return training_duration;
    };

    //this gives only the weekday, always converted to 3-letter abbreviation in english, t.ex "Tue"
    static void set_weekday(){
        Calendar calendar = Calendar.getInstance();
        String dayLongName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.forLanguageTag("en_us"));
        System.out.println("\n\nset weekday today = "+dayLongName+"\n\n");
        today = dayLongName;
    }

    public static String make_schedule_for_any_day(String button_day){
        System.out.println("---Schema make_schedule_for_any_day ---");
        StringBuilder schema=new StringBuilder();
        int study_h;
        int study_min;
        int training_h;
        int training_min;
        int offset[] = {0,5,2,7};
//        int offset[] = {0,5,10,2,7};

        study_h=0;
        study_min =session_duration;
        training_h=0;
        training_min =training_duration;


//        make_to_hours_and_minutes(study_min, study_h);
//        make_to_hours_and_minutes(training_min, training_h);

        schema.append(button_day+": \n");

        for (int i=0; i<session_amount; i++){
            String start1=(8+offset[i])+":00";
            String end1;
            if (study_min ==0){
                end1=(8+offset[i]+study_h)+":00";
            }else{end1=(8+offset[i]+study_h)+":"+study_min;

            }
            schema.append(start1+" - "+end1+" studytime\n");
        }

        if(training_amount==2){
            if(button_day.equals("Tue")|| button_day.equals("Thu")) {
                if (training_h>0 || training_min>0) {

                    String start2 = (8 + 10) + ":00";
                    String end2;
                    if (study_min == 0) {
                        end2 = (8 + 10 + study_h) + ":00";
                    } else {
                        end2 = (8 + 10 + study_h) + ":" + study_min;
                    }
                    if(button_day.equals(today)){
                        schema.append(start2 + " - " + end2 +" "+MainActivity.where_to_train +" training\n");
                    }else{
                        schema.append(start2 + " - " + end2 +" training\n");
                    }
                }
            }
        }
        else if(training_amount==3){
            if((button_day.equals("Mon")|| button_day.equals("Wed")|| button_day.equals("Fri"))) {
                if (training_h>0 || training_min>0) {
                    String start2 = (8 + 10) + ":00";
                    String end2;
                    if (training_min == 0) {
                        System.out.println("8 + 10 + study_h: "+(8 + 10 + training_h));
                        end2 = (8 + 10 + training_h) + ":00";
                    } else {
                        end2 = (8 + 10 + training_h) + ":" + training_min;
                    }
                    System.out.println("\n\ntoday: "+today);
                    System.out.println("buttonday: "+button_day+"\n\n");
                    if(today.equals(button_day)){
                        schema.append(start2 + " - " + end2 +" "+MainActivity.where_to_train +" training\n");
                    }else{
                    schema.append(start2 + " - " + end2 +" training\n");
                    }

                }
            }
        }
        else if(training_amount==1) {
            if (button_day.equals("Sat")) {
                if (training_h > 0 || training_min > 0) {
                    String start2 = (8 + 10) + ":00";
                    String end2;
                    if (training_min == 0) {
                        end2 = (8 + 10 + training_h) + ":00";
                    } else {
                        end2 = (8 + 10 + training_h) + ":" + training_min;
                    }
                    if(button_day.equals(today)){
                        schema.append(start2 + " - " + end2 +" "+MainActivity.where_to_train +" training\n");
                    }else{
                        schema.append(start2 + " - " + end2 +" training\n");
                    }
                }
            }
        }
        System.out.println("---End Schema make_schedule_for_any_day---");
        return schema.toString();
    }

}