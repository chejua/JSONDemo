package com.example.geo.jsondemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    public class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {

                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while(data != -1){

                    char current = (char) data;
                    result += current;
                    data = reader.read();

                }

                return result;


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    DownloadTask task;
    String result = "";
    public void showToast(View view){

        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();


    }

    public void showLog(View view){

        Log.i("Result is here", result);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        task = new DownloadTask();

        try {
            result = task.execute("http://help.websiteos.com/websiteos/example_of_a_simple_html_page.htm").get();

        } catch (InterruptedException e) {

            e.printStackTrace();

        } catch (ExecutionException e) {

            e.printStackTrace();
        }

    }
}
