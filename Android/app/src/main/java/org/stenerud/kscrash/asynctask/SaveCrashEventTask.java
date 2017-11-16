package org.stenerud.kscrash.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.pwrd.google.gson.reflect.TypeToken;

import org.stenerud.kscrash.bean.CrashEvent;
import org.stenerud.kscrash.db.DBManager;
import org.stenerud.kscrash.utils.GsonUtils;
import org.stenerud.kscrash.utils.LogUtils;

import java.sql.SQLException;





/**
 * 保存Crash事件统计打点，程序启动时发送
 * @author Jack
 */
public class SaveCrashEventTask extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "SaveEventTask";
    private final String CRASH_VERSION = "1.0.0";   //todo 读取sdk jar包的名称
    private Context mContext;
    private int mTaskId;
    private String mAppId, mTaskVersion, mChannel;
    /**已经带有logType exception_summary exception_thread_stack信息的事件*/
    private CrashEvent mCrashEvent;




    /**
     * 初始化crash异常类
     * @param context
     * @param crashEvent 不能为空
     */
    public SaveCrashEventTask(Context context, CrashEvent crashEvent, int taskId,
                              int appId, String taskVersion, String channel) {
        if (context == null) {
            throw new NullPointerException("context can't be null");
        }else{
            mContext = context.getApplicationContext();
        }
        if(crashEvent == null){
            throw new NullPointerException("event can't be null");
        }else{
            mCrashEvent = crashEvent;
        }
        if(taskId == 0){
            throw new RuntimeException("taskId cannot be 0!");
        }else{
            mTaskId = taskId;
        }
        if(appId == 0){
            throw new RuntimeException("appId cannot be 0!");
        }else{
            mAppId = String.valueOf(appId);
        }
        if(TextUtils.isEmpty(taskVersion)){
            throw new RuntimeException("taskVersion cannot be null!");
        }else{
            mTaskVersion = taskVersion;
        }
        if(TextUtils.isEmpty(channel)){
            throw new RuntimeException("channel cannot be null!");
        }else{
            mChannel = channel;
        }
    }


    @Override
    protected Void doInBackground(Void... params) {
        //封装成event对象保存到本地数据库
        LogUtils.e(TAG, "SaveCrashEventTask------------doInBackground");
        mCrashEvent.setSdkVersion(CRASH_VERSION);
        mCrashEvent.setAppVersion("");          //todo 需要获得AndroidManifest.xml中的package
        mCrashEvent.setTaskId(String.valueOf(mTaskId));
        mCrashEvent.setAppId(mAppId);
        mCrashEvent.setTaskVersion(mTaskVersion);
        mCrashEvent.setChannel(mChannel);
        mCrashEvent.setTimestamp(String.valueOf(System.currentTimeMillis()));

        LogUtils.d(TAG, "before saveCrashEvent --------- " + mCrashEvent);
        boolean flag = false;
        try {
            flag = DBManager.getInstance(mContext).saveCrashEvent(mCrashEvent);
        } catch (SQLException e) {
            LogUtils.e(TAG, "first saveCrashEvent -------- crash");
            e.printStackTrace();
        }
        if (!flag) {   //不成功就再存一次
            LogUtils.e(TAG, "DfgaSDK save crash event error!----------" +
                    GsonUtils.toJson(mCrashEvent, new TypeToken<CrashEvent>(){}.getType()));
            try {
                DBManager.getInstance(mContext).saveCrashEvent(mCrashEvent);
            } catch (SQLException e) {
                LogUtils.e(TAG, "again saveCrashEvent -------- crash");
                e.printStackTrace();
            }
        } else {  //成功
            LogUtils.d(TAG, "DfgaSDK save crash event success----------" +
                    GsonUtils.toJson(mCrashEvent, new TypeToken<CrashEvent>(){}.getType()));
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.e(TAG, "dealWithCrash summary----------" + mCrashEvent.getExceptionSummary());
        Log.e(TAG, "dealWithCrash detail----------" + mCrashEvent.getExceptionThreadStack());
    }
}
