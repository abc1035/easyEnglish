package com.example.easyenglish;

import androidx.appcompat.app.AppCompatActivity;

import Connect.ConnectMessage;
import Connect.ConnectToDataBase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {
    /*
        private EditText etxtUserName;
        etxtUserName=(EditText) findViewById(R.id.et_username);
        object.put("userphonenumber", etxtUserName.getText().toString());
     */
    EditText Username, Password;
    private static boolean mBackKeyPressed = false;//记录是否有首次按键

    @Override
    public void onBackPressed() {
        if(!mBackKeyPressed){
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {//延时两秒，如果超出则擦错第一次按键记录
                @Override
                public void run() {
                    mBackKeyPressed = false;
                }
            }, 2000);
        }
        else{//退出程序
            this.finish();
            //System.exit(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Username = findViewById(R.id.et_username);
                Password = findViewById(R.id.et_password);
                final String username = Username.getText().toString().trim();
                final String password = Password.getText().toString().trim();
                System.out.println("login");

                if(username.equals(""))
                    Toast.makeText(LoginActivity.this,"请输入邮箱/手机号",Toast.LENGTH_SHORT).show();
                else if(password.equals(""))
                    Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                else
                    new Thread(LoginRun).start();

            }
            ConnectMessage a=new ConnectMessage();
            Runnable LoginRun=new Runnable() {
                @Override
                public void run() {
                    try {
                        Username = findViewById(R.id.et_username);
                        Password = findViewById(R.id.et_password);
                        final String username = Username.getText().toString().trim();
                        final String password = Password.getText().toString().trim();
                        JSONObject object = new JSONObject();
                        if(username.indexOf("@")==-1){//手机登录
                            object.put("jsonid", "1");
                            object.put("userphonenumber",username);

                        }
                        else{//邮箱注册
                            object.put("jsonid", "2");
                            object.put("mail",username);
                        }
                        object.put("userpassword", password);
                        final String result = object.toString();//json输出部分
                        //Log.i("jSON字符串", result);

                        //端口号为6666，自己选择，只要不和电脑中的其他程序冲突即可
                        Socket socket = new Socket(a.GetIpAddress(), 6666);//此处连接后端
                        System.out.println("loginsuccess");
                        OutputStream os = socket.getOutputStream();
                        DataOutputStream out=new DataOutputStream(os);
                        out.writeUTF(result);// 向服务器传送json信息


                        InputStream is = socket.getInputStream();
                        DataInputStream in=new DataInputStream(is);
                        String str = in.readUTF();//读入运行结果
                        object = JSONObject.parseObject(str);//转化为json
                        int status = object.getInteger("status");

                        Looper.prepare();
                        if(status==0)
                            Toast.makeText(LoginActivity.this,"该用户不存在！",Toast.LENGTH_SHORT).show();
                        else if(status==1)
                            Toast.makeText(LoginActivity.this,"密码错误，请重试！",Toast.LENGTH_SHORT).show();
                        else{
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        }
                        Looper.loop();
                    }
                    catch(Exception e){
                        String TAG="login";
                        Log.e(TAG,Log.getStackTraceString(e));
                        System.out.println("failed");
                    }
                }
            };
        });

    }
}
