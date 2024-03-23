package com.example.easyenglish.Activity.WordActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.alibaba.fastjson.JSONObject;
import com.example.easyenglish.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Connect.ConnectMessage;

import static android.os.SystemClock.sleep;

public class WordStudyActivity extends AppCompatActivity {
    private MediaPlayer mPlayer;
    private SoundPool mSound;
    private HashMap<Integer, Integer> soundPoolMap;
    private int process=0;
    private byte [] b=new byte[10000000];
    private String id;
    private String wordlist[]=new String[10];
    private String chinese[]=new String[10];
    private String[] s;
    private String wordchinese;
    private String wordsentence;
    private String wordsymbol;
    private TextView word_study_word;
    private TextView word_study_pronounce;
    private TextView word_study_translate;
    private TextView word_study_example;
    private TextView star;
    private Button collect;

    private Handler handler=null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_study);
        Intent getIntent=getIntent();
        id=getIntent.getStringExtra("1");
        word_study_word=findViewById(R.id.word_study_word);
        word_study_pronounce=findViewById(R.id.word_study_pronounce);
        word_study_translate=findViewById(R.id.word_study_translate);
        word_study_example=findViewById(R.id.word_study_example);
        star=findViewById(R.id.word_study_star);
        collect=findViewById(R.id.word_study_collect);
        handler=new Handler();

        int permission= ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permission!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10);
        }

        Thread T1=new Thread(){
            public void run(){
                handler.post(UpdateRun);
            }
        };
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(WordListRun);
        executor.submit(T1);
        executor.shutdown();
        /*
        new Thread(WordListRun).start();
        new Thread(){
            public void run(){
                handler.post(UpdateRun);
            }
        }.start();

         */
        //InitSounds();
        mSound = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);
        soundPoolMap = new HashMap<Integer, Integer>();
        //soundPoolMap.put(1, mSound.load(this, k, 1));
        soundPoolMap.put(2, mSound.load(this, R.raw.princess, 1));
        for(int i=0;i<10;i++){
            //soundPoolMap.put(i, mSound.load(this, R.raw.princess, 1));
        }
        for(int i=0;i<10;i++){
            wordlist[i]="trival";
        }
        new Thread(WordListRun).start();


        findViewById(R.id.word_study_Exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.word_study_collect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=(String)star.getText();
                sleep(350);
                if(s.equals("0")){
                    Intent intent=new Intent(WordStudyActivity.this, WordCollectingActivity.class);
                    intent.putExtra("1",wordlist[process]);
                    startActivityForResult(intent,1);

                    //collect.setBackgroundResource(R.drawable.btn_collected);
                    //star.setText("1");
                }
                else{
                    collect.setBackgroundResource(R.drawable.btn_collect);
                    star.setText("0");
                }
            }
        });
        findViewById(R.id.word_study_ErrorReport).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(WordStudyActivity.this, WordStudiedActivity.class));
            }
        });
        findViewById(R.id.word_study_voice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mSound.play(0, 1, 1, 100, 0, 1);
                PlaySound(2,0);
                 //mPlayer.start();
            }
        });

        findViewById(R.id.word_study_prev).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                try{
                    TextView word_study_progress=findViewById(R.id.word_study_Progress);
                    ProgressBar word_study_ProgressBar=findViewById(R.id.word_study_ProgressBar);
                    String s=(String)word_study_progress.getText();
                    String[] str = s.split("/");
                    int t=Integer.parseInt(str[0]);
                    if(t!=1){
                        process=t-2;
                        word_study_progress.setText(Integer.toString(t-1)+"/10");
                        word_study_ProgressBar.setProgress(t-1);

                        Thread T1=new Thread(){
                            public void run(){
                                handler.post(UpdateRun);
                            }
                        };
                        ExecutorService executor = Executors.newSingleThreadExecutor();
                        executor.submit(WordRun);
                        executor.submit(T1);
                        executor.shutdown();
                    }
                }
                catch(Exception e){
                    String TAG="word_study";
                    Log.e(TAG,Log.getStackTraceString(e));
                }
            }
        });
        findViewById(R.id.word_study_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    TextView word_study_progress=findViewById(R.id.word_study_Progress);
                    ProgressBar word_study_ProgressBar=findViewById(R.id.word_study_ProgressBar);
                    collect.setBackgroundResource(R.drawable.btn_collect);
                    star.setText("0");
                    String s=(String)word_study_progress.getText();
                    String[] str = s.split("/");
                    int t=Integer.parseInt(str[0]);
                    if(t!=10){
                        process=t;
                        word_study_progress.setText(Integer.toString(t+1)+"/10");
                        word_study_ProgressBar.setProgress(t+1);
                        Thread T1=new Thread(){
                            public void run(){
                                handler.post(UpdateRun);
                            }
                        };
                        ExecutorService executor = Executors.newSingleThreadExecutor();
                        executor.submit(WordRun);
                        executor.submit(T1);
                        executor.shutdown();
                    }
                    else{
                        finish();
                        Intent intent=new Intent(WordStudyActivity.this, WordStudiedActivity.class);
                        String s1="",s2="";
                        for(int i=0;i<10;i++){
                            if(i!=9){
                                s1=s1+wordlist[i]+"%";
                                s2=s2+chinese[i]+"%";
                            }
                            else{
                                s1=s1+wordlist[i];
                                s2=s2+chinese[i];
                            }
                        }
                        intent.putExtra("1",s1+"%"+s2);
                        intent.putExtra("2",id);
                        startActivityForResult(intent,1);
                    }
                }
                catch(Exception e){
                    String TAG="word_study";
                    Log.e(TAG,Log.getStackTraceString(e));
                }

            }
        });

    }
    private void InitSounds(){
        // 设置播放音效
        //mPlayer = MediaPlayer.create(this, R.raw.dream);
        // 第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量

        mSound = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);
        soundPoolMap = new HashMap<Integer, Integer>();
        //soundPoolMap.put(1, mSound.load(this, k, 1));
        soundPoolMap.put(2, mSound.load(this, R.raw.princess, 1));
        //可以在后面继续put音效文件

    }

    /**
     * soundPool播放
     *
     * @param sound
     *            播放第一个
     * @param loop
     *            是否循环
     */
    private void PlaySound(int sound, int loop) {

        AudioManager mgr = (AudioManager) this
                .getSystemService(Context.AUDIO_SERVICE);
        // 获取系统声音的当前音量
        float currentVolume = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        // 获取系统声音的最大音量
        float maxVolume = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        // 获取当前音量的百分比
        float volume = currentVolume / maxVolume;

        // 第一个参数是声效ID,第二个是左声道音量，第三个是右声道音量，第四个是流的优先级，最低为0，第五个是是否循环播放，第六个播放速度(1.0 =正常播放,范围0.5 - 2.0)
        mSound.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        collect.setBackgroundResource(R.drawable.btn_collected);
        star.setText("1");
        //if(data.getStringExtra("result").equals("ok")){


        //}


    }
    ConnectMessage a=new ConnectMessage();
    //Runnable
    Runnable WordListRun=new Runnable() {
        @Override
        public void run() {
            try{
                //20 获得新学习单词(指定单词数量，单词的类)
                JSONObject object = new JSONObject();
                //soundPoolMap.put(2, mSound.load(this, R.raw.princess, 1));
                object.put("jsonid","20");
                object.put("userid","1");
                object.put("wordtype","cet6");
                object.put("wordnumber","10");

                String result = object.toString();//json输出部分
                //Toast.makeText(getApplicationContext(),"注册成功", Toast.LENGTH_SHORT).show();
                Socket socket = new Socket(a.GetIpAddress(), a.GetPort());//此处连接后端
                //System.out.println(229);
                OutputStream os = socket.getOutputStream();
                DataOutputStream out=new DataOutputStream(os);
                out.writeUTF(result);// 向服务器传送json信息

                InputStream is = socket.getInputStream();
                DataInputStream in=new DataInputStream(is);
                String str = in.readUTF();//读入运行结果
                //System.out.println(230);
                object = JSONObject.parseObject(str);//转化为json
                String status = object.getString("status");
                String id = object.getString("userid");

                for(int i=0;i<10;i++){
                    wordlist[i]=object.getString("wordenglish"+i);
                }
                //System.out.println("word:"+wordlist[0]);

                //System.out.println(232);
                //16 单词英文 -> 单词详细信息
                object = new JSONObject();
                object.put("jsonid", "16");
                object.put("wordenglish", wordlist[process]);

                result = object.toString();//json输出部分


                socket = new Socket(a.GetIpAddress(), a.GetPort());//此处连接后端
                os = socket.getOutputStream();
                out=new DataOutputStream(os);
                out.writeUTF(result);// 向服务器传送json信息

                is = socket.getInputStream();
                in=new DataInputStream(is);
                str = in.readUTF();//读入运行结果

                object = JSONObject.parseObject(str);//转化为json

                wordchinese = object.getString("wordchinese");
                wordsentence = object.getString("wordsentence");
                wordsymbol = object.getString("wordsymbol");

                s=wordsentence.split("%");

                /*
                //30 单词英文 -> 声音
                for(int i=0;i<10;i++){
                    object = new JSONObject();
                    object.put("jsonid", "30");
                    object.put("wordenglish", wordlist[i]);

                    //System.out.println("id:"+id);
                    result = object.toString();//json输出部分
                    socket = new Socket(a.GetIpAddress(), a.GetPort());//此处连接后端

                    os = socket.getOutputStream();
                    out=new DataOutputStream(os);
                    out.writeUTF(result);// 向服务器传送json信息

                    is = socket.getInputStream();
                    in=new DataInputStream(is);
                    str = in.readUTF();//读入运行结果

                    object = JSONObject.parseObject(str);//转化为json

                    status = object.getString("status");
                    System.out.println(status);

                    in.read(b);
                }

                */
            }
            catch(Exception e){
                String TAG="word";
                Log.e(TAG,Log.getStackTraceString(e));
            }
        }

    };
    Runnable WordRun=new Runnable() {
        @Override
        public void run() {
            try{
                //16 单词英文 -> 单词详细信息
                JSONObject object = new JSONObject();
                object.put("jsonid", "16");
                object.put("wordenglish", wordlist[process]);
                //System.out.println(wordlist[process]);
                String result = object.toString();//json输出部分

                Socket socket = new Socket(a.GetIpAddress(), a.GetPort());//此处连接后端
                OutputStream os = socket.getOutputStream();
                DataOutputStream out=new DataOutputStream(os);
                out.writeUTF(result);// 向服务器传送json信息

                InputStream is = socket.getInputStream();
                DataInputStream in=new DataInputStream(is);
                String str = in.readUTF();//读入运行结果

                object = JSONObject.parseObject(str);//转化为json

                wordchinese = object.getString("wordchinese");
                wordsentence = object.getString("wordsentence");
                wordsymbol = object.getString("wordsymbol");
                System.out.println(wordchinese);
                System.out.println(wordsentence);
                System.out.println(wordsymbol);

                s=wordsentence.split("%");

            }
            catch(Exception e){
                String TAG="word";
                Log.e(TAG,Log.getStackTraceString(e));
            }
        }
    };

    Runnable UpdateRun=new Runnable() {
        @Override
        public void run() {
            try{
                word_study_word.setText(wordlist[process]);
                word_study_pronounce.setText(wordsymbol);
                word_study_translate.setText(wordchinese);
                word_study_example.setText(s[0]+"\n"+s[1]);
                chinese[process]=wordchinese;
            }
            catch(Exception e){
                String TAG="word";
                Log.e(TAG,Log.getStackTraceString(e));
            }
        }
    };
}
