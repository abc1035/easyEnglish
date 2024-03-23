package com.example.easyenglish.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easyenglish.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Thread myThread = new Thread(){//创建子线程
            @Override
            public void run() {
                try{
                    sleep(2000);//使程序休眠两秒
                    Intent it=new Intent(getApplicationContext(), LoginActivity.class);//启动LoginActivity
                    startActivity(it);
                    finish();//关闭当前活动
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        myThread.start();//启动线程
    }
}
