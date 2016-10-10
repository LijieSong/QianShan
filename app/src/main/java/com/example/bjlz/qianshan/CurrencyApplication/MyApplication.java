package com.example.bjlz.qianshan.CurrencyApplication;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.multidex.MultiDex;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.SDKInitializer;
import com.example.bjlz.qianshan.CurrencyBase.DemoHelper;
import com.example.bjlz.qianshan.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import org.xutils.BuildConfig;
import org.xutils.x;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import okhttp3.OkHttpClient;

/**
 * 项目名称：QianShanDoctor
 * 类描述：MyApplication 全局变量
 * 创建人：slj
 * 创建时间：2016-6-27 20:06
 * 修改人：slj
 * 修改时间：2016-6-27 20:06
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class MyApplication extends Application {

    public static Map<String, Long> map;//用来存放倒计时的时间
    private List<Activity> activityList = new LinkedList<Activity>();//activity管理器
    private static Handler mainHandler = null;
    private static MyApplication mApplication;//application
    public static RequestQueue queue;//请求队列
    private static OkHttpClient mOkHttpClient;//okHttpClient对象
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        //自动分包
        MultiDex.install(this);
        //图片
        Fresco.initialize(this);
        //网络
        queue = Volley.newRequestQueue(this);
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
        // 创建okHttpClient对象
        mOkHttpClient = new OkHttpClient();
        //初始化EaseUi
        DemoHelper.getInstance().init(this);
        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        //初始化
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().init(this, options);
//        //初始化  easeui
        EaseUI.getInstance().init(this, options);
        EMClient.getInstance().setDebugMode(true);
    }

    /**
     * 获取OkHttpClient对象
     * @return
     */
    public static OkHttpClient GetOkHttpClient() {
        return mOkHttpClient;
    }
    /**
     * 获取请求队列
     * @return
     */
    public static RequestQueue getHttpQueue() {
        return queue;
    }
    /**
     * 获取全局变量
     *
     * @return
     */
    public static MyApplication getInstance() {
        return mApplication;
    }


    public static Handler getHandler() {
        return mainHandler;
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
     * 跳转指定Action 例如打电话
     * @param context
     * @param actionCall actionCall地址
     * @param parse uri
     * @param <T> 例如打电话
     */
    public static <T> void startActivity(Context context,String actionCall, Uri parse) {
        Intent intent = new Intent(actionCall,parse);
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
    public static <T> void startActivityForResult(Activity activity, Class<T> clazz,
                                                  int requestCode) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.cuntomanim, R.anim.cunexitmanim);
    }

    /**
     * 添加Activity到容器中
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 关闭所有activity
     */
    public void close() {
        for (Activity activity : activityList) {
            if (activity != null) {
                activity.finish();
            }
        }
        activityList = new LinkedList<Activity>();
    }

    /**
     * 移除activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        for (Activity ac : activityList) {
            if (ac.equals(activity)) {
                activityList.remove(ac);
                break;
            }
        }
    }

}
