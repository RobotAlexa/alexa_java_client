package com.amazon.alexa.avs.log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ConsoleLogger {

    public static void print(String tag, String msg) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
        String time = sdf.format(calendar.getTime()) + String.format(".%03d ", calendar.get(Calendar.MILLISECOND));
        System.out.println(time + " [" + tag + "] " + msg);
    }

    public static void print(String tag, String msg, Exception e) {
        print(tag, msg + " " + e.getMessage());
    }

    public static void print(String tag, String msg, Throwable e) {
        print(tag, msg + " " + e.getMessage());
    }
}
