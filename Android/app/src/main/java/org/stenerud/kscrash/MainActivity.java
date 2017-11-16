package org.stenerud.kscrash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.stenerud.kscrash.init.KSCrashInstallationLocal;

import java.io.IOException;
import java.sql.SQLException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int taskId = 1;
        int appId = 1000;
        String taskVersion = "1.0.0";
        String channel = "Lily";

        try {  //在处理native异常时可能会跑IOException
            //日志本地处理
            KSCrashInstallationLocal.INSTANCE.install(MainActivity.this, taskId, appId, taskVersion, channel);
        } catch (IOException e) {
            e.printStackTrace();
        } catch(SQLException e){
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
            case R.id.button_java:
                throw new IllegalArgumentException("Argument was illegal or something");
            case R.id.button_native:
                causeNativeCrash();
                break;
            case R.id.button_cpp:
                causeCPPException();
                break;
            default:
                break;
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
