package com.example.hooney.net_tutorial.HttpURLConnect;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hooney on 2018. 1. 9..
 */

public class Get {
    private static final String TAG = "HttpURLConnect Get Method";

    private String Url;

    public Get(String URL) {
        this.Url = URL;
    }

    public String send(){
        try{
            URL reqURL = new URL(this.Url);
            HttpURLConnection urlConn = (HttpURLConnection) reqURL.openConnection();
            urlConn.setRequestMethod("GET");

            urlConn.setConnectTimeout(60); //타임아웃 시간 설정 (default : 무한대기)

            int resCode = urlConn.getResponseCode();
            Log.d(TAG, "Server ResCode : " + resCode);

            if(resCode != HttpURLConnection.HTTP_OK){
                return "Fail";
            }else{
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                String input;
                StringBuffer buffer = new StringBuffer();

                while ((input = reader.readLine())!=null){
                    buffer.append(input);
                }
                return buffer.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Fail";
    }
}
