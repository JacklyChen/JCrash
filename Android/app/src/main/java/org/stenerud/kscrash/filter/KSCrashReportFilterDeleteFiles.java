package org.stenerud.kscrash.filter;

import java.io.File;
import java.util.List;


/**
 * 删除crash文件
 */
public class KSCrashReportFilterDeleteFiles implements KSCrashReportFilter {

    @Override
    public void filterReports(List reports, CompletionCallback completionCallback)
            throws KSCrashReportFilteringFailedException {
        try {
            for (Object report : reports) {
                File file = (File) report;
                file.delete();
            }
        } catch (Throwable error) {
            throw new KSCrashReportFilteringFailedException(error, reports);
        }
        completionCallback.onCompletion(reports);
    }
}
