package com.example.easyenglish.Activity;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.example.easyenglish.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import Connect.ConnectMessage;

public class RegisterActivity extends AppCompatActivity {

    EditText Username,Password,RePassword,Vericode,Nickname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.register_getvericode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Username=findViewById(R.id.register_username);
                final String name=Username.getText().toString().trim();
                if(name.equals(""))
                    Toast.makeText(RegisterActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                else
                    new Thread(VericodeRun).start();
            }
            ConnectMessage a=new ConnectMessage();
            Runnable VericodeRun=new Runnable() {
                @Override
                public void run() {
                    try{
                        Username=findViewById(R.id.register_username);
                        final String name=Username.getText().toString().trim();
                        JSONObject object = new JSONObject();
                        if(name.indexOf("@")==-1){//手机注册
                            object.put("jsonid", "5");
                            object.put("userphonenumber",name);

                        }
                        else{//邮箱注册
                            object.put("jsonid", "3");
                            object.put("mail",name);
                        }
                        final String result = object.toString();
                        Socket socket = new Socket(a.GetIpAddress(), 6666);
                        OutputStream os = socket.getOutputStream();
                        DataOutputStream out=new DataOutputStream(os);
                        out.writeUTF(result);// 向服务器传送json信息

                        InputStream is = socket.getInputStream();
                        DataInputStream in=new DataInputStream(is);
                        String str = in.readUTF();//读入运行结果
                        object = JSONObject.parseObject(str);//转化为json
                        int status = object.getInteger("status");
                        Looper.prepare();
                        if(status==1)
                            Toast.makeText(RegisterActivity.this,"验证码已发送",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(RegisterActivity.this,"该用户已注册",Toast.LENGTH_SHORT).show();
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
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Username=findViewById(R.id.register_username);
                Nickname=findViewById(R.id.register_nickname);
                Password=findViewById(R.id.register_password);
                RePassword=findViewById(R.id.register_repassword);
                Vericode=findViewById(R.id.register_vericode);

                final String name=Username.getText().toString().trim();
                final String nickname=Nickname.getText().toString().trim();
                final String password=Password.getText().toString().trim();
                final String password2=RePassword.getText().toString().trim();
                final String vericode=Vericode.getText().toString().trim();
                if(name.equals(""))
                    Toast.makeText(RegisterActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                else if(nickname.equals(""))
                    Toast.makeText(RegisterActivity.this,"请输入昵称",Toast.LENGTH_SHORT).show();
                else if(password.equals(""))
                    Toast.makeText(RegisterActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                else if(!password.equals(password2))
                    Toast.makeText(RegisterActivity.this,"两次密码输入不一致",Toast.LENGTH_SHORT).show();
                else if(vericode.equals(""))
                    Toast.makeText(RegisterActivity.this,"请输入验证码",Toast.LENGTH_SHORT).show();
                else
                    new Thread(RegisterRun).start();
            }
            ConnectMessage a=new ConnectMessage();
            Runnable RegisterRun=new Runnable() {
                @Override
                public void run() {
                    try{
                        Username=findViewById(R.id.register_username);
                        Nickname=findViewById(R.id.register_nickname);
                        Password=findViewById(R.id.register_password);
                        Vericode=findViewById(R.id.register_vericode);

                        final String name=Username.getText().toString().trim();
                        final String nickname=Nickname.getText().toString().trim();
                        final String password=Password.getText().toString().trim();
                        final String vericode=Vericode.getText().toString().trim();
                        JSONObject object = new JSONObject();
                        if(name.indexOf("@")==-1){//手机注册
                            object.put("jsonid", "6");
                            object.put("userphonenumber",name);

                        }
                        else{//邮箱注册
                            object.put("jsonid", "4");
                            object.put("mail",name);
                        }
                        object.put("username",nickname);
                        object.put("userpassword",password);
                        object.put("verificationcode",vericode);
                        final String result = object.toString();
                        Socket socket = new Socket(a.GetIpAddress(), 6666);
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
                            Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                        else if(status==1)
                            Toast.makeText(RegisterActivity.this,"该用户已注册",Toast.LENGTH_SHORT).show();
                        else if(status==2)
                            Toast.makeText(RegisterActivity.this,"验证码错误或超时，请重试",Toast.LENGTH_SHORT).show();
                        else{
                            LoginActivity log=new LoginActivity();
                            finish();
                            Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
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
