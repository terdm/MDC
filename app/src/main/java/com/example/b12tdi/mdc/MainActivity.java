package com.example.b12tdi.mdc;

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

        //asyncT = new AsyncT();
    }

    public void onButtonLog(View v) {
        Log.d("MyTag","onButtonLog starts ");
        editText.setText("onButtonLog strats");
        //String[] cmd = new String[] { "logcat", "-f", "/storage/extSdCard/log/MyTag", "-v", "time", "ActivityManager:W", "myapp:D" };
        String[] cmd = new String[] { "logcat", "-f", "/storage/emulated/0/log/MyTag", "-v", "time", "ActivityManager:W", "myapp:D" };
        try {
            Runtime.getRuntime().exec(cmd);
            editText.setText("Log saved to file");

        }
        catch (IOException e) {
            Log.d("MyTag",e.toString());
            editText.setText("onButtonLog Error " +  e.toString());

        }

    }

    public void onButtonStart(View v) {
        Log.d("MyTag","onButtonStart starts ");

        try {
            //asyncT.execute("Start");}
            new AsyncT().execute("Start");
            editText.setText("onButtonStart");
        }
        catch (Exception ex) {
            Log.d("MyTag","onButtonStart  error " + ex.toString());
            editText.setText("onButtonStart error " + ex.toString());
        }


    }

    public void onButtonStop(View v) {
        Log.d("MyTag","onButtonStop stop ");
        try {
          //asyncT.execute("Stop");}
            new AsyncT().execute("Stop");
            editText.setText("onButtonStop");
        }
        catch (Exception ex) {
            Log.d("MyTag","onButtonStop error " + ex.toString());
            editText.setText("onButtonStop error " + ex.toString());
        }


    }

}
