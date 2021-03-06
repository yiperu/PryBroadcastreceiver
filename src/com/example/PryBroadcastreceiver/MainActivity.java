package com.example.PryBroadcastreceiver;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    EditText mEditText = null;
    Button miButon = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mEditText = (EditText)findViewById(R.id.idEditTextUrl);
        miButon = (Button)findViewById(R.id.idBtnSendUrl);

        miButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringUrl = mEditText.getText().toString();
                if (isNetworkAvailable()) {
                    //new DownloadURL().execute(stringUrl);
                    obtainValueForCity("lima");
                } else{
                    System.out.println("No hay Conexion Disponible");
                }

            }
        });




    }

    public boolean isNetworkAvailable(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
    public boolean isWifiConnection(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifi = networkInfo.isConnected();
        return isWifi;
    }

    public boolean isMobileConnection(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobile = networkInfo.isConnected();
        return isMobile;
    }

    private void readStream(InputStream in){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while((line = reader.readLine()) != null){
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {}
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class DownloadURL extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            System.out.println("==================  INICIO  ==================");
            try {
                URL myurl = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection)myurl.openConnection();
                readStream(con.getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("================== FIN ======================");
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }
    }

    private void connectionWithHttpClient(){
        HttpClient httpClient = new DefaultHttpClient();

        HttpPost httpPost = new HttpPost("http://www.example.com/login");

        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>(2);
        nameValuePairList.add(new BasicNameValuePair("email","yiperu@gmail.com"));
        nameValuePairList.add(new BasicNameValuePair("password","123456"));

        try {
            HttpResponse response = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void obtainValueForCity(String ciudad){
        String url = "http://maps.google.com/maps/api/geocode/json?address="+ciudad+"&sensor=false";
        new DownloadURL().execute(url);
    }


}
