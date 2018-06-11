package com.amazon.alexa.avs.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ConsoleLogger {
    private static final Logger log = LoggerFactory.getLogger(ConsoleLogger.class);

    public static void print(String tag, String msg) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
        String time = sdf.format(calendar.getTime()) + String.format(".%03d ", calendar.get(Calendar.MILLISECOND));
        System.out.println(time + " [" + tag + "] " + msg);

        log.info(" [" + tag + "] " + msg);
    }

    public static void print(String tag, String msg, Exception e) {
        print(tag, msg + " " + e.getMessage());
    }

    public static void print(String tag, String msg, Throwable e) {
        print(tag, msg + " " + e.getMessage());
    }
}
