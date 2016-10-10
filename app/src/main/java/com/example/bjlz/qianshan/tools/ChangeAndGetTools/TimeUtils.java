package com.example.bjlz.qianshan.tools.ChangeAndGetTools;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间处理工具
 * Created by Nereo on 2015/4/8.
 */
public class TimeUtils {
    /**
     * 获取时间
     * @param timeMillis
     * @param pattern
     * @return
     */
    public static String timeFormat(long timeMillis, String pattern){
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        return format.format(new Date(timeMillis));
    }

    /**
     * 获取照片的时间
     * @param time
     * @param timeType
     * @return
     */
    public static String formatPhotoDate(long time,String timeType){
        return timeFormat(time, timeType);
    }

    /**
     * 获取照片时间排列顺序
     * @param path
     * @param timeType
     * @return
     */
    public static String formatPhotoDate(String path,String timeType){
        File file = new File(path);
        if(file.exists()){
            long time = file.lastModified();
            return formatPhotoDate(time,timeType);
        }
        return "1970-01-01";
    }
}