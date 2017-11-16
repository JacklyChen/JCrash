package org.stenerud.kscrash.bean;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.pwrd.google.gson.annotations.Expose;
import com.pwrd.google.gson.annotations.SerializedName;

import org.stenerud.kscrash.common.Constant;

import static org.stenerud.kscrash.common.Constant.DefaultValue.NULL;





/**
 * 设备相关信息
 * @author Jack
 */
@DatabaseTable(tableName = "deviceinfo")
public class DeviceInfo {

    /**
     * 数据库中的列名
     */
    public static final String ID = "id";
    @DatabaseField(generatedId = true, columnName = ID)
    private int id;
    /**
     * 设备标识符，按照老虎SDK既有生成规则
     */
    @Expose
    @SerializedName("e_did")
    @DatabaseField(columnName = "deviceId")
    private String deviceId = NULL;
    /**
     * 设备类型，android
     */
    @Expose
    @SerializedName("e_dtp")
    @DatabaseField(columnName = "deviceType")
    private String deviceType;
    /**
     * 设备型号
     */
    @Expose
    @SerializedName("e_dmd")
    @DatabaseField(columnName = "deviceModel")
    private String deviceModel;
    /**
     * 系统版本
     */
    @Expose
    @SerializedName("e_dss")
    @DatabaseField(columnName = "deviceSys")
    private String deviceSys;
    /**
     * mac地址
     */
    @Expose
    @SerializedName("e_mac")
    @DatabaseField(columnName = "mac")
    private String mac = "02:00:00:00:00:00";
    /**
     * 打点SDK version
     */
    @Expose
    @SerializedName("e_os")
    @DatabaseField(columnName = "os_type")
    private String osType = String.valueOf(Constant.OS_TYPE);
    /**
     * 接入统计SDK的应用的应用唯一标识（Android为包名，IOS为BundleID）, 完整字段为package_name，暂不存入数据库
     */
    @Expose
    @SerializedName("e_pn")
    @DatabaseField(columnName = "packageName")
    private String packageName = NULL;


    public DeviceInfo() {
        super();
    }

    public int getId() {
        return id;
    }

    public DeviceInfo setId(int id) {
        this.id = id;
        return this;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public DeviceInfo setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public DeviceInfo setDeviceType(String deviceType) {
        this.deviceType = deviceType;
        return this;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public DeviceInfo setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
        return this;
    }

    public String getDeviceSys() {
        return deviceSys;
    }

    public DeviceInfo setDeviceSys(String deviceSys) {
        this.deviceSys = deviceSys;
        return this;
    }

    public String getMac() {
        return mac;
    }

    public DeviceInfo setMac(String mac) {
        this.mac = mac;
        return this;
    }

    public String getPackageName() {
        return packageName;
    }

    public DeviceInfo setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }


    public DeviceInfo(String deviceId, String deviceType, String deviceModel, String deviceSys, String mac) {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.deviceModel = deviceModel;
        this.deviceSys = deviceSys;
        this.mac = mac;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "deviceId='" + deviceId + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", deviceModel='" + deviceModel + '\'' +
                ", deviceSys='" + deviceSys + '\'' +
                ", mac='" + mac + '\'' +
                ", packageName='" + packageName + '\'' +
                '}';
    }
}
