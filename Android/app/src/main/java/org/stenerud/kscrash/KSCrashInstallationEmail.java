package org.stenerud.kscrash;

import android.content.Context;

import java.io.File;
import java.util.LinkedList;
import java.util.List;





/**
 * Installation to send reports as attachments over email.
 *
 * Requires the following XML file changes:
 *
 * Modify AndroidManifest.xml:
 *    <application>
 *        ...
 *        <provider
 *            android:name="android.support.v4.content.FileProvider"
 *            android:authorities="${applicationId}.provider"
 *            android:exported="false"
 *            android:grantUriPermissions="true">
 *            <meta-data
 *                android:name="android.support.FILE_PROVIDER_PATHS"
 *                android:resource="@xml/provider_paths"/>
 *        </provider>
 *
 * Create res/xml/provider_paths.xml:
 *    <?xml version="1.0" encoding="utf-8"?>
 *    <paths xmlns:android="http://schemas.android.com/apk/res/android">
 *        <external-path name="external_files" path="."/>
 *    </paths>
 */
public class KSCrashInstallationEmail extends KSCrashInstallation {
    private static List<KSCrashReportFilter> generateFilters(Context context, List<String> recipients, String subject, String message) {
        List<KSCrashReportFilter> reportFilters = new LinkedList<KSCrashReportFilter>();
        reportFilters.add(new KSCrashReportFilterJSONEncode(4));
        reportFilters.add(new KSCrashReportFilterGZipCompress());
        reportFilters.add(new KSCrashReportFilterCreateTempFiles(new File(context.getCacheDir(), "temp"), "report", "gz"));
        reportFilters.add(new KSCrashReportFilterEmail(context, recipients, subject, message));
        reportFilters.add(new KSCrashReportFilterDeleteFiles());

        return reportFilters;
    }
    private static List<String> toList(String value) {
        List list = new LinkedList();
        list.add(value);
        return list;
    }
    private static String getDefaultSubject(Context context) {
        return context.getApplicationInfo().processName + " has crashed";
    }
    private static String getDefaultMessage(Context context) {
        return context.getApplicationInfo().processName + " has crashed.\nReports are attached to this email.";
    }

    /**
     * Constructor.
     *
     * @param context A context that can open an email intent.
     * @param recipients A list of recipients for the email.
     * @param subject The subject of the message.
     * @param message An optional message body to send in addition to the attachments.
     */
    public KSCrashInstallationEmail(Context context, List<String> recipients, String subject, String message) {
        super(context, generateFilters(context, recipients, subject, message));
    }

    /**
     * Constructor.
     *
     * @param context A context that can open an email intent.
     * @param recipient The recipient's email address.
     * @param subject The subject of the message.
     * @param message An optional message body to send in addition to the attachments.
     */
    public KSCrashInstallationEmail(Context context, String recipient, String subject, String message) {
        this(context, toList(recipient), subject, message);
    }

    /**
     * Constructor. Gives a default subject and message.
     *
     * @param context A context that can open an email intent.
     * @param recipients A list of recipients for the email.
     */
    public KSCrashInstallationEmail(Context context, List<String> recipients) {
        super(context, generateFilters(context, recipients, getDefaultSubject(context), getDefaultMessage(context)));
    }

    /**
     * Constructor. Gives a default subject and message.
     *
     * @param context A context that can open an email intent.
     * @param recipient The recipient's email address.
     */
    public KSCrashInstallationEmail(Context context, String recipient) {
        this(context, toList(recipient));
    }
}
