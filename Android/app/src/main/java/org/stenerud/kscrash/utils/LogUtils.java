package org.stenerud.kscrash.utils;

import android.util.Log;


/**
 * 日志的工具类
 * @author Jack
 */
public class LogUtils {

    /**
     * 是否显示日志
     */
    private static boolean LOG = true;


    public static void v(String tag, String msg) {
        if (LOG) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (LOG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (LOG) {
            Log.i(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LOG) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (LOG) {
            Log.e(tag, msg, tr);
        }
    }


}
