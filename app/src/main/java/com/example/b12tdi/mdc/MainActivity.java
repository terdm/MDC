package com.example.b12tdi.mdc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    //AsyncT asyncT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d("MyTag","MainActivity onCreate starts ");
        setContentView(R.layout.activity_main);
        //asyncT = new AsyncT();
    }

    public void onButtonLog(View v) {
        Log.d("MyTag","onButtonLog starts ");
        String[] cmd = new String[] { "logcat", "-f", "/storage/extSdCard/log/MyTag", "-v", "time", "ActivityManager:W", "myapp:D" };
        try {
            Runtime.getRuntime().exec(cmd);
        }
        catch (IOException e) {
            Log.d("MyTag",e.toString());
        }

    }

    public void onButtonStart(View v) {
        Log.d("MyTag","onButtonStart starts ");

        try {
            //asyncT.execute("Start");}
            new AsyncT().execute("Start");
        }
        catch (Exception ex) {
            Log.d("MyTag","onButtonStart error " + ex.toString());
        }


    }

    public void onButtonStop(View v) {
        Log.d("MyTag","onButtonStop stop ");
        try {
          //asyncT.execute("Stop");}
            new AsyncT().execute("Stop");
        }
        catch (Exception ex) {
            Log.d("MyTag","onButtonStop error " + ex.toString());
        }


    }

}
