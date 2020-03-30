package com.example.easyenglish;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import Connect.ConnectToDataBase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ConnectToDataBase a=new ConnectToDataBase();
        if(a.studentphonelogin("123","123")==1)
        {
            System.out.println("ok");
        }
        else
        {
            System.out.println("no");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
