package com.example.b12tdi.mdc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
public class MainActivity extends AppCompatActivity {

    //AsyncT asyncT;
    String refreshedToken;
    String logUri ="";

    TextView myAwesomeTextView;
    TextView editText;
    String TAG = "MyTag";
    String[] detectors = {"e9Acm88_C_U:APA91bHCtb4ex9_2hDKhhtLxL_7kX7Y747M7UkjSTX5iHuNJdacJ3pT9oMtW_FLRkT7OXGLZgP5MReYH9Wlz3abMwzqYm671aj3vritzkm-mgLh52Dq1GsfbloozESGclKmzFrERQm6p","eYn1KPG5u-Y:APA91bHcjmzb-tN_BnuBLFd1WkqStZ2-Ie1B54vWNItyvmF3iMWB3yNjyghtyBUbh2XIm-Q5POs9RRI9L3OiRtgpkcypvei_aCbGQHBVQ1FwT4NA7tmaQntYdu4CKigOwGTyDO8CRssk"};
    private FirebaseAuth mAuth;
    //private RecyclerView tweetsRecyclerView;
    private DatabaseReference mDatabase;

    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

// ...
        mAuth = FirebaseAuth.getInstance();
        myLogging("MainActivity onCreate starts ");
        setContentView(R.layout.activity_main);
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        myLogging( "token "+ refreshedToken);
        //myAwesomeTextView = (TextView)findViewById(R.id.myAwesomeTextView);
        editText = (TextView)findViewById(R.id.editText);
        //myAwesomeTextView.setText(refreshedToken);
        editText.setText("onCreate");
myLogging("before register receiver");
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


