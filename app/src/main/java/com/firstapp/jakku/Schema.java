package com.firstapp.jakku;

public class Schema {
//study hours are between 8am and 8pm

    private static int session_amount;
    private static int session_duration;


    public static String make_schedule(){
        System.out.println("\n \n make_shedule\n\n");
        StringBuilder schema=new StringBuilder();
        int h;
        int min;
        int offset[] = {0,5,10,2,7};

        h=0;
        min =session_duration;
 //       schema = "";

        while (min >= 60){
            h++;
            min-=60;
        }
        System.out.println(session_amount+" is session_amount");
        for (int i=0; i<session_amount; i++){
            System.out.println("inne i forloop");
            String start=(8+offset[i])+":00";
            String end;
            if (min ==0){
               end=(8+offset[i]+h)+":00";
            }else{end=(8+offset[i]+h)+":"+min;

            }
            schema.append(start+" - "+end+" studytime\n");
//            schema.append((8+offset[i])+":00 - "+(8+offset[i]+h)+":"+min+" studytime\n");
 //           System.out.println(schema.toString());
        }
 //       System.out.println(schema);

        return schema.toString();
    }



    public static void set_studypref(int a, int b){
        session_amount=a;
        session_duration=b*15;
//        System.out.println("!!!!!!!!!!!!!!!!studypref set!!!!!!!!!!!!!!!!!!!");
//        System.out.println("session_amount = "+session_amount);
//        System.out.println("session_duration = "+session_duration);
    }


    public static int get_study_amount(){
        System.out.println("\nAmount: "+session_amount+"\n");
        return session_amount;
    };
    public static int get_study_duration(){
        System.out.println("\nDuration: "+ session_duration+"\n");
        return session_duration;
    };
}
