package com.example.bjlz.qianshan.tools.PermissionsManager;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * 6.0需要的权限类，需要主动提示用户是否允许
 * Created by slj on 2016/5/26.
 */
public class PerssionUtils {
    public static boolean checkPerssion(Context context, String perssion) {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(context,
                perssion);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    perssion)) {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{perssion},
                        123);
                return false;
            }
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{perssion},
                    123);
            return false;
        }
        return true;
    }
}