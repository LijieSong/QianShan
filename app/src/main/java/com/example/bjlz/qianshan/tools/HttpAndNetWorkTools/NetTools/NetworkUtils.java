package com.example.bjlz.qianshan.tools.HttpAndNetWorkTools.NetTools;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

/**
 * 网络判断工具类
 */
public class NetworkUtils
{
  public static final String NETWORK_TYPE_WIFI_CN = "wifi";
  public static final String NETWORK_TYPE_3G = "eg";
  public static final String NETWORK_TYPE_2G = "2g";
  public static final String NETWORK_TYPE_WAP = "wap";
  public static final String NETWORK_TYPE_UNKNOWN_CN = "unknown";
  public static final String NETWORK_TYPE_DISCONNECT = "disconnect";


  private static final int NETWORK_TYPE_UNAVAILABLE = -1;
  // private static final int NETWORK_TYPE_MOBILE = -100;
  private static final int NETWORK_TYPE_WIFI = -101;

  private static final int NETWORK_CLASS_WIFI = -101;
  private static final int NETWORK_CLASS_UNAVAILABLE = -1;
  /** Unknown network class. */
  private static final int NETWORK_CLASS_UNKNOWN = 0;
  /** Class of broadly defined "2G" networks. */
  private static final int NETWORK_CLASS_2_G = 1;
  /** Class of broadly defined "3G" networks. */
  private static final int NETWORK_CLASS_3_G = 2;
  /** Class of broadly defined "4G" networks. */
  private static final int NETWORK_CLASS_4_G = 3;

  private static DecimalFormat df = new DecimalFormat("#.##");

  // 适配低版本手机
  /** Network type is unknown */
  public static final int NETWORK_TYPE_UNKNOWN = 0;
  /** Current network is GPRS */
  public static final int NETWORK_TYPE_GPRS = 1;
  /** Current network is EDGE */
  public static final int NETWORK_TYPE_EDGE = 2;
  /** Current network is UMTS */
  public static final int NETWORK_TYPE_UMTS = 3;
  /** Current network is CDMA: Either IS95A or IS95B */
  public static final int NETWORK_TYPE_CDMA = 4;
  /** Current network is EVDO revision 0 */
  public static final int NETWORK_TYPE_EVDO_0 = 5;
  /** Current network is EVDO revision A */
  public static final int NETWORK_TYPE_EVDO_A = 6;
  /** Current network is 1xRTT */
  public static final int NETWORK_TYPE_1xRTT = 7;
  /** Current network is HSDPA */
  public static final int NETWORK_TYPE_HSDPA = 8;
  /** Current network is HSUPA */
  public static final int NETWORK_TYPE_HSUPA = 9;
  /** Current network is HSPA */
  public static final int NETWORK_TYPE_HSPA = 10;
  /** Current network is iDen */
  public static final int NETWORK_TYPE_IDEN = 11;
  /** Current network is EVDO revision B */
  public static final int NETWORK_TYPE_EVDO_B = 12;
  /** Current network is LTE */
  public static final int NETWORK_TYPE_LTE = 13;
  /** Current network is eHRPD */
  public static final int NETWORK_TYPE_EHRPD = 14;
  /** Current network is HSPA+ */
  public static final int NETWORK_TYPE_HSPAP = 15;
  /**
   * 判断网络是否连接
   *
   * @param context
   * @return
   */
  public static boolean isConnected(Context context)
  {
    ConnectivityManager connectivity = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);