        myLogging("on_create ends");
    }
    @Override
    public void onStart() {
        super.onStart();
        myLogging("on_start starts");
        // Check if user is signed in (non-null) and update UI accordingly.
        try{
        //FirebaseUser currentUser = mAuth.getCurrentUser();}
            FirebaseUser currentUser = mAuth.getCurrentUser();}
        catch (Exception ex) {myLogging("error in getcurrentuser " + ex.toString());}
        myLogging("before updateUI");
        updateUI(currentUser);
        myLogging("on start ends");
    }

    public void onButtonLog(View v) {
        myLogging("onButtonLog starts ");
        editText.setText("onButtonLog strats");
        String logCatFile;
        String txt;
        Boolean bool;



        Date currentTime = Calendar.getInstance().getTime();
        logCatFile = "logcat.txt";
        //String[] cmd = new String[] { "logcat", "-f", "/storage/extSdCard/log/MyTag", "-v", "time", "ActivityManager:W", "myapp:D" };
        //String[] cmd = new String[] { "logcat", "-f", "/storage/emulated/0/log/MyTag", "-v", "time", "ActivityManager:W", "myapp:D" };
        String sFilePath = Environment.getExternalStorageDirectory().toString() + "/MDC/";
        //String[] cmd = new String[] { "logcat", "-f", sFilePath, "MyAppTAG:V", "*:S"};
        File filename = new File(sFilePath+"/"+ logCatFile);
        filename.delete();
        myLogging( "before createNewFile");
        try {
            filename.createNewFile();
        }
        catch (IOException ex) {
            myLogging( "exeption "+ ex.toString());
        }
        myLogging( "before cmd");
        String cmd = "logcat -d -f"+filename.getAbsolutePath();
        //String[] cmd = new String[] { "logcat", "-f", sFilePath, "MyTag:V", "*:S"};
        Runtime rt = Runtime.getRuntime();
        File dir = new File(sFilePath);
        String vErr;
        vErr = "dir.toString()+\" isSDCARDAvailable \"+ isSDCARDAvailable()";
        Toast.makeText(this, vErr, Toast.LENGTH_SHORT).show();
        myLogging(vErr);



        if (!dir.exists()) {
            // Make it, if it doesn't exit
            bool = dir.mkdirs();
            myLogging( dir.toString() + " mkdirs");
            Toast.makeText(this, "mkdirs " + bool,
                    Toast.LENGTH_SHORT).show();

        }
        else {
            myLogging( dir.toString() + " already exists");
        }

        Toast.makeText(this, dir.toString(),
                Toast.LENGTH_SHORT).show();

       // file.delete();

        /*Toast.makeText(this, file.toString(),
                Toast.LENGTH_SHORT).show();
        try {
            file.createNewFile();
        } catch (IOException e) {
            Toast.makeText(this, e.toString(),
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
*/
        try {
           /*
            Runtime.getRuntime().exec(cmd);
            Toast.makeText(this, "logcat to file",
                    Toast.LENGTH_SHORT).show();
            editText.setText("logcat to file ");*/
           myLogging("cmd "+ cmd);
            Process proc = rt.exec(cmd);
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(proc.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(proc.getErrorStream()));

            String s = null;
            while ((s = stdInput.readLine()) != null) {
                //System.out.println(s);
                Toast.makeText(this, s,
                        Toast.LENGTH_SHORT).show();
            }

            while ((s = stdError.readLine()) != null) {
               // System.out.println(s);
                Toast.makeText(this, s,
                        Toast.LENGTH_SHORT).show();
            }
        }
        catch (IOException e) {
            myLogging(e.toString());
            editText.setText("onButtonLog IO Error " +  e.toString());
        }
        catch (Exception e) {
            myLogging(e.toString());
            editText.setText("onButtonLog Error " +  e.toString());
        }


        if (filename.exists()) {
            editText.setText("Log saved to file "+ filename.toString() + " length " + filename.length());
            //Do action
        }
        else
        {editText.setText("log was not saved");}
        myLogging("After saving logcat.txt");
try {
    mAuth.signInAnonymously()
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInAnonymously:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInAnonymously:failure", task.getException());
                        myLogging("Authentication failed.");
                        updateUI(null);
                    }

                    // ...
                }
            });

    FirebaseStorage storage = FirebaseStorage.getInstance();
    myLogging("after FirebaseStorage.getInstance");
    // Create a storage reference from our app
    //Затем, как и в базе данных или Firestore, мы получим ссылку на корневую папку нашего хранилища.
    StorageReference rootRef = storage.getReference();
    //Тогда, как и в Realtime Database, вы можете пойти ниже по дереву с помощью
    StorageReference myRef = rootRef.child("logs/MDC" + currentTime.toString());
    //Дополнительная навигация
    //Как и в случае с базами данных, у вас есть методы  getParent и getRoot для навигации.

    //Загрузка файлов в хранилище
    //До сих пор мы рассматривали ссылки на каталоги. Однако с хранилищем, в отличие от баз данных Firebase,
    // ссылки могут указывать на местоположение файла . Я покажу вам, что имею в виду.
    StorageReference logRef = myRef.child(logCatFile);

    UploadTask uploadTask;

    //Этот метод принимает Uri файла и снова возвращает UploadTask.
    Uri file = Uri.fromFile(filename);
    //StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
    uploadTask = logRef.putFile(file);
    myLogging("after put file");


    logUri = "no url";

// Register observers to listen for when the download is done or if it fails
    uploadTask.addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception exception) {
            // Handle unsuccessful uploads
            logUri = "error";
            myLogging("NOT loaded " + exception.toString());
        }
    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
            Uri downloadUrl = taskSnapshot.getDownloadUrl();
            logUri = downloadUrl.toString();
            myLogging("Successful load uri " + downloadUrl.toString());
        }
    });

    //////////////////////////////////////////////////////////
    FirebaseDatabase database =  FirebaseDatabase.getInstance();
    //FirebaseUser user =  mAuth.getCurrentUser();

    News news = new News("MDC log", "log " + currentTime.toString(), logUri,logUri);
    //String newsId = news.getUid();
    DatabaseReference mRef =  database.getReference().child("News").push();
    mRef.setValue(news);
    //////////////////////////////////////////////////////////


