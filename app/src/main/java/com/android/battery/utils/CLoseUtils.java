package com.android.battery.utils;

import java.io.Closeable;
import java.io.IOException;

public class CLoseUtils {
    public static void safeColse(Closeable closeable){
        if (null != closeable){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
