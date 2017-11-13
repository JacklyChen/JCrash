package org.stenerud.kscrash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;






/**
 * Take the contents of each report and store it in a temp file.
 * The files will be named using incrementing numbers of the form:
 *     [prefix]-[index].[extension]
 *
 * Index starts at 1
 *
 * The files themselves are passed on to the completion handler.
 *
 * Input: String or byte[]
 * Output: File
 */
public class KSCrashReportFilterCreateTempFiles implements KSCrashReportFilter {

    private File tempDir;
    private String prefix;
    private String extension;




    /**
     * Constructor.
     *
     * @param tempDir The directory to create the temporary files in.
     * @param prefix What prefix to use for the files.
     * @param extension What extension to use for the files.
     */
    public KSCrashReportFilterCreateTempFiles(File tempDir, String prefix, String extension) {
        this.tempDir = tempDir;
        this.prefix = prefix;
        this.extension = extension;
    }
    @Override
    public void filterReports(List reports, CompletionCallback completionCallback) throws KSCrashReportFilteringFailedException {
        try {
            tempDir.mkdir();
            List<File> files = new LinkedList<File>();
            int index = 1;
            for(Object report: reports) {
                File tempFile = new File(this.tempDir, this.prefix + "-" + index + "." + this.extension);
                OutputStream outStream = new FileOutputStream(tempFile);
                if(report instanceof String) {
                    Writer out = new OutputStreamWriter(outStream, "UTF-8");
                    try {
                        out.write((String)report);
                    } finally {
                        out.close();
                    }
                } else if(report instanceof byte[]) {
                    try {
                        outStream.write((byte[])report);
                    } finally {
                        outStream.close();
                    }
                } else {
                    throw new IllegalArgumentException("Unhandled class type: " + report.getClass());
                }
                files.add(tempFile);
                index++;
            }
            completionCallback.onCompletion(files);
        } catch (Throwable error) {
            throw new KSCrashReportFilteringFailedException(error, reports);
        }
    }
}
