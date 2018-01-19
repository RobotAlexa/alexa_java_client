package com.amazon.alexa.avs.robot.communicate.util;

public class ExceptionUtils {
    private static final String TAG = "Socket";

    public static void throwException(String message) {
        throw new IllegalStateException(TAG + " : " + message);
    }
}

