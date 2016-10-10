package com.example.bjlz.qianshan.tools.ChangeAndGetTools;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.tools.OtherTools.UITools.ToastUtil;

/**
 * 封装一些零碎的工具方法
 * @author Administrator
 *
 */
public class CommonUtil {
	/**
	 * 获取主线程的handler
	 */
	public static Handler getHandler() {
		return MyApplication.getHandler();
	}
	/**
	 * 在主线程执行Runnable
	 * @param r
	 */
	public static void runOnUIThread(Runnable r){
		getHandler().post(r);
	}

	/**
	 * 延时在主线程执行runnable
	 * @param r
	 * @param times
     */
	public static void runOnUIThreadDelayed(Runnable r,long times){
		getHandler().postDelayed(r,times);
	}
	/**
	 * 从主线程looper里面移除runnable
	 * @param runnable
     */
	public static void removeCallbacks(Runnable runnable) {
		getHandler().removeCallbacks(runnable);
	}
	/**
	 * 在主线程执行的吐司
	 * @param string
	 */
	public static void runOnUIThreadToast(final Context context, final String string){
		getHandler().post(new Runnable() {
			@Override
			public void run() {
				ToastUtil.shortDiyToast(context,string);
			}
		});
	}
	/**
	 * 在主线程执行的吐司
	 * @param StrResId
	 */
	public static void runOnUIThreadToast(final Context context, final int StrResId){
		getHandler().post(new Runnable() {
			@Override
			public void run() {
				ToastUtil.shortDiyToastByRec(context,StrResId);
			}
		});
	}
	/**
	 * 将自己从父view移除
	 * @param child
	 */
	public static void removeSelfFromParent(View child){
		if(child!=null){
			ViewParent parent = child.getParent();
			if(parent instanceof ViewGroup){
				ViewGroup group = (ViewGroup) parent;
				group.removeView(child);//移除子view
			}
		}
	}
	/**
	 * 请求View树重新布局，用于解决中层View有布局状态而导致上层View状态断裂
	 * @param view
	 * @param isAll
     */
	public static void requestLayoutParent(View view, boolean isAll) {
		ViewParent parent = view.getParent();
		while (parent != null && parent instanceof View) {
			if (!parent.isLayoutRequested()) {
				parent.requestLayout();
				if (!isAll) {
					break;
				}
			}
			parent = parent.getParent();
		}
	}
	/**
	 * 判断触点是否落在该View上
	 */
	public static boolean isTouchInView(MotionEvent ev, View v) {
		int[] vLoc = new int[2];
		v.getLocationOnScreen(vLoc);
		float motionX = ev.getRawX();
		float motionY = ev.getRawY();
		return motionX >= vLoc[0] && motionX <= (vLoc[0] + v.getWidth()) && motionY >= vLoc[1] && motionY <= (vLoc[1] + v.getHeight());
	}

	/**
	 * FindViewById的泛型封装，减少强转代码
	 */
	public static <T extends View> T findViewById(View layout, int id) {
		return (T) layout.findViewById(id);
	}

	/**
	 * 对inflate的封装,减少代码强转
	 * @param context
	 * @param resId
     * @return
     */
	public static View inflate(Context context, int resId) {
		return LayoutInflater.from(context).inflate(resId, null);
	}
	/**
	 * 给textview添加下划线的方法
	 * @param textView
     */
	public static void tvUnderLine(TextView textView) {
		textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
		textView.getPaint().setAntiAlias(true);//抗锯齿
	}
	/**
	 * 获取Resource对象
	 * @return Resources
	 */
	public static Resources getResources(){
		return MyApplication.getInstance().getResources();
	}

	/**
	 * 获取字符串的资源
	 * @param resId
	 * @return String
	 */
	public static String getString(int resId){
		return getResources().getString(resId);
	}

	/**
	 * 获取字符串数组的资源
	 * @param resId
	 * @return String[]
	 */
	public static String[] getStringArray(int resId){
		return getResources().getStringArray(resId);
	}

	/**
	 * 获取图片资源
	 * @param resId
	 * @return Drawable
	 */
	public static Drawable getDrawable(int resId){
		return getResources().getDrawable(resId);
	}
	/**
	 * 获取dp资源
	 * @param resId
	 * @return float
	 */
	public static float getDimen(int resId){
		//会自动将dp值转为像素值
		return getResources().getDimension(resId);
	}

	/**
	 * 获取颜色资源
	 * @param resId
	 * @return 颜色
	 */
	public static int getColor(int resId){
		return getResources().getColor(resId);
	}

	/**
	 * 获取颜色选择器
	 * @param context
	 * @param resId
     * @return
     */
	public static ColorStateList getColorStateList(Context context, int resId) {
		return context.getResources().getColorStateList(resId);
	}

	public static Thread getMainThread() {
		return Thread.currentThread() ;
	}

	/**
	 * 获取主线程ID
	 * @return
     */
	public static int getMainThreadId() {
		return android.os.Process.myPid();
	}
	/**
	 * 获取deviceId
	 * @param context
	 * @return deviceId
     */
	public static String getDeviceId(Context context) {
		// 获取系统管理者
		final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	/**
	 * 获取版本号
	 * @param context
	 * @return
     */
	public static String getVersion(Context context)// 获取版本号
	{
		PackageInfo pi = null;
		try {
			pi = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return pi.versionName;
	}
	/**
	 * 获取手机型号
	 * @return 手机型号
     */
	public static String getBuildBrand(){
		 String MIUI = "Xiaomi";
		 String FLYME = "Meizu";
		 String HUAWEI = "HUAWEI";
		String QIHU = "360";
		String result = null;
		String brand = android.os.Build.BRAND;//手机品牌
		String model = android.os.Build.MANUFACTURER;// CPU厂商

		if (brand.equals(MIUI) || model.equals(MIUI)){
			result = MIUI;
		} else if (brand.equals(FLYME)|| model.equals(FLYME)){
			result = FLYME;
		} else if (brand.equals(HUAWEI)|| model.equals(HUAWEI)){
			result = HUAWEI;
		}else if (brand.equals(QIHU)|| model.equals(QIHU)){
			result = QIHU;
		}
		return result;
	}
	/**
	 * 是否开启 GPS
	 *
	 * @param context
	 * @return
	 */
	public static boolean isOpenGPS(Context context) {

		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		boolean isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		boolean isNPEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		if (isGPSEnable || isNPEnable)
			return true;


		return false;
	}

	/**
	 * 打开Gps
	 * @param context
     */
	public static void openGPS(Context context) {

		Intent GPSIntent = new Intent();

		GPSIntent.setClassName("com.android.settings",
				"com.android.settings.widget.SettingsAppWidgetProvider");

		GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
		GPSIntent.setData(Uri.parse("custom:3"));

		try {
			PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
		} catch (PendingIntent.CanceledException e) {
			e.printStackTrace();
		}
	}
}
