package org.stenerud.kscrash.init;

import android.content.Context;

import org.stenerud.kscrash.IDealWithCrash;
import org.stenerud.kscrash.KSCrash;

import java.io.IOException;
import java.sql.SQLException;


/**
 * 记录crash日志到本地的工具类
 * @Author Jack
 * @Date 2017/11/16 10:27
 * @Copyright:wanmei.com Inc. All rights reserved.
 */
public enum KSCrashInstallationLocal {

    INSTANCE;

    private KSCrash mKSCrash;

    /**
     * 初始化
     * @throws IOException
     */
    public void install(Context context, int taskId, int appId, String taskVersion, String channel)
            throws IOException, SQLException {
        mKSCrash = KSCrash.getInstance(context, taskId, appId, taskVersion, channel);
        mKSCrash.install();
    }

    public void install(Context context) throws IOException, SQLException {
        mKSCrash = KSCrash.getInstance(context);
        mKSCrash.install();
    }

    /**
     * 设置自定义处理crash
     * @param iDealWithCrash
     */
    public void setIDealWithCrash(IDealWithCrash iDealWithCrash) throws IOException {
        if(mKSCrash == null){
            throw new RuntimeException("first install mKSCrash is null!!!");
        }else{
            mKSCrash.setIDealWithCrash(iDealWithCrash);
        }
    }

}
