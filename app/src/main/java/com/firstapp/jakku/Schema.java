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


    public static String make_schedule(){
        System.out.println("---Schema make_schedule_for_any_day ---");
        StringBuilder schema=new StringBuilder();
        int[] study_time = new int[2];
        int[] training_time = new int[2];

        int offset[] = {0,5,2,7};

        study_time = set_hours_and_minutes(study_duration);
        training_time = set_hours_and_minutes(training_duration);


        schema.append(today+": \n");
        schema.append(make_study_string(study_frequency,study_time));
        schema.append(choose_training_string(training_frequency, training_time, today));


        System.out.println("---End Schema make_schedule_for_any_day---");
        return schema.toString();
    }


    public static String schedulemaker(String button_day){
        System.out.println("---Schema make_schedule_for_any_day ---");
        StringBuilder schema=new StringBuilder();
        int[] study_time = new int[2];
        int[] training_time = new int[2];

        int offset[] = {0,5,2,7};

        study_time = set_hours_and_minutes(study_duration);
        training_time = set_hours_and_minutes(training_duration);


        schema.append(button_day+": \n");
        schema.append(make_study_string(study_frequency,study_time));
        schema.append(choose_training_string(training_frequency, training_time, button_day));


        System.out.println("---End Schema make_schedule_for_any_day---");
        return schema.toString();
    }

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



    public static void set_studypref(int freq, int dur){
        study_frequency=freq;
        study_duration=dur*15;
    }
    public static void set_trainingpref(int freq, int dur){
        training_frequency=freq;
        training_duration=dur*30;
        System.out.println("training_duration är: "+training_duration);
    }
    public static int get_study_amount(){return study_frequency;};
    public static int get_study_duration(){return study_duration;};
    public static int get_training_amount(){return training_frequency;};
    public static int get_training_duration(){return training_duration;};


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

    //this gives only the weekday, always converted to 3-letter abbreviation in english, t.ex "Tue"
    static void set_weekday(){
        Calendar calendar = Calendar.getInstance();
        String dayLongName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.forLanguageTag("en_us"));
        System.out.println("\n\nset weekday today = "+dayLongName+"\n\n");
        today = dayLongName;
    }

    static String getToday(){
        return today;
    }

/*    public static String make_schedule(){
        System.out.println("---Schema make_shedule ---");
        StringBuilder schema=new StringBuilder();
        int[] study_time = new int[2];
        int[] training_time = new int[2];

        int offset[] = {0,5,2,7};

        study_time = set_hours_and_minutes(study_duration);
        training_time = set_hours_and_minutes(training_duration);

        schema.append("Today:\n");

        for (int i=0; i<study_frequency; i++){
            String start1=(8+offset[i])+":00";
            String end1;
            if (study_time[1] ==0){
               end1=(8+offset[i]+study_time[0])+":00";
            }else{
                end1=(8+offset[i]+study_time[0])+":"+study_time[1];
            }
            if (i!=2) {
                schema.append(start1 + " - " + end1 + " studytime\n");
            }else {
                schema.insert(schema.indexOf("13"), start1 + " - " + end1 + " studytime\n");
            }
        }


        if(training_frequency==2){
            if(today.equals("Tue")|| today.equals("Thu")) {
                if (training_time[0]>0 || training_time[1]>0) {

                        String start2 = (8 + 10) + ":00";
                        String end2;
                        if (training_time[1] == 0) {
                            end2 = (8 + 10 + training_time[0]) + ":00";
                        } else {
                            end2 = (8 + 10 + training_time[0]) + ":" + training_time[1];
                        }
                    if (MainActivity.where_to_train==null) {
                        schema.append(start2 + " - " + end2 +" training\n");
                    } else {
                        schema.append(start2 + " - " + end2 + " " + MainActivity.where_to_train + " training\n");
                    }
                    }
            }
        }
        else if(training_frequency==3){
            if((today.equals("Mon")|| today.equals("Wed")|| today.equals("Fri"))) {
                if (training_time[0]>0 || training_time[1]>0) {
                    String start2 = (18) + ":00";
                    String end2;
                    if (training_time[1] == 0) {
                        end2 = (18 + training_time[0]) + ":00";
                    } else {
                        end2 = (18 + training_time[0]) + ":" + training_time[1];
                    }
                    if (MainActivity.where_to_train==null) {
                        schema.append(start2 + " - " + end2 +" training\n");
                    } else {
                        schema.append(start2 + " - " + end2 + " " + MainActivity.where_to_train + " training\n");
                    }
                }
            }
        }
        else if(training_frequency==1) {
            if (today.equals("Sat")) {
                if (training_time[0] > 0 || training_time[1] > 0) {
                    String start2 = (18) + ":00";
                    String end2;
                    if (training_time[1] == 0) {
                        end2 = (18 + training_time[0]) + ":00";
                    } else {
                        end2 = (18 + training_time[0]) + ":" + training_time[1];
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

 */


