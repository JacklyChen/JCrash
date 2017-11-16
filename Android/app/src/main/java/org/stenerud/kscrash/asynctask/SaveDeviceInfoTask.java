package org.stenerud.kscrash.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import org.stenerud.kscrash.bean.DeviceInfo;
import org.stenerud.kscrash.db.DBManager;
import org.stenerud.kscrash.utils.LogUtils;




/**
 * 保存设备相关信息到DB的异步任务
 * @author Jack
 */
public class SaveDeviceInfoTask extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "SaveDeviceInfoTask";
    private Context mContext;
    private DeviceInfo mDeviceInfo;



    public SaveDeviceInfoTask(Context context, DeviceInfo deviceInfo) {
        if (context != null) {
            mContext = context.getApplicationContext();
        }
        this.mDeviceInfo = deviceInfo;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            DBManager.getInstance(mContext).deleteUploadedDevices();
            DBManager.getInstance(mContext).saveDevice(mDeviceInfo);
        } catch (Exception e) {
            LogUtils.e(TAG, "SaveDeviceInfoTask Error: " + e.getMessage());
        }
        return null;
    }
}
