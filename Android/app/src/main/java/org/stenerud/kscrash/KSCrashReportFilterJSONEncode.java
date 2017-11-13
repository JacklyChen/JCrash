package org.stenerud.kscrash;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;


/**
 * 将对象转换为Json字符串工具类
 * Encodes objects into a JSON encoded string.
 * <p>
 * Input: JSON encodable object
 * Output: String (JSON encoded)
 */
public class KSCrashReportFilterJSONEncode implements KSCrashReportFilter {

    private int indentDepth = -1;



    /**
     * Constructor.
     *
     * @param indentDepth The depth to indent while pretty printing (-1 = do not pretty print).
     */
    public KSCrashReportFilterJSONEncode(int indentDepth) {
        this.indentDepth = indentDepth;
    }

    /**
     * Constructor.
     * Do not pretty print.
     */
    public KSCrashReportFilterJSONEncode() {
        this(-1);
    }

    @Override
    public void filterReports(List reports, CompletionCallback completionCallback)
            throws KSCrashReportFilteringFailedException {
        List<String> processedReports = new LinkedList<String>();
        try {
            for (Object report : reports) {
                JSONObject object = (JSONObject) report;
                String string;
                if (indentDepth >= 0) {
                    string = object.toString(indentDepth);
                } else {
                    string = object.toString();
                }
                processedReports.add(string);
            }
        } catch (Throwable error) {
            throw new KSCrashReportFilteringFailedException(error, reports);
        }
        completionCallback.onCompletion(processedReports);
    }
}
