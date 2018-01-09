package com.example.hooney.net_tutorial.OkHTTP;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hooney on 2018. 1. 9..
 */

public class Get {
    private static final String TAG = "OkHTTP Get Method";
    private String URL;
    private OkHttpClient client;

    public Get(String URL) {
        this.URL = URL;
        this.client = new OkHttpClient();
        Log.d(TAG, URL);
    }

    public Get(String URL, OkHttpClient client) {
        this.URL = URL;
        this.client = client;
    }

    public String send() throws IOException{
        Request request = new Request.Builder()
                .url(URL)
                .build();
        Response response = client.newCall(request).execute();
        Log.d(TAG, "Send Ok...");
        return response.body().string();
    }
}
