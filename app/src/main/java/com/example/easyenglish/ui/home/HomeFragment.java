package com.example.easyenglish.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSONObject;
import com.example.easyenglish.Activity.WordActivity.WordCollectActivity;
import com.example.easyenglish.Activity.WordActivity.WordReviewActivity;
import com.example.easyenglish.Activity.WordActivity.WordSearchActivity;
import com.example.easyenglish.Activity.WordActivity.WordStudyActivity;
import com.example.easyenglish.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import Connect.ConnectMessage;

import static android.os.SystemClock.sleep;

public class HomeFragment extends Fragment {

    private Button word_search;
    private Button word_study;
    private Button word_review;
    private Button word_setting;
    private Button word_fun5;
    private TextView word_studynumber;
    private TextView word_reviewnumber;
    private TextView word_label;
    private TextView word_process;
    private ProgressBar word_progressbar;
    private String username;
    private String id;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent getIntent=getActivity().getIntent();
        username=getIntent.getStringExtra("1");

        word_search=getActivity().findViewById(R.id.word_Label);
        word_study =getActivity().findViewById(R.id.word_Study);
        word_review=getActivity().findViewById(R.id.word_Review);
        word_setting=getActivity().findViewById(R.id.word_Setting);
        word_fun5=getActivity().findViewById(R.id.word_LabelChange5);
        word_studynumber=getActivity().findViewById(R.id.word_StudyNumber);
        word_reviewnumber=getActivity().findViewById(R.id.word_ReviewNumber);
        word_label=getActivity().findViewById(R.id.word_LabelSelect);
        word_process=getActivity().findViewById(R.id.word_Process);
        word_progressbar=getActivity().findViewById(R.id.word_ProgressBar);
        new Thread(LoginRun).start();
        word_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WindowManager wm = (WindowManager) getContext()
                        .getSystemService(Context.WINDOW_SERVICE);
                /*
                int width = wm.getDefaultDisplay().getWidth();
                int height = wm.getDefaultDisplay().getHeight();
                System.out.println("width:"+width);
                System.out.println("height:"+height);
                 */
                startActivity(new Intent(getActivity(), WordSearchActivity.class));
            }
        });

        //修改今日新词

        word_study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), WordStudyActivity.class);
                intent.putExtra("1",id);
                startActivityForResult(intent,1);
            }
        });
        word_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), WordReviewActivity.class);
                intent.putExtra("1",id);
                startActivityForResult(intent,1);
            }
        });

        word_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //startActivity(new Intent(getActivity(), WordReviewActivity.class));
            }
        });

        word_fun5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sleep(300);
                startActivity(new Intent(getActivity(), WordCollectActivity.class));
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //String result = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据
        if(requestCode==1){
            try{
                //new Thread(LoginRun).start();
                sleep(500);
                //修改学习计划
                //word_label.setText("四级词汇");
                //修改今日新词
                //String s=(String)word_studynumber.getText();
                int n=30;
                word_studynumber.setText(Integer.toString(n));
                //修改复习任务
                word_reviewnumber.setText("10");
                word_review.setBackground(getActivity().getResources().getDrawable(R.drawable.btn_bg));
                //修改进度条
                int now=10;
                int total=3071;
                word_process.setText("当前进度 "+Integer.toString(now)+"/"+Integer.toString(total));
                word_progressbar.setProgress(now);
                word_progressbar.setMax(total);


            }
            catch(Exception e){
                String TAG="word";
                Log.e(TAG,Log.getStackTraceString(e));
            }


        }

    }
    ConnectMessage a=new ConnectMessage();
    Runnable LoginRun=new Runnable() {
        @Override
        public void run() {
            try{

                //31 邮箱/手机号->用户id
                JSONObject object = new JSONObject();
                object.put("jsonid", "31");
                if(username.indexOf("@")==-1){//手机登录
                    object.put("type","phone");
                }
                else{//邮箱注册
                    object.put("type","mail");
                }
                object.put("typemessage", username);


                String result = object.toString();//json输出部分
                //Toast.makeText(getApplicationContext(),"注册成功", Toast.LENGTH_SHORT).show();
                Socket socket = new Socket(a.GetIpAddress(), a.GetPort());//此处连接后端

                OutputStream os = socket.getOutputStream();
                DataOutputStream out=new DataOutputStream(os);
                out.writeUTF(result);// 向服务器传送json信息

                InputStream is = socket.getInputStream();
                DataInputStream in=new DataInputStream(is);
                String str = in.readUTF();//读入运行结果

                object = JSONObject.parseObject(str);//转化为json
                String status = object.getString("status");
                id = object.getString("userid");

                //22 用户id 单词类型 -> 已学习单词数量
                object = new JSONObject();
                object.put("jsonid", "22");
                object.put("wordtype","cet6");
                object.put("userid",id);

                result = object.toString();//json输出部分
                //Toast.makeText(getApplicationContext(),"注册成功", Toast.LENGTH_SHORT).show();
                socket = new Socket(a.GetIpAddress(), a.GetPort());//此处连接后端

                os = socket.getOutputStream();
                out=new DataOutputStream(os);
                out.writeUTF(result);// 向服务器传送json信息
                is = socket.getInputStream();
                in=new DataInputStream(is);
                str = in.readUTF();//读入运行结果
                object = JSONObject.parseObject(str);//转化为json
                status = object.getString("status");
                String number = object.getString("wordnumber");
                int study_number=0;
                if(number!=null){
                    study_number=Integer.parseInt(number);
                }

                //26 用户id 单词类型 -> 是否复习 复习单词数量
                object = new JSONObject();
                object.put("jsonid", "26");
                object.put("wordtype","cet6");
                object.put("userid",id);

                result = object.toString();//json输出部分
                //Toast.makeText(getApplicationContext(),"注册成功", Toast.LENGTH_SHORT).show();
                socket = new Socket(a.GetIpAddress(), a.GetPort());//此处连接后端

                os = socket.getOutputStream();
                out=new DataOutputStream(os);
                out.writeUTF(result);// 向服务器传送json信息

                is = socket.getInputStream();
                in=new DataInputStream(is);
                str = in.readUTF();//读入运行结果

                object = JSONObject.parseObject(str);//转化为json
                status = object.getString("status");
                String isreview = object.getString("isreview");

                int review_number=0;
                if(isreview!=null){
                    //28 用户id 单词类型 -> 需复习单词数量
                    object = new JSONObject();
                    object.put("jsonid", "28");
                    object.put("wordtype","cet6");
                    object.put("userid",id);

                    result = object.toString();//json输出部分
                    //Toast.makeText(getApplicationContext(),"注册成功", Toast.LENGTH_SHORT).show();
                    socket = new Socket(a.GetIpAddress(), a.GetPort());//此处连接后端

                    os = socket.getOutputStream();
                    out=new DataOutputStream(os);
                    out.writeUTF(result);// 向服务器传送json信息

                    is = socket.getInputStream();
                    in=new DataInputStream(is);
                    str = in.readUTF();//读入运行结果

                    object = JSONObject.parseObject(str);//转化为json
                    status = object.getString("status");
                    number = object.getString("wordnumber");

                    if(number!=null){
                        review_number=Integer.parseInt(number);
                    }
                }

                word_studynumber.setText(String.valueOf(40-study_number));
                //修改复习任务
                word_reviewnumber.setText(String.valueOf(review_number));

                if(40-study_number==0){
                    word_study.setBackground(getActivity().getResources().getDrawable(R.drawable.btn_gray));

                    word_study.setEnabled(false);
                }
                else{
                    word_review.setBackground(getActivity().getResources().getDrawable(R.drawable.btn_bg));
                    word_review.setEnabled(true);
                }
                if(review_number==0){
                    word_review.setBackground(getActivity().getResources().getDrawable(R.drawable.btn_gray));
                    //word_review.setEnabled(false);
                }
                else{
                    word_review.setBackground(getActivity().getResources().getDrawable(R.drawable.btn_bg));
                    word_review.setEnabled(true);
                }
                //修改进度条
                int now=study_number;
                int total=3071;
                word_process.setText("当前进度 "+Integer.toString(now)+"/"+Integer.toString(total));
                word_progressbar.setProgress(now);
                word_progressbar.setMax(total);
                //System.out.println("number:"+n);

            }
            catch(Exception e){
                String TAG="word";
                Log.e(TAG,Log.getStackTraceString(e));
            }
        }

    };
}
