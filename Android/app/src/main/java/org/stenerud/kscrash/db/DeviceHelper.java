package org.stenerud.kscrash.db;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import org.stenerud.kscrash.bean.DeviceInfo;

import java.sql.SQLException;
import java.util.List;




/**
 * Device相关数据库操作的工具类
 * @author Jack
 */
public class DeviceHelper {

    private RuntimeExceptionDao<DeviceInfo, Long> mDeviceDao;
    private DBHelper mDatabaseHelper;

    public DeviceHelper(DBHelper databaseHelper) {
        mDatabaseHelper = databaseHelper;
        if (mDeviceDao == null) {
            mDeviceDao = mDatabaseHelper.getRuntimeExceptionDao(DeviceInfo.class);
        }
    }

    /**
     * 获取未上传成功的设备信息
     *
     * @return
     * @throws SQLException
     */
    public List<DeviceInfo> getUnUploadDevices() throws SQLException {
        QueryBuilder<DeviceInfo, Long> queryBuilder = mDeviceDao.queryBuilder();
        queryBuilder.orderBy(DeviceInfo.ID, true);
        return queryBuilder.query();
    }

    /**
     * 获取设备信息
     *
     * @return
     * @throws SQLException
     */
    public List<DeviceInfo> getDeviceTop() throws SQLException {
        QueryBuilder<DeviceInfo, Long> queryBuilder = mDeviceDao.queryBuilder();
        queryBuilder.orderBy(DeviceInfo.ID, false);
        queryBuilder.limit(1);
        return queryBuilder.query();
    }

    /**
     * 删除设备信息
     *
     * @return
     * @throws SQLException
     */
    public int deleteUploadedDevices() throws SQLException {
        DeleteBuilder<DeviceInfo, Long> deleteBuilder = mDeviceDao.deleteBuilder();
        return deleteBuilder.delete();
    }

    /**
     * 插入新数据
     *
     * @param deviceInfo
     * @return
     * @throws SQLException
     */
    public DeviceInfo insertUnUploadDevices(DeviceInfo deviceInfo) throws SQLException {
        return mDeviceDao.createIfNotExists(deviceInfo);
    }

}
