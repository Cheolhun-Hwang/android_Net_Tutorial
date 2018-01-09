package com.example.hooney.net_tutorial.HttpURLConnect;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hooney on 2018. 1. 9..
 */

public class Post {
    private static final String TAG = "HttpURLConnect Post Method";

    private String Url;

    public Post(String url) {
        Url = url;
    }

    public String send(){
        HttpURLConnection conn = null;
        try {
            URL url = new URL("requestURL"); //요청 URL을 입력
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST"); //요청 방식을 설정 (default : GET)
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");


            출처: http://arabiannight.tistory.com/entry/안드로이드Android-HttpUrlConnection-JSON-으로-Request-Response-하기 [아라비안나이트]

            conn.setDoInput(true); //input을 사용하도록 설정 (default : true)
            conn.setDoOutput(true); //output을 사용하도록 설정 (default : false)

            conn.setConnectTimeout(60); //타임아웃 시간 설정 (default : 무한대기)

            JSONObject job = new JSONObject();
            job.put("phoneNum", "01000000000");
            job.put("name", "test name");
            job.put("address", "test address");

            OutputStream os = conn.getOutputStream();
            os.write(job.toString().getBytes());
            os.flush();

            conn.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); //캐릭터셋 설정

            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                if(sb.length() > 0) {
                    sb.append("\n");
                }
                sb.append(line);
            }

            Log.d(TAG, "response:" + sb.toString());
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conn != null) {
                conn.disconnect();
            }
        }
        return "Fail";
    }
}
