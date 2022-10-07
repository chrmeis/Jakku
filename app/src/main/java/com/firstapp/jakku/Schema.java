package com.firstapp.jakku;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

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


        while (study_min >= 60){
            study_h++;
            study_min-=60;
        }

        while (training_min >= 60){
            training_h++;
            training_min-=60;
        }

        schema.append("Today:\n\n");

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
            if(today.equals("Tue")|| today.equals("Thu")) {
                if (training_h>0 || training_min>0) {

                        String start2 = (8 + 10) + ":00";
                        String end2;
                        if (study_min == 0) {
                            end2 = (8 + 10 + study_h) + ":00";
                        } else {
                            end2 = (8 + 10 + study_h) + ":" + study_min;
                        }
                        schema.append(start2 + " - " + end2 + " training\n");
                    }
            }
        }
        else if(training_amount==3){
            if((today.equals("Mon")|| today.equals("Wed")|| today.equals("Fri"))) {
                if (training_h>0 || training_min>0) {
                        String start2 = (8 + 10) + ":00";
                        String end2;
                        if (training_min == 0) {
                            System.out.println("8 + 10 + study_h: "+(8 + 10 + training_h));
                            end2 = (8 + 10 + training_h) + ":00";
                        } else {
                            end2 = (8 + 10 + training_h) + ":" + training_min;
                        }
                        schema.append(start2 + " - " + end2 + " training\n");
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
                    schema.append(start2 + " - " + end2 +" training\n");
                }
            }
        }
        System.out.println("---End Schema make_schedule---");
            return schema.toString();
    }



    public static void set_studypref(int a, int b){
        session_amount=a;
        session_duration=b*15;
    }
    public static int get_study_amount(){
        return session_amount;
    };
    public static int get_study_duration(){
        return session_duration;
    };

    public static void set_trainingpref(int a, int b){
        training_amount=a;
        training_duration=b*30;

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
//        System.out.println("\ndayLongName = "+dayLongName+"\n");
        today = dayLongName;
    }

    public static String make_schedule_for_any_day(String today){
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


        while (study_min >= 60){
            study_h++;
            study_min-=60;
        }

        while (training_min >= 60){
            training_h++;
            training_min-=60;
        }

        schema.append(today+": \n");

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
            if(today.equals("Tue")|| today.equals("Thu")) {
                if (training_h>0 || training_min>0) {

                    String start2 = (8 + 10) + ":00";
                    String end2;
                    if (study_min == 0) {
                        end2 = (8 + 10 + study_h) + ":00";
                    } else {
                        end2 = (8 + 10 + study_h) + ":" + study_min;
                    }
                    schema.append(start2 + " - " + end2 + " training\n");
                }
            }
        }
        else if(training_amount==3){
            if((today.equals("Mon")|| today.equals("Wed")|| today.equals("Fri"))) {
                if (training_h>0 || training_min>0) {
                    String start2 = (8 + 10) + ":00";
                    String end2;
                    if (training_min == 0) {
                        System.out.println("8 + 10 + study_h: "+(8 + 10 + training_h));
                        end2 = (8 + 10 + training_h) + ":00";
                    } else {
                        end2 = (8 + 10 + training_h) + ":" + training_min;
                    }
                    schema.append(start2 + " - " + end2 + " training\n");
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
                    schema.append(start2 + " - " + end2 +" training\n");
                }
            }
        }
        System.out.println("---End Schema make_schedule_for_any_day---");
        return schema.toString();
    }


}
