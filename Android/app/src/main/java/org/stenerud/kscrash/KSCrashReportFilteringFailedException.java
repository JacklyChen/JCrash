package org.stenerud.kscrash;

import java.util.List;


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
