package org.stenerud.kscrash.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.pwrd.google.gson.annotations.Expose;
import com.pwrd.google.gson.annotations.SerializedName;


/**
 * crash记录的日志
 * 完美世界
 * @Author Jack
 * @Date 2017/11/16 11:22
 * @Copyright:wanmei.com Inc. All rights reserved.
 */
@DatabaseTable(tableName = "crashevent")
public class CrashEvent {

    public static final String ID = "id";
    @DatabaseField(generatedId = true, columnName = ID)
    private int id;

    /**
     * Crash SDK版本
     */
    @Expose
    @SerializedName("e_sdk")
    @DatabaseField(columnName = "sdk_version")
    private String sdkVersion;

    /**
     * 应用版本号（Android应用或游戏若没在清单文件配置为空）
     */
    @Expose
    @SerializedName("e_apv")
    @DatabaseField(columnName = "app_version")
    private String appVersion;

    /**
     * 错误摘要，错误码。aes加密字符串存储在本地，上传时解密
     */
    @Expose
    @SerializedName("e_esy")
    @DatabaseField(columnName = "exception_summary")
    private String exceptionSummary;

    /**
     * 出错线程堆栈。aes加密字符串存储。在本地，上传时解密
     */
    @Expose
    @SerializedName("e_ets")
    @DatabaseField(columnName = "exception_thread_stack")
    private String exceptionThreadStack;


    /**
     * 服务端分配给业务的id
     */
    @Expose
    @SerializedName("e_tid")
    @DatabaseField(columnName = "task_id")
    private String taskId;

    /**
     * 客户端标识，比如一个游戏一个app_id（OneSDK接入的4位数，老虎SDK接入的5位数，线上、线下demo用1000或1001）
     */
    @Expose
    @SerializedName("e_aid")
    @DatabaseField(columnName = "app_id")
    private String appId;

    /**
     * 业务的版本号，比如老虎sdk的版本号，游戏的版本号
     */
    @Expose
    @SerializedName("e_tvn")
    @DatabaseField(columnName = "task_version")
    private String taskVersion;

    /**
     * 渠道id
     */
    @Expose
    @SerializedName("e_chn")
    @DatabaseField(columnName = "channel")
    private String channel;

    /**
     * 上传日志的类型，1是Android，2是IOS
     1.1 Java运行时异常
     1.2 NDK异常
     1.3 ANR
     */
    @Expose
    @SerializedName("e_lt")
    @DatabaseField(columnName = "logType")
    private String logType;

    /**
     * 崩溃日志产生时间戳，单位ms
     */
    @Expose
    @SerializedName("e_ts")
    @DatabaseField(columnName = "timestamp")
    private String timestamp;


    public CrashEvent() {
        super();
    }

    public CrashEvent(String exceptionSummary, String exceptionThreadStack) {
        this.exceptionSummary = exceptionSummary;
        this.exceptionThreadStack = exceptionThreadStack;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public CrashEvent setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
        return this;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public CrashEvent setAppVersion(String appVersion) {
        this.appVersion = appVersion;
        return this;
    }

    public String getExceptionSummary() {
        return exceptionSummary;
    }

    public CrashEvent setExceptionSummary(String exceptionSummary) {
        this.exceptionSummary = exceptionSummary;
        return this;
    }

    public String getExceptionThreadStack() {
        return exceptionThreadStack;
    }

    public CrashEvent setExceptionThreadStack(String exceptionThreadStack) {
        this.exceptionThreadStack = exceptionThreadStack;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public CrashEvent setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getAppId() {
        return appId;
    }

    public CrashEvent setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getTaskVersion() {
        return taskVersion;
    }

    public CrashEvent setTaskVersion(String taskVersion) {
        this.taskVersion = taskVersion;
        return this;
    }

    public String getChannel() {
        return channel;
    }

    public CrashEvent setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public String getLogType() {
        return logType;
    }

    public CrashEvent setLogType(String logType) {
        this.logType = logType;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public CrashEvent setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @Override
    public String toString() {
        return "CrashEvent{" +
                "id=" + id +
                ", sdkVersion='" + sdkVersion + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", exceptionSummary='" + exceptionSummary + '\'' +
                ", exceptionThreadStack='" + exceptionThreadStack + '\'' +
                ", taskId='" + taskId + '\'' +
                ", appId='" + appId + '\'' +
                ", taskVersion='" + taskVersion + '\'' +
                ", channel='" + channel + '\'' +
                ", logType='" + logType + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
