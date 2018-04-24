package com.example.b12tdi.mdc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    //AsyncT asyncT;
    String refreshedToken;

    TextView myAwesomeTextView;
    TextView editText;
    String[] detectors = {"e9Acm88_C_U:APA91bHCtb4ex9_2hDKhhtLxL_7kX7Y747M7UkjSTX5iHuNJdacJ3pT9oMtW_FLRkT7OXGLZgP5MReYH9Wlz3abMwzqYm671aj3vritzkm-mgLh52Dq1GsfbloozESGclKmzFrERQm6p","eYn1KPG5u-Y:APA91bHcjmzb-tN_BnuBLFd1WkqStZ2-Ie1B54vWNItyvmF3iMWB3yNjyghtyBUbh2XIm-Q5POs9RRI9L3OiRtgpkcypvei_aCbGQHBVQ1FwT4NA7tmaQntYdu4CKigOwGTyDO8CRssk"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d("MyTag","MainActivity onCreate starts ");
        setContentView(R.layout.activity_main);
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Firebase", "token "+ refreshedToken);
        myAwesomeTextView = (TextView)findViewById(R.id.myAwesomeTextView);
        editText = (TextView)findViewById(R.id.editText);
        myAwesomeTextView.setText(refreshedToken);
        editText.setText("onCreate");

        LocalBroadcastManager.getInstance(this).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String message = intent.getStringExtra(MyFirebaseMessagingService.EXTRA_MESSAGE);

                        editText.setText(message);
                    }
                }, new IntentFilter(MyFirebaseMessagingService.ACTION_MESSAGE_BROADCAST)
        );

        //asyncT = new AsyncT();
    }

    public void onButtonLog(View v) {
        Log.d("MyTag","onButtonLog starts ");
        editText.setText("onButtonLog strats");
        //String[] cmd = new String[] { "logcat", "-f", "/storage/extSdCard/log/MyTag", "-v", "time", "ActivityManager:W", "myapp:D" };
        //String[] cmd = new String[] { "logcat", "-f", "/storage/emulated/0/log/MyTag", "-v", "time", "ActivityManager:W", "myapp:D" };
        String filePath = Environment.getExternalStorageDirectory() + "/logcat.txt";
        String[] cmd = new String[] { "logcat", "-f", filePath, "MyAppTAG:V", "*:S"};

        try {
            Runtime.getRuntime().exec(cmd);
            editText.setText("Log saved to file "+ filePath);

        }
        catch (IOException e) {
            Log.d("MyTag",e.toString());
            editText.setText("onButtonLog Error " +  e.toString());
        }
    }
    public void onW_Log(View v) {
        Log.d("MyTag","onW_Log starts ");
        try {
            new AsyncT().execute("Log",refreshedToken,detectors[1]);
            editText.setText("onW_Log");
        }
        catch (Exception ex) {
            Log.d("MyTag","onW_Log  error " + ex.toString());
            editText.setText("onW_Log error " + ex.toString());
        }
    }

    public void onB_Log(View v) {
        Log.d("MyTag","onB_Log starts ");
        try {
            new AsyncT().execute("Log",refreshedToken,detectors[0]);
            editText.setText("onB_Log");
        }
        catch (Exception ex) {
            Log.d("MyTag","onB_Log  error " + ex.toString());
            editText.setText("onB_Log error " + ex.toString());
        }
    }

    public void onButtonStart(View v) {
        Log.d("MyTag","onButtonStart starts ");

        try {
            //asyncT.execute("Start");}
            new AsyncT().execute("Start",refreshedToken,detectors[0]);
            new AsyncT().execute("Start",refreshedToken,detectors[1]);
            editText.setText("onButtonStart");
        }
        catch (Exception ex) {
            Log.d("MyTag","onButtonStart  error " + ex.toString());
            editText.setText("onButtonStart error " + ex.toString());
        }
    }

    public void onW_Start(View v) {
        Log.d("MyTag","onW_Start starts ");

        try {
            //asyncT.execute("Start");}

            new AsyncT().execute("Start",refreshedToken,detectors[1]);
            editText.setText("onW_Start");
        }
        catch (Exception ex) {
            Log.d("MyTag","onW_Start  error " + ex.toString());
            editText.setText("onW_Start error " + ex.toString());
        }
    }

    public void onB_Start(View v) {
        Log.d("MyTag","onB_Start starts ");

        try {
            //asyncT.execute("Start");}
            new AsyncT().execute("Start",refreshedToken,detectors[0]);

            editText.setText("onB_Start");
        }
        catch (Exception ex) {
            Log.d("MyTag","onB_Start  error " + ex.toString());
            editText.setText("onB_Start error " + ex.toString());
        }
    }

    public void onButtonStop(View v) {
        Log.d("MyTag","onButtonStop stop ");
        try {
            //asyncT.execute("Stop");}
            new AsyncT().execute("Stop",refreshedToken,detectors[0]);
            new AsyncT().execute("Stop",refreshedToken,detectors[1]);

            editText.setText("onButtonStop");
        }
        catch (Exception ex) {
            Log.d("MyTag","onButtonStop error " + ex.toString());
            editText.setText("onButtonStop error " + ex.toString());
        }
    }

    public void onW_Stop(View v) {
        Log.d("MyTag","onW_Stop stop ");
        try {
            //asyncT.execute("Stop");}

            new AsyncT().execute("Stop",refreshedToken,detectors[1]);

            editText.setText("onW_Stop");
        }
        catch (Exception ex) {
            Log.d("MyTag","onW_Stop error " + ex.toString());
            editText.setText("onW_Stop error " + ex.toString());
        }
    }

    public void onB_Stop(View v) {
        Log.d("MyTag","onB_Stop stop ");
        try {
            //asyncT.execute("Stop");}

            new AsyncT().execute("Stop",refreshedToken,detectors[0]);

            editText.setText("onB_Stop");
        }
        catch (Exception ex) {
            Log.d("MyTag","onB_Stop error " + ex.toString());
            editText.setText("onB_Stop error " + ex.toString());
        }
    }

}