    if (null != connectivity)
    {
      NetworkInfo info = connectivity.getActiveNetworkInfo();
      if (null != info && info.isConnected())
      {
        if (info.getState() == NetworkInfo.State.CONNECTED)
        {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 是否是 wap方式连接
   * @param paramContext
     * @return
     */
  public static boolean isMobileConnected(Context paramContext)
  {
    boolean bool = false;
    if (paramContext != null)
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(0);
      bool = false;
      if (localNetworkInfo != null)
        bool = localNetworkInfo.isAvailable();
    }
    return bool;
  }

  /**
   * 是否是net方式连接
   * @param paramContext
   * @return
     */
  public static boolean isNetworkConnected(Context paramContext)
  {
    if (paramContext != null)
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
      if (localNetworkInfo != null)
        return localNetworkInfo.isAvailable();
    }
    return false;
  }

  /**
   * 是否是wifi连接
   * @param paramContext
     * @return
     */
  public static boolean isWifiConnected(Context paramContext)
  {
    if (paramContext != null)
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(1);
      if (localNetworkInfo != null)
        return localNetworkInfo.isAvailable();
    }
    return false;
  }

  /**
   * 判断是否是wifi连接
   * @param context
   * @return
     */
  public static boolean isWifi(Context context)
  {
    ConnectivityManager cm = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);
    if (cm == null)
      return false;
    return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
  }

  /**
   * 是否是wap方式连接
   * @param context
   * @return
     */
  public static boolean isMobile(Context context)
  {
    ConnectivityManager cm = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);
    if (cm == null)
      return false;
    return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_MOBILE;
  }

  /**
   * 打开wifi设置界面
   * @param activity
     */
  public static void openSetting(Activity activity)
  {
    Intent intent = new Intent("/");
    ComponentName cm = new ComponentName("com.android.settings",
            "com.android.settings.WirelessSettings");
    intent.setComponent(cm);
    intent.setAction("android.intent.action.VIEW");
    activity.startActivityForResult(intent, 0);
  }

  /**
   * Get network type
   *
   * @param context
   * @return boolean
   */
  public static int getNetworkType(Context context) {
    ConnectivityManager connectivityManager = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager == null ? null : connectivityManager.getActiveNetworkInfo();
    return networkInfo == null ? -1 : networkInfo.getType();
  }

  /**
   * Get network type name
   *
   * @param context
   * @return boolean
   */
  public static String getNetworkTypeName(Context context) {
    ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo;
    String type = NETWORK_TYPE_DISCONNECT;
    if (manager == null || (networkInfo = manager.getActiveNetworkInfo()) == null) {
      return type;
    }
    if (networkInfo.isConnected()) {
      String typeName = networkInfo.getTypeName();
      if ("WIFI".equalsIgnoreCase(typeName)) {
        type = NETWORK_TYPE_WIFI_CN;
      } else if ("MOBILE".equalsIgnoreCase(typeName)) {
        String proxyHost = android.net.Proxy.getDefaultHost();
        type = TextUtils.isEmpty(proxyHost) ? (isFastMobileNetwork(context) ? NETWORK_TYPE_3G : NETWORK_TYPE_2G)
                : NETWORK_TYPE_WAP;
      } else {
        type = NETWORK_TYPE_UNKNOWN_CN;
      }
    }
    return type;
  }

  /**
   * 描述：判断网络是否有效.
   *
   * @param context the context
   * @return true, if is network available
   */
  public static boolean isNetworkAvailable(Context context) {
    try {
      ConnectivityManager connectivity = (ConnectivityManager) context
              .getSystemService(Context.CONNECTIVITY_SERVICE);
      if (connectivity != null) {
        NetworkInfo info = connectivity.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
          if (info.getState() == NetworkInfo.State.CONNECTED) {
            return true;
          }
        }
      }
    } catch (Exception e) {
      return false;
    }
    return false;
  }

  /**
   * Whether is fast mobile network
   *
   * @param context
   * @return boolean
   */

  private static boolean isFastMobileNetwork(Context context) {
    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    if (telephonyManager == null) {
      return false;
    }
    switch (telephonyManager.getNetworkType()) {
      case TelephonyManager.NETWORK_TYPE_1xRTT:
        return false;
      case TelephonyManager.NETWORK_TYPE_CDMA:
        return false;
      case TelephonyManager.NETWORK_TYPE_EDGE:
        return false;
      case TelephonyManager.NETWORK_TYPE_EVDO_0:
        return true;
      case TelephonyManager.NETWORK_TYPE_EVDO_A:
        return true;
      case TelephonyManager.NETWORK_TYPE_GPRS:
        return false;
      case TelephonyManager.NETWORK_TYPE_HSDPA:
        return true;
      case TelephonyManager.NETWORK_TYPE_HSPA:
        return true;
      case TelephonyManager.NETWORK_TYPE_HSUPA:
        return true;
      case TelephonyManager.NETWORK_TYPE_UMTS:
        return true;
      case TelephonyManager.NETWORK_TYPE_EHRPD:
        return true;
      case TelephonyManager.NETWORK_TYPE_EVDO_B:
        return true;
      case TelephonyManager.NETWORK_TYPE_HSPAP:
        return true;
      case TelephonyManager.NETWORK_TYPE_IDEN:
        return false;
      case TelephonyManager.NETWORK_TYPE_LTE:
        return true;
      case TelephonyManager.NETWORK_TYPE_UNKNOWN:
        return false;
      default:
        return false;
    }
  }
  /**
   * wifi 是否可用
   * @param context
   * @return
   */
  public static boolean isWifiAvailable(Context context) {
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    return (networkInfo != null && networkInfo.isConnected() && networkInfo
            .getType() == ConnectivityManager.TYPE_WIFI);
  }
  /**
   * 获取MAC地址
   *
   * @param context
   * @return
   */
  public static String getMacAddress(Context context) {
    if (context == null) {
      return "";
    }

    String localMac = null;
    if (isWifiAvailable(context)) {
      localMac = getWifiMacAddress(context);
    }

    if (localMac != null && localMac.length() > 0) {
      localMac = localMac.replace(":", "-").toLowerCase();
      return localMac;
    }

    localMac = getMacFromCallCmd();
    if (localMac != null) {
      localMac = localMac.replace(":", "-").toLowerCase();
    }

    return localMac;
  }

  private static String getWifiMacAddress(Context context) {
    String localMac = null;
    try {
      WifiManager wifi = (WifiManager) context
              .getSystemService(Context.WIFI_SERVICE);
      WifiInfo info = wifi.getConnectionInfo();
      if (wifi.isWifiEnabled()) {
        localMac = info.getMacAddress();
        if (localMac != null) {
          localMac = localMac.replace(":", "-").toLowerCase();
          return localMac;
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * 通过callCmd("busybox ifconfig","HWaddr")获取mac地址
   *
   * @attention 需要设备装有busybox工具
   * @return Mac Address
   */
  private static String getMacFromCallCmd() {
    String result = "";
    result = callCmd("busybox ifconfig", "HWaddr");
    if (result == null || result.length() <= 0) {
      return null;
    }
    // 对该行数据进行解析
    // 例如：eth0 Link encap:Ethernet HWaddr 00:16:E8:3E:DF:67
    if (result.length() > 0 && result.contains("HWaddr") == true) {
      String Mac = result.substring(result.indexOf("HWaddr") + 6,
              result.length() - 1);
      if (Mac.length() > 1) {
        result = Mac.replaceAll(" ", "");
      }
    }

    return result;
  }

  public static String callCmd(String cmd, String filter) {
    String result = "";
    String line = "";
    try {
      Process proc = Runtime.getRuntime().exec(cmd);
      InputStreamReader is = new InputStreamReader(proc.getInputStream());
      BufferedReader br = new BufferedReader(is);

      // 执行命令cmd，只取结果中含有filter的这一行
      while ((line = br.readLine()) != null
              && line.contains(filter) == false) {
      }

      result = line;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 网络是否可用
   *
   * @param context
   * @return
   */
  public static boolean IsNetWorkEnable(Context context) {
    try {
      ConnectivityManager connectivity = (ConnectivityManager) context
              .getSystemService(Context.CONNECTIVITY_SERVICE);
      if (connectivity == null) {
        return false;
      }

      NetworkInfo info = connectivity.getActiveNetworkInfo();
      if (info != null && info.isConnected()) {
        // 判断当前网络是否已经连接
        if (info.getState() == NetworkInfo.State.CONNECTED) {
          return true;
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }


  /**
   * 格式化大小
   *
   * @param size
   * @return
   */
  public static String formatSize(long size) {
    String unit = "B";
    float len = size;
    if (len > 900) {
      len /= 1024f;
      unit = "KB";
    }
    if (len > 900) {
      len /= 1024f;
      unit = "MB";
    }
    if (len > 900) {
      len /= 1024f;
      unit = "GB";
    }
    if (len > 900) {
      len /= 1024f;
      unit = "TB";
    }
    return df.format(len) + unit;
  }

  public static String formatSizeBySecond(long size) {
    String unit = "B";
    float len = size;
    if (len > 900) {
      len /= 1024f;
      unit = "KB";
    }
    if (len > 900) {
      len /= 1024f;
      unit = "MB";
    }
    if (len > 900) {
      len /= 1024f;
      unit = "GB";
    }
    if (len > 900) {
      len /= 1024f;
      unit = "TB";
    }
    return df.format(len) + unit + "/s";
  }

  public static String format(long size) {
    String unit = "B";
    float len = size;
    if (len > 1000) {
      len /= 1024f;
      unit = "KB";
      if (len > 1000) {
        len /= 1024f;
        unit = "MB";
        if (len > 1000) {
          len /= 1024f;
          unit = "GB";
        }
      }
    }
    return df.format(len) + "\n" + unit + "/s";
  }

  /**
   * 获取运营商
   *
   * @return
   */
  public static String getProvider(Context context) {
    String provider = "未知";
    try {
      TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
      String IMSI = telephonyManager.getSubscriberId();
      Log.v("tag", "getProvider.IMSI:" + IMSI);
      if (IMSI == null) {
        if (TelephonyManager.SIM_STATE_READY == telephonyManager
                .getSimState()) {
          String operator = telephonyManager.getSimOperator();
          Log.v("tag", "getProvider.operator:" + operator);
          if (operator != null) {
            if (operator.equals("46000")
                    || operator.equals("46002")
                    || operator.equals("46007")) {
              provider = "中国移动";
            } else if (operator.equals("46001")) {
              provider = "中国联通";
            } else if (operator.equals("46003")) {
              provider = "中国电信";
            }
          }
        }
      } else {
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")
                || IMSI.startsWith("46007")) {
          provider = "中国移动";
        } else if (IMSI.startsWith("46001")) {
          provider = "中国联通";
        } else if (IMSI.startsWith("46003")) {
          provider = "中国电信";
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return provider;
  }

  /**
   * 获取网络类型
   *
   * @return
   */
  public static String getCurrentNetworkType(Context context) {
    int networkClass = getNetworkClass(context);
    String type = "未知";
    switch (networkClass) {
      case NETWORK_CLASS_UNAVAILABLE:
        type = "无";
        break;
      case NETWORK_CLASS_WIFI:
        type = "Wi-Fi";
        break;
      case NETWORK_CLASS_2_G:
        type = "2G";
        break;
      case NETWORK_CLASS_3_G:
        type = "3G";
        break;
      case NETWORK_CLASS_4_G:
        type = "4G";
        break;
      case NETWORK_CLASS_UNKNOWN:
        type = "未知";
        break;
    }
    return type;
  }

  private static int getNetworkClassByType(int networkType) {
    switch (networkType) {
      case NETWORK_TYPE_UNAVAILABLE:
        return NETWORK_CLASS_UNAVAILABLE;
      case NETWORK_TYPE_WIFI:
        return NETWORK_CLASS_WIFI;
      case NETWORK_TYPE_GPRS:
      case NETWORK_TYPE_EDGE:
      case NETWORK_TYPE_CDMA:
      case NETWORK_TYPE_1xRTT:
      case NETWORK_TYPE_IDEN:
        return NETWORK_CLASS_2_G;
      case NETWORK_TYPE_UMTS:
      case NETWORK_TYPE_EVDO_0:
      case NETWORK_TYPE_EVDO_A:
      case NETWORK_TYPE_HSDPA:
      case NETWORK_TYPE_HSUPA:
      case NETWORK_TYPE_HSPA:
      case NETWORK_TYPE_EVDO_B:
      case NETWORK_TYPE_EHRPD:
      case NETWORK_TYPE_HSPAP:
        return NETWORK_CLASS_3_G;
      case NETWORK_TYPE_LTE:
        return NETWORK_CLASS_4_G;
      default:
        return NETWORK_CLASS_UNKNOWN;
    }
  }

  private static int getNetworkClass(Context context) {
    int networkType = NETWORK_TYPE_UNKNOWN;
    try {
      final NetworkInfo network = ((ConnectivityManager) context
              .getSystemService(Context.CONNECTIVITY_SERVICE))
              .getActiveNetworkInfo();
      if (network != null && network.isAvailable()
              && network.isConnected()) {
        int type = network.getType();
        if (type == ConnectivityManager.TYPE_WIFI) {
          networkType = NETWORK_TYPE_WIFI;
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
          TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(
                  Context.TELEPHONY_SERVICE);
          networkType = telephonyManager.getNetworkType();
        }
      } else {
        networkType = NETWORK_TYPE_UNAVAILABLE;
      }

    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return getNetworkClassByType(networkType);

  }

  public static String getWifiRssi(Context context) {
    int asu = 85;
    try {
      final NetworkInfo network = ((ConnectivityManager) context
              .getSystemService(Context.CONNECTIVITY_SERVICE))
              .getActiveNetworkInfo();
      if (network != null && network.isAvailable()
              && network.isConnected()) {
        int type = network.getType();
        if (type == ConnectivityManager.TYPE_WIFI) {
          WifiManager wifiManager = (WifiManager) context
                  .getSystemService(Context.WIFI_SERVICE);

          WifiInfo wifiInfo = wifiManager.getConnectionInfo();
          if (wifiInfo != null) {
            asu = wifiInfo.getRssi();
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return asu + "dBm";
  }

  public static String getWifiSsid(Context context) {
    String ssid = "";
    try {
      final NetworkInfo network = ((ConnectivityManager)context .getSystemService(Context.CONNECTIVITY_SERVICE))
              .getActiveNetworkInfo();
      if (network != null && network.isAvailable()
              && network.isConnected()) {
        int type = network.getType();
        if (type == ConnectivityManager.TYPE_WIFI) {
          WifiManager wifiManager = (WifiManager) context
                  .getSystemService(Context.WIFI_SERVICE);

          WifiInfo wifiInfo = wifiManager.getConnectionInfo();
          if (wifiInfo != null) {
            ssid = wifiInfo.getSSID();
            if (ssid == null) {
              ssid = "";
            }
            ssid = ssid.replaceAll("\"", "");
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ssid;
  }

  /**
   * 检查sim卡状态
   *
   * @param context
   * @return
   */
  public static boolean checkSimState(Context context) {
    TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    if (tm.getSimState() == TelephonyManager.SIM_STATE_ABSENT
            || tm.getSimState() == TelephonyManager.SIM_STATE_UNKNOWN) {
      return false;
    }

    return true;
  }

  /**
   * 获取imei
   */
  public static String getImei(Context context) {
    TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    String imei = mTelephonyMgr.getDeviceId();
    if (imei == null) {
      imei = "000000000000000";
    }
    return imei;
  }

  public static String getPhoneImsi(Context context) {
    TelephonyManager mTelephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
    return mTelephonyMgr.getSubscriberId();
  }
}