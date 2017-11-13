package org.stenerud.kscrash;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;



/**
 * The installation is the primary user-facing interface to the crash handling system.
 * A user instantiates an installation, optionally configures it, then calls install to
 * start handling crashes.
 *
 * sendOutstandingReports() will send any reports for crashes that occurred previously.
 */
public class KSCrashInstallation {

    private Context context;
    private List<KSCrashReportFilter> reportFilters;

    public KSCrashInstallation(Context context, List<KSCrashReportFilter> reportFilters) {
        this.context = context;
        this.reportFilters = reportFilters;
    }


    /**
     * 初始化
     * @throws IOException
     */
    public void install() throws IOException {
        KSCrash.getInstance().install(this.context);
    }

    /**
     * More generalized version of this method for debugging purposes. Consider using one of the
     * other two forms instead.
     *
     * @param reports The reports to send.
     * @param callback Called upon successful completion (null = default handler - delete all reports).
     */
    public void sendOutstandingReports(final List reports, KSCrashReportFilter.CompletionCallback callback) {
        if(reports.size() > 0) {
            if (callback == null) {
                callback = new KSCrashReportFilter.CompletionCallback() {
                    @Override
                    public void onCompletion(List reports) throws KSCrashReportFilteringFailedException {
                        KSCrash.getInstance().deleteAllReports();
                    }
                };
            }
            final KSCrashReportFilter.CompletionCallback lastCallback = callback;
            final KSCrashReportFilter pipeline = new KSCrashReportFilterPipeline(reportFilters);
            new AsyncTask<Object, Object, Void>() {
                @Override
                protected Void doInBackground(Object... params) {
                    try {
                        pipeline.filterReports(reports, new KSCrashReportFilter.CompletionCallback() {
                            @Override
                            public void onCompletion(List filteredReports) throws KSCrashReportFilteringFailedException {
                                Log.i("Crash Reporter", "Sent " + reports.size() + " reports.");
                                lastCallback.onCompletion(reports);
                            }
                        });
                    } catch (KSCrashReportFilteringFailedException e) {
                        Log.e("KSCrash", "Error sending reports", e);
                    }
                    return null;
                }
            }.execute();
        } else {
            Log.i("Crash Reporter", "No reports to send.");
        }
    }

    /**
     * Send all outstanding crash reports, with a callback.
     *
     * @param callback Called upon successful completion (null = default handler - delete all reports).
     */
    public void sendOutstandingReports(KSCrashReportFilter.CompletionCallback callback) {
        sendOutstandingReports(KSCrash.getInstance().getAllReports(), callback);
    }

    /**
     * Send all outstanding reports, deleting them from disk upon successful completion.
     */
    public void sendOutstandingReports() {
        sendOutstandingReports(null);
    }
}
