package org.stenerud.kscrash.db;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import org.stenerud.kscrash.bean.CrashEvent;
import org.stenerud.kscrash.bean.DeviceInfo;

import java.io.IOException;
import java.sql.SQLException;




/**
 * 数据库配置类
 * @author Jack
 */
public class DBConfig extends OrmLiteConfigUtil {

    //dfgasdk1.0.0版本时是1，之后更新数据库时需要保留之前的版本
    public static final int DATABASE_VERSION_1_0_0 = 1;
    public static final String DATABASE_NAME = "crash_sdk_db.db";   //注：名字不要改，因为第一版本历史问题就一直是这个
    public static final String CONFIG_FILE_NAME = "crash_sdk_ormLite_config";


    public static Class<?>[] classes = {CrashEvent.class, DeviceInfo.class};

    public static void main(String[] args) throws SQLException, IOException {
        writeConfigFile(CONFIG_FILE_NAME, classes);
    }

}
