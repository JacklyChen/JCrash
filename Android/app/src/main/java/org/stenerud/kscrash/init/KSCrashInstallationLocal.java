package org.stenerud.kscrash.init;

import android.content.Context;

import org.stenerud.kscrash.IDealWithCrash;
import org.stenerud.kscrash.KSCrash;

import java.io.IOException;




/**
 * 记录crash日志到本地的工具类
 * 完美世界
 * @Author Jack
 * @Date 2017/11/16 10:27
 * @Copyright:wanmei.com Inc. All rights reserved.
 */
public enum KSCrashInstallationLocal {

    INSTANCE;

    /**
     * 初始化
     * @throws IOException
     */
    public void install(Context context) throws IOException {
        KSCrash.getInstance().install(context);
    }

    /**
     * 设置自定义处理crash
     * @param iDealWithCrash
     */
    public void setIDealWithCrash(IDealWithCrash iDealWithCrash){
        KSCrash.getInstance().setIDealWithCrash(iDealWithCrash);
    }

}
