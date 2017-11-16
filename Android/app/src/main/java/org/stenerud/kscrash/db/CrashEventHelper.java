package org.stenerud.kscrash.db;


import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import org.stenerud.kscrash.bean.CrashEvent;

import java.sql.SQLException;
import java.util.List;




/**
 * Event数据库操作工具类
 * @author Jack
 */
public class CrashEventHelper {

    private RuntimeExceptionDao<CrashEvent, Long> mCrashEventDao;
    private DBHelper mDatabaseHelper;
    private final int FAIL = -1;



    public CrashEventHelper(DBHelper databaseHelper) {
        mDatabaseHelper = databaseHelper;
        if (mCrashEventDao == null) {
            mCrashEventDao = mDatabaseHelper.getRuntimeExceptionDao(CrashEvent.class);
        }
    }

    /**
     * 按上传条数，获取前piece条的上传日志
     *
     * @param piece 前piece条
     * @return
     * @throws SQLException
     */
    public List<CrashEvent> getTopEvents(Long piece) throws SQLException {
        QueryBuilder<CrashEvent, Long> queryBuilder = mCrashEventDao.queryBuilder();
        queryBuilder.orderBy(CrashEvent.ID, true);
        queryBuilder.limit(piece);
        return queryBuilder.query();
    }


    /**
     * 按上传条数和event类型，获取前piece条的上传日志
     *
     * @param piece
     * @param type
     * @return
     * @throws SQLException
     */
    public List<CrashEvent> getTopEventsByType(Long piece, String type) throws SQLException {
        QueryBuilder<CrashEvent, Long> queryBuilder = mCrashEventDao.queryBuilder();
        queryBuilder.where().eq("type", type);
        queryBuilder.orderBy(CrashEvent.ID, true);
        queryBuilder.limit(piece);
        return queryBuilder.query();
    }

    /**
     * 清空event
     *
     * @return
     * @throws SQLException
     */
    public int clearEvent() throws SQLException {
        DeleteBuilder<CrashEvent, Long> deleteBuilder = mCrashEventDao.deleteBuilder();
        return deleteBuilder.delete();
    }

    /**
     * 插入新crash日志数据
     */
    public CrashEvent insertUnUploadEvents(CrashEvent event) throws SQLException {
        return mCrashEventDao.createIfNotExists(event);
    }

    /**
     * 获取数据库中事件的数量
     *
     * @return
     * @throws SQLException
     */
    public long getEventSize() throws SQLException {
        return mCrashEventDao.countOf();
    }

    /**
     * 获取数据库中指定类型崩溃事件
     * @return
     * @throws SQLException
     */
    public long getCrashEventSizeByLogType(String type) throws SQLException {
        QueryBuilder<CrashEvent, Long> queryBuilder = mCrashEventDao.queryBuilder();
        queryBuilder.where().eq("logType", type);
        List<CrashEvent> events = queryBuilder.query();
        if (events != null) {
            return events.size();
        } else {
            return 0;
        }
    }


    /**
     * 把已经上传的集合删除
     * @param eventList
     * @return
     */
    public int deleteUpdateEvent(List<CrashEvent> eventList) {
        return mCrashEventDao.delete(eventList);
    }

    /**
     * 删除单个事件
     * @param event
     * @return
     */
    public int deleteEvent(CrashEvent event){
        if(event != null){
            return mCrashEventDao.delete(event);
        }else{
            return FAIL;
        }
    }

}
