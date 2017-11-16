package org.stenerud.kscrash.utils;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import org.stenerud.kscrash.common.Constant;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;



/**
 * 设备相关工具类
 * @author Jack
 */
public final class DeviceUtils {

    private static final String TAG = "DeviceUtils";
    /**
     * 没有网络连接
     */
    private static final String NO_NET = "no";
    private static DeviceUtils sInstance;
    private static Context sContext;
    private static final String NULL = "NULL";





    /**
     * 获取单例
     *
     * @param context
     * @return
     */
    public synchronized static DeviceUtils getInstance(Context context) {
        if (context == null) {
            throw new NullPointerException();
        }
        sContext = context.getApplicationContext();
        if (sInstance == null) {
            sInstance = new DeviceUtils();
        }
        return sInstance;
    }


    /**
     * 获取IMEI
     * @return
     */
    public String getDeviceId() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) sContext.getSystemService(Context.TELEPHONY_SERVICE);
            String deviceId = telephonyManager.getDeviceId();
            String result = TextUtils.isEmpty(deviceId) ||
                    deviceId.equals("000000000000000") ||
                    deviceId.equals("0") ? "NULL" : deviceId.toLowerCase();
            LogUtils.d(TAG, "IMEI :" + result);
            return result;
        } catch (Exception e) {
            return NULL;
        }
    }

    /**
     * 获取设备获取设备品牌
     *
     * @return
     */
    public static String getDeviceType() {
        try {
            String deviceType = Build.MANUFACTURER;
            return deviceType == null ? "" : deviceType;
        } catch (Exception e) {
            return NULL;
        }
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getDeviceModel() {
        try {
            return Build.MODEL;
        } catch (Exception e) {
            return NULL;
        }
    }

    /**
     * 设备系统版本
     *
     * @return
     */
    public static String getDeviceSys() {
        try {
            String deviceSys = Build.VERSION.RELEASE;
            return deviceSys == null ? "" : deviceSys;
        } catch (Exception e) {
            return NULL;
        }
    }

    /**
     * 获取Mac
     *
     * @return
     */
    public static String getMac() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            LogUtils.e(TAG, "get mac failed, maybe forget \"android.permission.INTERNET\"? Error:" + ex.getMessage());
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 获取操作系统类型
     *
     * @return
     */
    public static String getOSType() {
        try {
            return String.valueOf(Constant.OS_TYPE);
        } catch (Exception e) {
            return NULL;
        }
    }

    /**
     * 获取清单文件中package字段的值
     * @return
     */
    public static String getPackage(){
        if(sContext != null){
            return sContext.getPackageName();
        }else{
            return NULL;
        }
    }




}
