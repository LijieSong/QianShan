/*
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         佛祖保佑       永无BUG
*/
//          佛曰:
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱；

package com.example.bjlz.qianshan.tools.AppManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.bjlz.qianshan.R;

/**
 * 类描述：ActivityOrServiceStartUtils activity和service跳转管理类
 * 创建人：slj
 * 创建时间：2016-9-20 16:15
 * 修改人：slj
 * 修改时间：2016-9-20 16:15
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class ActivityOrServiceStartUtils {
    public ActivityOrServiceStartUtils() {
    }
    /**
     * 无参跳转
     *
     * @param activity
     * @param clazz
     */
    public static <T> void startActivity(Activity activity, Class<T> clazz) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.cuntomanim, R.anim.cunexitmanim);
    }
    /**
     * 无参跳转
     *
     * @param context
     * @param clazz
     */
    public static <T> void startActivity(Context context, Class<T> clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    /**
     * 有参跳转  传递bundle
     *
     * @param activity
     * @param clazz
     * @param bundle
     */
    public static <T> void startActivity(Activity activity, Class<T> clazz, Bundle bundle) {
        Intent intent = new Intent(activity, clazz);
        intent.putExtra("bundle", bundle);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.cuntomanim, R.anim.cunexitmanim);
    }
    /**
     * 有参跳转  传递bundle
     *
     * @param context
     * @param clazz
     * @param bundle
     */
    public static <T> void startActivity(Context context, Class<T> clazz, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    /**
     * 开启服务
     *
     * @param activity
     * @param serviceClazz
     */
    public static <T> void startService(Activity activity, Class<T> serviceClazz) {
        Intent intent = new Intent(activity, serviceClazz);
        activity.startService(intent);
    }

    /**
     * 停止服务
     *
     * @param activity
     * @param serviceClazz
     */
    public static <T> void stopService(Activity activity, Class<T> serviceClazz) {
        Intent intent = new Intent(activity, serviceClazz);
        activity.stopService(intent);

    }

    /**
     * 带请求码的跳转方式
     *
     * @param activity
     * @param clazz
     * @param requestCode
     */
    public static <T> void startActivityForResult(Activity activity, Class<T> clazz, int requestCode) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.cuntomanim, R.anim.cunexitmanim);
    }

}
