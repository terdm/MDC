package com.example.b12tdi.mdc;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class AsyncT extends AsyncTask<String,Void,Void> {

    @Override
    protected Void doInBackground(String... params) {

        try {
            Log.d("MyTag","AsyncTask   starts  ");
            URL url = new URL("https://fcm.googleapis.com/fcm/send"); //Enter URL here
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
            httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
            httpURLConnection.setRequestProperty("Authorization","key=AAAAZAyzAtg:APA91bFg-Mb80iXj2bsat4eDcuq6LAsaxHLAazZbdwXYL2JMjWAu3eBVH82fj635EjEB4gYqCHl4s2HuOp1l24zddDK2Q-nr-Ulvy9JGxG1WpLrdhrdhkiesWu92d8VmI_Kr0VCZyTH1");
            httpURLConnection.connect();

            JSONObject jsonObject = new JSONObject();
            JSONObject param = new JSONObject();
            param.put("Hii",params[0]);

            jsonObject.put("data",param);
            //black
            //jsonObject.put("to", "e9Acm88_C_U:APA91bHCtb4ex9_2hDKhhtLxL_7kX7Y747M7UkjSTX5iHuNJdacJ3pT9oMtW_FLRkT7OXGLZgP5MReYH9Wlz3abMwzqYm671aj3vritzkm-mgLh52Dq1GsfbloozESGclKmzFrERQm6p");
            // white
            jsonObject.put("to", "eYn1KPG5u-Y:APA91bHcjmzb-tN_BnuBLFd1WkqStZ2-Ie1B54vWNItyvmF3iMWB3yNjyghtyBUbh2XIm-Q5POs9RRI9L3OiRtgpkcypvei_aCbGQHBVQ1FwT4NA7tmaQntYdu4CKigOwGTyDO8CRssk");
            Log.d("MyTag","AsyncTask  before DataOutputStream");
            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());

            Log.d("MyTag","AsyncTask  before writeBytes");
            wr.writeBytes(jsonObject.toString());
            Log.d("MyTag","AsyncTask  before flush");
            wr.flush();
            Log.d("MyTag","AsyncTask  before close");
            wr.close();

            //Read
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));

            String line = null;


            while ((line = bufferedReader.readLine()) != null) {

                Log.d("MyTag","    " + line.toString());
            }

            bufferedReader.close();

        } catch (MalformedURLException e) {
            Log.d("MyTag","AsyncTask  MalformedURLException " + e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("MyTag","AsyncTask  IOException " + e.toString());
            e.printStackTrace();
        } catch (JSONException e) {
            Log.d("MyTag","AsyncTask  JSONException " + e.toString());
            e.printStackTrace();
        }

        return null;
    }


}