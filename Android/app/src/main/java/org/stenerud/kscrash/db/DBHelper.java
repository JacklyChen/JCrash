package org.stenerud.kscrash.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.stenerud.kscrash.utils.LogUtils;

import java.sql.SQLException;





/**
 * 数据库操作工具类
 * @author Jack
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {

    private final String TAG = "DBHelper";



    public DBHelper(Context context) {
        super(context, DBConfig.DATABASE_NAME, null, DBConfig.DATABASE_VERSION_1_0_0);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        LogUtils.d(TAG, "oncreate -------- createTable");
        try {
            for (Class clazz : DBConfig.classes) {
                TableUtils.createTableIfNotExists(connectionSource, clazz);
                LogUtils.d(TAG, "oncreate -------- createTable" + clazz.getName());
            }
        } catch (SQLException e) {
            LogUtils.e(TAG, "e----------" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,
                          ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        Log.d(TAG, "DfgaSDK onUpgrade database from:" + oldVersion + "to:" + newVersion);
    }


}
