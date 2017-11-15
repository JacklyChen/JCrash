package org.stenerud.kscrash.filter;

import java.util.Iterator;
import java.util.List;



/**
 * 逐个执行通过何种方式上传日志的类
 * Create a pipeline of filters that get called one after the other.
 * The output of one filter is fed as input to the next.
 * Input: Any
 * Output: Any
 */
public class KSCrashReportFilterPipeline implements KSCrashReportFilter {

    private List<KSCrashReportFilter> filters;

    public KSCrashReportFilterPipeline(List<KSCrashReportFilter> filters) {
        this.filters = filters;
    }

    @Override
    public void filterReports(List reports, CompletionCallback completionCallback)
            throws KSCrashReportFilteringFailedException {
        runNextFilter(reports, filters.iterator(), completionCallback);
    }

    private void runNextFilter(List incomingReports,
                               final Iterator<KSCrashReportFilter> iterator,
                               final CompletionCallback finalCallback) throws KSCrashReportFilteringFailedException {
        if (iterator.hasNext()) {
            KSCrashReportFilter filter = iterator.next();
            filter.filterReports(incomingReports, new KSCrashReportFilter.CompletionCallback() {
                @Override
                public void onCompletion(List reports) throws KSCrashReportFilteringFailedException {
                    runNextFilter(reports, iterator, finalCallback);
                }
            });
        } else {
            finalCallback.onCompletion(incomingReports);
        }
    }
}
