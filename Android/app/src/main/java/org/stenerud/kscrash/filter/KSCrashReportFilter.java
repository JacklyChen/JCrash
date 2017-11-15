package org.stenerud.kscrash.filter;

import java.util.List;




/**
 * 通过何种方式上报日志的接口定义
 * <p>
 * The transformed reports are returned via the completion callback.
 * <p>
 * Each filter implementation takes a specific data type as input and returns a possibly different
 * data type. The expected types are listed in the implementation documentation.
 */
public interface KSCrashReportFilter {

    /**
     * The callback to call with the filtered reports
     */
    interface CompletionCallback {

        /**
         * 过滤日志完成时受到通知
         * Receive a notification of a completed filter operation.
         * @param reports The transformed reports.
         * @throws KSCrashReportFilteringFailedException
         */
        void onCompletion(List reports) throws KSCrashReportFilteringFailedException;
    }

    /**
     * 过滤日志
     * Filter some reports.
     * @param reports            The reports to filter.
     * @param completionCallback The callback to call with the filtered reports.
     * @throws KSCrashReportFilteringFailedException
     */
    void filterReports(List reports, CompletionCallback completionCallback)
            throws KSCrashReportFilteringFailedException;
}
