package org.stenerud.kscrash.db;

import android.content.Context;


import org.stenerud.kscrash.bean.CrashEvent;
import org.stenerud.kscrash.bean.DeviceInfo;
import org.stenerud.kscrash.utils.LogUtils;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.List;


/**
 * 数据库操作工具类
 * @author Jack
 */
public class DBManager {


    private static final String TAG = "DBManager";
    private static DBManager INSTANCE;
    private static Context mContext;
    private static WeakReference<DBHelper> mDBReference;
    private static CrashEventHelper mCrashEventHelper;
    private static DeviceHelper mDeviceHelper;



    public static synchronized DBManager getInstance(Context context) throws SQLException{
        if (INSTANCE == null) {
            INSTANCE = new DBManager();
        }
        INSTANCE.init(context);
        return INSTANCE;
    }

    private void init(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 注册数据库
     * @throws SQLException
     */
    public synchronized static void registerDB() {
        DBHelper dbHelper;
        if (mDBReference == null) {
            dbHelper = new DBHelper(mContext);
            mDBReference = new WeakReference<DBHelper>(dbHelper);
        }
        dbHelper = mDBReference.get();
        if (dbHelper != null) {
            mCrashEventHelper = new CrashEventHelper(dbHelper);
            mDeviceHelper = new DeviceHelper(dbHelper);
        }
    }

    /**
     * 获取设备信息
     *
     * @return
     */
    public synchronized DeviceInfo getFirstDevice() {
        try {
            List<DeviceInfo> deviceInfoList = mDeviceHelper.getDeviceTop();
            if (deviceInfoList != null && !deviceInfoList.isEmpty()) {
                DeviceInfo deviceInfo = deviceInfoList.get(0);
                // 在数据库获取DeviceInfo时写入Admonitor
                deviceInfo.setPackageName(mContext.getPackageName());
                LogUtils.d(TAG, "packageName----------" + mContext.getPackageName());
                return deviceInfo;
            } else {
                return null;
            }
        } catch (SQLException e) {
            LogUtils.e(TAG, "error when ------ getUnUploadDeviceInfos : " + e.getMessage());
            return null;
        }
    }

    /**
     * 删除所有设备信息
     *
     * @return
     */
    public synchronized boolean deleteUploadedDevices() {
        try {
            return (mDeviceHelper.deleteUploadedDevices() > 0);
        } catch (SQLException e) {
            LogUtils.e(TAG, "error when ------ deleteUploadedDevices : " + e.getMessage());
            return false;
        }
    }

    /**
     * 保存设备信息
     *
     * @param deviceInfo
     * @return
     */
    public synchronized boolean saveDevice(DeviceInfo deviceInfo) {
        try {
            return (mDeviceHelper.insertUnUploadDevices(deviceInfo) != null);
        } catch (SQLException e) {
            LogUtils.e(TAG, "error when ------ saveDevice : " + e.getMessage());
            return false;
        }
    }

    /**
     * 获取前piece条Event记录
     *
     * @param piece 获取的条数
     * @return
     */
    public synchronized List<CrashEvent> getTopCrashEvents(Long piece) {
        try {
            return mCrashEventHelper.getTopEvents(piece);
        } catch (SQLException e) {
            LogUtils.e(TAG, "error when ------ getUnUploadEvents : " + e.getMessage());
            return null;
        }
    }

    /**
     * 获取前piece条Event记录
     *
     * @param piece 获取的条数
     * @return
     */
    public synchronized List<CrashEvent> getTopEventsByType(Long piece, String type) {
        try {
            return mCrashEventHelper.getTopEventsByType(piece, type);
        } catch (SQLException e) {
            LogUtils.e(TAG, "error when ------ getUnUploadEvents : " + e.getMessage());
            return null;
        }
    }

    /**
     * 删除已经上传成功的事件
     *
     * @param eventList
     * @return
     */
    public synchronized boolean deleteUploadedEvents(List<CrashEvent> eventList) {
        return (mCrashEventHelper.deleteUpdateEvent(eventList) > 0);
    }

    /**
     * 清空数据库，包括rc和网络数据
     *
     * @return
     */
    public synchronized boolean clearEvent() {
        try {
            return (mCrashEventHelper.clearEvent() > 0);
        } catch (SQLException e) {
            LogUtils.e(TAG, "error when ------ clearEvent : " + e.getMessage());
            return false;
        }
    }


    /**
     * 保存crash event
     *
     * @param event
     * @return
     */
    public synchronized boolean saveEvent(CrashEvent event) {
        try {
            return (mCrashEventHelper.insertUnUploadEvents(event) != null);
        } catch (SQLException e) {
            LogUtils.e(TAG, "error when ------ saveEvent : " + e.getMessage());
            return false;
        }
    }

    /**
     * 保存crash日志
     * @return
     */
    public synchronized boolean saveCrashEvent(CrashEvent crashEvent){
        try {
            return (mCrashEventHelper.insertUnUploadEvents(crashEvent) != null);
        } catch (SQLException e) {
            LogUtils.e(TAG, "error when ------ saveEvent : " + e.getMessage());
            return false;
        }
    }


    /**
     * 删除指定事件
     * @param event 要删除的事件
     * @return true删除成功  false 失败
     */
    public synchronized boolean deleteEvent(CrashEvent event){
        return (mCrashEventHelper.deleteEvent(event) > 0);
    }

    /**
     * 获取event的列表大小
     *
     * @return
     */
    public synchronized long getEventSize() {
        try {
            return mCrashEventHelper.getEventSize();
        } catch (SQLException e) {
            LogUtils.e(TAG, "error when ------ saveEvent : " + e.getMessage());
            return -1;
        }
    }

    /**
     * 获取指定类型的crash event的列表大小
     * @return
     */
    public synchronized long getEventSizeByLogType(String type) {
        try {
            return mCrashEventHelper.getCrashEventSizeByLogType(type);
        } catch (SQLException e) {
            LogUtils.e(TAG, "error when ------ saveEvent : " + e.getMessage());
            return -1;
        }
    }

}
