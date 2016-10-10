package com.example.bjlz.qianshan.tools.ChangeAndGetTools;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * 转换工具
 */
public class DP2PXTools {
	/**
	 * dip转px
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * px转dip
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获取deviceId
	 * @param mcontext
	 * @return
     */
	public static String getDeviceId(Context mcontext){
		TelephonyManager tm = (TelephonyManager) mcontext.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}
}
