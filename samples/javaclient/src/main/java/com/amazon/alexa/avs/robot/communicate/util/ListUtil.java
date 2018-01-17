package com.amazon.alexa.avs.robot.communicate.util;

import java.util.Iterator;
import java.util.List;

public class ListUtil {
    public static <T> void iterate(List<T> listeners, IteratorCallback<T> callback) {
        if (listeners != null && callback != null) {
            Iterator<T> iterator = listeners.iterator();
            while (iterator.hasNext()) {
                callback.onNext(iterator.next());
            }
        }
    }

    public static  <T> void add(List<T> listeners, T listener) {
        if (listeners != null && listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public static  <T> void remove(List<T> listeners, T listener) {
        if (listeners != null && listener != null & listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }
}
