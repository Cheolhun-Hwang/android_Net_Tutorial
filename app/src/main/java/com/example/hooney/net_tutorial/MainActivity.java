package com.example.hooney.net_tutorial;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hooney.net_tutorial.OkHTTP.Delete;
import com.example.hooney.net_tutorial.OkHTTP.Get;
import com.example.hooney.net_tutorial.OkHTTP.Post;
import com.example.hooney.net_tutorial.OkHTTP.Put;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private EditText method;
    private EditText URL;
    private Button send;
    private TextView result;

    //Thread & Handler 를 이용하여 네트워크통신
    private Thread loadData;
    private Handler handler;

    //method 방식 Flag 0:GET 1:POST 2:PUT 3:DELETE
    private int F_method;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        event();
    }

    private boolean init(){
        try{
            //플래그 Default : Get
            F_method = 0;

            //layout resource init
            method = (EditText) this.findViewById(R.id.method);
            URL = (EditText) this.findViewById(R.id.url);
            send = (Button) this.findViewById(R.id.send);
            result = (TextView) this.findViewById(R.id.result);

            //Thread & Handler init
            handler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    switch (msg.what){
                        case 1001:
                            Toast.makeText(getApplicationContext(), "Send OK...", Toast.LENGTH_SHORT).show();
                            String res = (String) msg.obj;
                            result.setText(res);
                            return true;
                        case 1002:
                            Toast.makeText(getApplicationContext(), "Send Fail...", Toast.LENGTH_SHORT).show();
                            return true;
                        default:
                            return false;
                    }
                }
            });

            loadData = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        switch (F_method) {
                            case 0:
                                getResponce(new Get("http://"+URL.getText().toString()).send());
                                break;
                            case 1:
                                JSONObject weather = new JSONObject();
                                weather.put("spot", "seoul");
                                weather.put("temp", "0");
                                getResponce(new Post("http://"+URL.getText().toString()).send(weather.toString()));
                                break;
                            case 2:
                                JSONObject weather2 = new JSONObject();
                                weather2.put("spot", "seoul");
                                weather2.put("name", "1");
                                getResponce(new Put("http://"+URL.getText().toString()).send(weather2.toString()));
                                break;
                            case 3:
                                JSONObject weather3 = new JSONObject();
                                weather3.put("spot", "seoul");
                                getResponce(new Delete("http://"+NURL.getText().toString()).send(weather3.toString()));
                                break;
                            default:
                                Log.e(TAG, "F_Method Error...");
                                break;
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private boolean event(){
        try{
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String w_method = method.getText().toString().toLowerCase();
                    if(w_method.equals("get")||w_method.equals("")){
                        F_method = 0;
                        loadData.start();
                    }else if(w_method.equals("post")){
                        F_method = 1;
                        loadData.start();
                    }else if(w_method.equals("put")){
                        F_method = 2;
                        loadData.start();
                    }else if(w_method.equals("delete")){
                        F_method = 3;
                        loadData.start();
                    }else{
                        Log.e(TAG, "Method Error...");
                        Toast.makeText(getApplicationContext(), "Method 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private void getResponce(String result){
        if(!result.equals("Fail")){
            Message msg = Message.obtain();
            msg.what = 1001;
            msg.obj = result;
            handler.sendMessage(msg);
        }else{
            Message msg = Message.obtain();
            msg.what = 1002;
            msg.obj = result;
            handler.sendMessage(msg);
        }
    }
}
