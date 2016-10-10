package com.example.bjlz.qianshan.tools.OtherTools.CatchTools;

import android.text.TextUtils;
import android.util.Log;

import com.example.bjlz.qianshan.tools.BitmapTools.FileTools.FileUtils;

import java.util.List;

/**
 * Created by slj  on 2016/5/11.
 */
public class LogUtils {

    private static final boolean isLog = true;
    private static final String TAG = "slj";

    /**
     * 用于记时的变量
     */
    private static long mTimestamp = 0;
    /**
     * 写文件的锁对象
     */
    private static final Object mLogLock = new Object();


    public static void debug(Object paramObject, String paramString) {
        if (isLog == true) {
            Log.d(paramObject.getClass().getSimpleName(), paramString);
        }
    }

    public static void debug(String paramString) {
        if (isLog == true) {
            Log.d(TAG, paramString);
        }
    }

    /**
     * 自定义tag,打印debug
     *
     * @param tag
     * @param paramString
     */
    public static void debug(String tag, String paramString) {
        if (isLog == true) {
            Log.d(tag, paramString);
        }
    }

    public static void debug(String paramString, Throwable paramThrowable) {
        if (isLog == true) {
            Log.d(TAG, paramString, paramThrowable);
        }
    }

    public static void error(Object paramObject, String paramString) {
        if (isLog == true) {
            Log.e(paramObject.getClass().getSimpleName(), paramString);
        }
    }

    public static void error(String paramString) {
        if (isLog == true) {
            Log.e(TAG, paramString);
        }
    }

    /**
     * 自定义tag 打印error
     *
     * @param tag
     * @param paramString
     */
    public static void error(String tag, String paramString) {
        if (isLog == true) {
            Log.e(tag, paramString);
        }
    }

    public static void error(String paramString, Throwable paramThrowable) {
        if (isLog == true) {
            Log.e(TAG, paramString, paramThrowable);
        }
    }

    public static void info(Object paramObject, String paramString) {
        if (isLog == true) {
            Log.i(paramObject.getClass().getSimpleName(), paramString);
        }
    }

    public static void info(String paramString) {
        if (isLog == true) {
            Log.i(TAG, paramString);
        }
    }

    /**
     * 自定义tag 打印info
     *
     * @param tag
     * @param paramString
     */
    public static void info(String tag, String paramString) {
        if (isLog == true) {
            Log.i(tag, paramString);
        }
    }

    /**
     * 把Log存储到文件中
     *
     * @param log  需要存储的日志
     * @param path 存储路径
     */
    public static void log2File(String log, String path) {
        log2File(log, path, true);
    }

    /**
     * 把Log存储到文件中
     *
     * @param log
     * @param path
     * @param append
     */
    public static void log2File(String log, String path, boolean append) {
        synchronized (mLogLock) {
            FileUtils.writeFile(log + "\r\n", path, append);
        }
    }

    /**
     * 以级别为 e 的形式输出msg信息,附带时间戳，用于输出一个时间段起始点
     *
     * @param msg 需要输出的msg
     */
    public static void msgStartTime(String msg) {
        mTimestamp = System.currentTimeMillis();
        if (!TextUtils.isEmpty(msg)) {
            error("[Started：" + mTimestamp + "]" + msg);
        }
    }

    /**
     * 以级别为 e 的形式输出msg信息,附带时间戳，用于输出一个时间段结束点* @param msg 需要输出的msg
     */
    public static void elapsed(String msg) {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - mTimestamp;
        mTimestamp = currentTime;
        error("[Elapsed：" + elapsedTime + "]" + msg);
    }

    /**
     * 级别为 e 的形式打印list集合
     *
     * @param list
     * @param <T>
     */
    public static <T> void printList(List<T> list) {
        if (list == null || list.size() < 1) {
            return;
        }
        int size = list.size();
        error("---begin---");
        for (int i = 0; i < size; i++) {
            error(i + ":" + list.get(i).toString());
        }
        error("---end---");
    }

    /**
     * 级别为 e 的形式打印通用集合
     *
     * @param array
     * @param <T>
     */
    public static <T> void printArray(T[] array) {
        if (array == null || array.length < 1) {
            return;
        }
        int length = array.length;
        error("---begin---");
        for (int i = 0; i < length; i++) {
            error(i + ":" + array[i].toString());
        }
        error("---end---");
    }
}
