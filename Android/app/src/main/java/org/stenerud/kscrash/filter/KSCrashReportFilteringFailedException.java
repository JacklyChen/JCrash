package org.stenerud.kscrash.filter;

import java.util.List;


/**
 * 自定义的崩溃日志转换错误异常
 */
public class KSCrashReportFilteringFailedException extends Exception {

    public final List reports;

    public KSCrashReportFilteringFailedException(Throwable cause, List reports) {
        super(cause);
        this.reports = reports;
    }

    public KSCrashReportFilteringFailedException(String message, Throwable cause, List reports) {
        super(message, cause);
        this.reports = reports;
    }
}