/*
     // Register observers to listen for when the download is done or if it fails
    uploadTask.addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception exception) {
            // Handle unsuccessful uploads


            myLogging("NOT loaded " + exception.toString());

        }
    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
            // ...

            myLogging("Successful load");

        }
    });
    */
}catch (Exception ex) {
    myLogging( "firebase exception " + ex.toString());
}
        myLogging( "onButtonLog ends");
    }
    public void onW_Log(View v) {
        myLogging("onW_Log starts ");
        try {
            new AsyncT().execute("Log",refreshedToken,detectors[1]);
            editText.setText("onW_Log");
        }
        catch (Exception ex) {
            myLogging("onW_Log  error " + ex.toString());
            editText.setText("onW_Log error " + ex.toString());
        }
    }

    public void onB_Log(View v) {
        myLogging("onB_Log starts ");
        try {
            new AsyncT().execute("Log",refreshedToken,detectors[0]);
            editText.setText("onB_Log");
        }
        catch (Exception ex) {
            myLogging("onB_Log  error " + ex.toString());
            editText.setText("onB_Log error " + ex.toString());
        }
    }

    public void onButtonGo(View v) {
        myLogging("go starts ");
        try {
            startActivity(new Intent(getApplicationContext(), NewsActivity.class));
        }
        catch (Exception ex) {
            myLogging("onB_Log  error " + ex.toString());
            editText.setText("onB_Log error " + ex.toString());
        }
    }


    public void onButtonStart(View v) {
        myLogging("onButtonStart starts ");

        try {
            //asyncT.execute("Start");}
            new AsyncT().execute("Start",refreshedToken,detectors[0]);
            new AsyncT().execute("Start",refreshedToken,detectors[1]);
            editText.setText("onButtonStart");
        }
        catch (Exception ex) {
            myLogging("onButtonStart  error " + ex.toString());
            editText.setText("onButtonStart error " + ex.toString());
        }
    }

    public void onW_Start(View v) {
        myLogging("onW_Start starts ");

        try {
            //asyncT.execute("Start");}

            new AsyncT().execute("Start",refreshedToken,detectors[1]);
            editText.setText("onW_Start");
        }
        catch (Exception ex) {
            myLogging("onW_Start  error " + ex.toString());
            editText.setText("onW_Start error " + ex.toString());
        }
    }

    public void onB_Start(View v) {
        myLogging("onB_Start starts ");

        try {
            //asyncT.execute("Start");}
            new AsyncT().execute("Start",refreshedToken,detectors[0]);

            editText.setText("onB_Start");
        }
        catch (Exception ex) {
            myLogging("onB_Start  error " + ex.toString());
            editText.setText("onB_Start error " + ex.toString());
        }
    }

    public void onButtonStop(View v) {
        myLogging("onButtonStop stop ");
        try {
            //asyncT.execute("Stop");}
            new AsyncT().execute("Stop",refreshedToken,detectors[0]);
            new AsyncT().execute("Stop",refreshedToken,detectors[1]);

            editText.setText("onButtonStop");
        }
        catch (Exception ex) {
            myLogging("onButtonStop error " + ex.toString());
            editText.setText("onButtonStop error " + ex.toString());
        }
    }

    public void onW_Stop(View v) {
        myLogging("onW_Stop stop ");
        try {
            //asyncT.execute("Stop");}

            new AsyncT().execute("Stop",refreshedToken,detectors[1]);

            editText.setText("onW_Stop");
        }
        catch (Exception ex) {
            myLogging("onW_Stop error " + ex.toString());
            editText.setText("onW_Stop error " + ex.toString());
        }
    }

    public void onB_Stop(View v) {
        myLogging("onB_Stop stop ");
        try {
            //asyncT.execute("Stop");}

            new AsyncT().execute("Stop",refreshedToken,detectors[0]);

            editText.setText("onB_Stop");
        }
        catch (Exception ex) {
            myLogging("onB_Stop error " + ex.toString());
            editText.setText("onB_Stop error " + ex.toString());
        }
    }
    public static boolean isSDCARDAvailable(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
public void myLogging(String s){
        Log.d(TAG, s);
}
    private void updateUI(FirebaseUser user) {
        myLogging("updateUI starts");
        /*hideProgressDialog();
        if (user != null) {
            mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
        */
        if (user== null)
        {myLogging("user is null");}
        else
        {myLogging(user.toString());}
    }

}
