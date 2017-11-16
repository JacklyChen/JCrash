package org.stenerud.kscrash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;
import org.stenerud.kscrash.filter.KSCrashReportFilter;
import org.stenerud.kscrash.filter.KSCrashReportFilteringFailedException;
import org.stenerud.kscrash.init.KSCrashInstallation;
import org.stenerud.kscrash.init.KSCrashInstallationLocal;
import org.stenerud.kscrash.init.KSCrashInstallationUrl;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            //日志本地处理
            KSCrashInstallationLocal.INSTANCE.install(MainActivity.this);
            //统计SDK拿到后进行封装符合格式的数据并进行存储
            KSCrashInstallationLocal.INSTANCE.setIDealWithCrash(new IDealWithCrash(){  //属于耗时操作
                @Override
                public void dealWithCrash(Throwable summary, String detail) {
                    Log.e(TAG, "dealWithCrash summary----------" + summary.toString());
                    Log.e(TAG, "dealWithCrash detail----------" + detail);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Button javaButton = (Button) findViewById(R.id.button_java);
        javaButton.setOnClickListener(this);

        final Button nativeButton = (Button) findViewById(R.id.button_native);
        nativeButton.setOnClickListener(this);

        final Button cppButton = (Button) findViewById(R.id.button_cpp);
        cppButton.setOnClickListener(this);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        String result = stringFromTimestamp(0);
        tv.setText(result);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_native:
                causeNativeCrash();
                break;
            case R.id.button_cpp:
                causeCPPException();
                break;
            case R.id.button_java:
                sendFakeReports();
                throw new IllegalArgumentException("Argument was illegal or something");
            default:
                break;
        }
    }

    /**
     * 上传假数据
     */
    private void sendFakeReports() {
        try {
            //todo 日志上传服务器
            List reports = new LinkedList();
            Map report = new HashMap();
            report.put("test", "a value");
            reports.add(new JSONObject(report));
            URL url = new URL("http://10.0.2.2:5000/crashreport");
            //封装网络请求
            KSCrashInstallation installation = new KSCrashInstallationUrl(this, url);
            installation.sendOutstandingReports(reports, new KSCrashReportFilter.CompletionCallback() {
                @Override
                public void onCompletion(List reports) throws KSCrashReportFilteringFailedException {
                    Log.i("MainActivity", "Sent " + reports.size() + " reports");
                }
            });
        } catch(Exception e) {
            Log.e("MainActivity", "Error sending fake reports", e);
        }
    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromTimestamp(long timestamp);
    private native void causeNativeCrash();
    private native void causeCPPException();


}