/*    public static String make_schedule_for_any_day(String button_day){
        System.out.println("---Schema make_schedule_for_any_day ---");
        StringBuilder schema=new StringBuilder();
        int[] study_time = new int[2];
        int[] training_time = new int[2];

        int offset[] = {0,5,2,7};

        study_time = set_hours_and_minutes(study_duration);
        training_time = set_hours_and_minutes(training_duration);


        schema.append(button_day+": \n");

        for (int i=0; i<study_frequency; i++){
            String start1=(8+offset[i])+":00";
            String end1;
            if (study_time[1] ==0){
                end1=(8+offset[i]+study_time[0])+":00";
            }else{end1=(8+offset[i]+study_time[0])+":"+study_time[1];

            }
            if (i!=2) {
                schema.append(start1 + " - " + end1 + " studytime\n");
            }else {
                schema.insert(schema.indexOf("13"), start1 + " - " + end1 + " studytime\n");
            }
        }

        if(training_frequency==2){
            if(button_day.equals("Tue")|| button_day.equals("Thu")) {
                if (training_time[0]>0 || training_time[1]>0) {

                    String start2 = (8 + 10) + ":00";
                    String end2;
                    if (training_time[1] == 0) {
                        end2 = (8 + 10 + training_time[0]) + ":00";
                    } else {
                        end2 = (8 + 10 + training_time[0]) + ":" + training_time[1];
                    }
                    if(button_day.equals(today)){
                        schema.append(start2 + " - " + end2 +" "+MainActivity.where_to_train +" training\n");
                    }else{
                        schema.append(start2 + " - " + end2 +" training\n");
                    }
                }
            }
        }
        else if(training_frequency==3){
            if((button_day.equals("Mon")|| button_day.equals("Wed")|| button_day.equals("Fri"))) {
                if (training_time[0]>0 || training_time[1]>0) {
                    String start2 = (8 + 10) + ":00";
                    String end2;
                    if (training_time[1] == 0) {
                        end2 = (8 + 10 + training_time[0]) + ":00";
                    } else {
                        end2 = (8 + 10 + training_time[0]) + ":" + training_time[1];
                    }
                   if(today.equals(button_day)){
                        schema.append(start2 + " - " + end2 +" "+MainActivity.where_to_train +" training\n");
                    }else{
                    schema.append(start2 + " - " + end2 +" training  in old method\n");
                    }

                }
            }
        }
        else if(training_frequency==1) {
            if (button_day.equals("Sat")) {
                if (training_time[0] > 0 || training_time[1] > 0) {
                    String start2 = (8 + 10) + ":00";
                    String end2;
                    if (training_time[1] == 0) {
                        end2 = (8 + 10 + training_time[0]) + ":00";
                    } else {
                        end2 = (8 + 10 + training_time[0]) + ":" + training_time[1];
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
*/


}