package org.stenerud.kscrash.init;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.stenerud.kscrash.IDealWithCrash;
import org.stenerud.kscrash.KSCrash;
import org.stenerud.kscrash.filter.KSCrashReportFilter;
import org.stenerud.kscrash.filter.KSCrashReportFilterPipeline;
import org.stenerud.kscrash.filter.KSCrashReportFilteringFailedException;

import java.io.IOException;
import java.util.List;





/**
 * 初始化操作工具类
 * The installation is the primary user-facing interface to the crash handling system.
 * A user instantiates an installation, optionally configures it, then calls install to
 * start handling crashes.
 *
 * sendOutstandingReports() will send any reports for crashes that occurred previously.
 */
public class KSCrashInstallation {

    private Context mContext;
    private List<KSCrashReportFilter> reportFilters;



    public KSCrashInstallation(Context context, List<KSCrashReportFilter> reportFilters) {
        mContext = context;
        this.reportFilters = reportFilters;
    }

    /**
     * 初始化
     *
     * @throws IOException
     */
    public void install() throws IOException {
        KSCrash.getInstance().install(mContext);
    }

    /**
     * 设置自定义处理crash
     * @param iDealWithCrash
     */
    public void setIDealWithCrash(IDealWithCrash iDealWithCrash){
        KSCrash.getInstance().setIDealWithCrash(iDealWithCrash);
    }

    /**
     * 发送crash日志
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
                                Log.i("Crash Reporter", "--------Sent " + reports.size() + " reports.");
                                lastCallback.onCompletion(reports);
                            }
                        });
                    } catch (KSCrashReportFilteringFailedException e) {
                        Log.e("KSCrash", "--------Error sending reports", e);
                    }
                    return null;
                }
            }.execute();
        } else {
            Log.i("Crash Reporter", "No reports to send.");
        }
    }


    /**
     * 发送所有的错误日志，默认成功后删除日志 <p>
     * Send all reports, deleting them from disk upon successful completion.
     */
    public void sendOutstandingReports() {
        sendOutstandingReports(null);
    }

    /**
     * 发送所有的错误日志，支持自定义发送后的回调<p>
     *
     * Send all crash reports, with a callback.<p>
     *
     * @param callback Called upon successful completion (null = default handler - delete all reports).
     */
    public void sendOutstandingReports(KSCrashReportFilter.CompletionCallback callback) {
        sendOutstandingReports(KSCrash.getInstance().getAllReports(), callback);
    }

}
