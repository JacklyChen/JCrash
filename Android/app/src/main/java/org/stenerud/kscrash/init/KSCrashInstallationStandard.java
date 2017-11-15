package org.stenerud.kscrash.init;

import android.content.Context;

import org.stenerud.kscrash.filter.KSCrashReportFilter;
import org.stenerud.kscrash.filter.KSCrashReportFilterGZipCompress;
import org.stenerud.kscrash.filter.KSCrashReportFilterHttp;
import org.stenerud.kscrash.filter.KSCrashReportFilterJSONEncode;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;




/**
 * 通过http请求，接口上传crash日志
 */
public class KSCrashInstallationStandard extends KSCrashInstallation {

    /**
     * 构造方法
     * @param context A context.
     * @param url The URL to post the reports to.
     */
    public KSCrashInstallationStandard(Context context, URL url) {
        super(context, generateFilters(url));
    }


    /**
     * 产生过滤器
     * @param url
     * @return
     */
    private static List<KSCrashReportFilter> generateFilters(URL url) {
        List<KSCrashReportFilter> reportFilters = new LinkedList<KSCrashReportFilter>();
        reportFilters.add(new KSCrashReportFilterJSONEncode(4));
        reportFilters.add(new KSCrashReportFilterGZipCompress());
        reportFilters.add(new KSCrashReportFilterHttp(url, "report", "json.gz"));
        return reportFilters;
    }
}
