package com.firstapp.jakku;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.LinkedList;

public class Schema {
//study hours are between 8am and 8pm

    private static int session_amount;
    private static int session_duration;
    private static int training_amount;
    private static int training_duration;
  //  private StringBuilder[] dayschema = new StringBuilder[5];
    public static LinkedList<StringBuilder> dayschema = new LinkedList<>();


    public void make_schedule() {




            System.out.println("\n \n make_shedule\n\n");
            StringBuilder schema = new StringBuilder();
            int study_h;
            int study_min;
            int training_h;
            int training_min;
            int offset[] = {0, 5, 2, 7};
//        int offset[] = {0,5,10,2,7};

            study_h = 0;
            study_min = session_duration;
            training_h = 0;
            training_min = training_duration;

            //This loops 5 times, corresponding to each day in week.
            for (int i = 0; i < 5; i++) {

            while (study_min >= 60) {
                study_h++;
                study_min -= 60;
            }

            while (training_min >= 60) {
                training_h++;
                training_min -= 60;
            }

            System.out.println(session_amount + " is session_amount");
            for (int y = 0; y < session_amount; y++) {
                System.out.println("inne i studyloop");
                String start1 = (8 + offset[y]) + ":00";
                String end1;
                if (study_min == 0) {
                    end1 = (8 + offset[y] + study_h) + ":00";
                } else {
                    end1 = (8 + offset[y] + study_h) + ":" + study_min;

                }
                //schema.append(start1 + " - " + end1 + " studytime\n");

                schema.append(start1 + " - " + end1 + " studytime\n");

            }
            if ((training_h > 0 || training_min > 0) && (i != 1 && i != 3)) {
                for (int y = 0; y < training_amount; y++) {
                    System.out.println("inne i trainingloop");
                    String start2 = (8 + 10) + ":00";
                    String end2;
                    if (study_min == 0) {
                        end2 = (8 + 10 + study_h) + ":00";
                    } else {
                        end2 = (8 + 10 + study_h) + ":" + study_min;
                    }
                    // schema.append(start2 + " - " + end2 + " training\n");
                    schema.append(start2 + " - " + end2 + " training\n");

                }
            }
            //dayschema getting a stringbuilder each iteration
            dayschema.add(schema);

        }
        //return dayschema;
    }

    public StringBuilder get_scheduel(DayOfWeek day) {
        switch (day) {
            case MONDAY:
                System.out.println("Detta står i monday +" + dayschema.get(0).toString());
                return dayschema.get(0);
            case TUESDAY:
                System.out.println("Detta står i tue +" + dayschema.get(1).toString());
                return dayschema.get(1);
            case WEDNESDAY:
                System.out.println("Detta står i wen +" + dayschema.get(2).toString());
                return dayschema.get(2);
            case THURSDAY:
                System.out.println("Detta står i thu +" + dayschema.get(3).toString());
                return dayschema.get(3);
            case FRIDAY:
                System.out.println("Detta står i fri +" + dayschema.get(4).toString());
                return dayschema.get(4);
        }
        return dayschema.getFirst();
    }


    public static void set_studypref(int a, int b){
        session_amount=a;
        session_duration=b*15;
    }


    public static int get_study_amount(){
        System.out.println("\nAmount: "+session_amount+"\n");
        return session_amount;
    };
    public static int get_study_duration(){
        System.out.println("\nDuration: "+ session_duration+"\n");
        return session_duration;
    };

    public static void set_trainingpref(int a, int b){
        training_amount=a;
        training_duration=b*30;

    }
    public static int get_training_amount(){
        System.out.println("\nAmount: "+training_amount+"\n");
        return training_amount;
    };
    public static int get_training_duration(){
        System.out.println("\nDuration: "+ training_duration+"\n");
        return training_duration;
    };
}
