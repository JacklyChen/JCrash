package org.stenerud.kscrash;

import java.io.File;
import java.util.List;


/**
 * Delete files.
 */
public class KSCrashReportFilterDeleteFiles implements KSCrashReportFilter {
    @Override
    public void filterReports(List reports, CompletionCallback completionCallback) throws KSCrashReportFilteringFailedException {
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
