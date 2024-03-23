package com.example.easyenglish.Activity.WordActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easyenglish.R;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.os.SystemClock.sleep;


public class WordReviewActivity extends AppCompatActivity {
    private MediaPlayer mPlayer;
    private SoundPool mSound;
    private HashMap<Integer, Integer> soundPoolMap;
    private int process=0;
    private int ans[]={2,1,3,4};
    private String wordlist[]={"resemble","respective","reserve","resultant"};


    private String chinese1[]={"adv. 相反地；倒转地","vt. 类似，像","vt. 诊断；断定vi. 诊断；判断","n. 市郊，郊区"};
    private String chinese2[]={"adj. 分别的，各自的","vt. 表示，指示","n. 入口，进口；插入物；水湾v. 引进; 嵌入; 插入;","n. 到来；出现；基督降临；基督降临节"};
    private String chinese3[]={"n. 垃圾；废物vt. 丢弃；修剪树枝","adv. 明显地；无疑地，确实地","v. 预订；储备；拥有；n. 自然保护区；居住地；预备队；预备役部队；保留意见；储备金；","n. 可能性，可能"};
    private String chinese4[]={"n. 启示；揭露；出乎意料的事；被揭露的真相","adv. 不可避免地；必然地","n. 联盟，联合；联姻","adj. 由此导致的，因而发生的n. 合力，合成速率；结果"};
    private TextView star;
    private Button collect;
    private TextView progress;
    private TextView word;
    private TextView pronounce;
    private ProgressBar bar;
    private Button translate1;
    private Button translate2;
    private Button translate3;
    private Button translate4;
    private TextView stat1;
    private TextView stat2;
    private TextView stat3;
    private TextView stat4;
    private Handler handler=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_review);
        progress=findViewById(R.id.word_review_Progress);
        word=findViewById(R.id.word_review_word);
        pronounce=findViewById(R.id.word_review_pronounce);
        bar=findViewById(R.id.word_review_ProgressBar);
        translate1=findViewById(R.id.word_review_translate1);
        translate2=findViewById(R.id.word_review_translate2);
        translate3=findViewById(R.id.word_review_translate3);
        translate4=findViewById(R.id.word_review_translate4);
        collect=findViewById(R.id.word_review_collect);
        stat1=findViewById(R.id.word_review_stat1);
        stat2=findViewById(R.id.word_review_stat2);
        stat3=findViewById(R.id.word_review_stat3);
        stat4=findViewById(R.id.word_review_stat4);
        star=findViewById(R.id.word_study_star);
        collect=findViewById(R.id.word_study_collect);
        //new Thread(WordListRun).start();
        handler=new Handler();

        findViewById(R.id.word_review_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.word_review_voice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PlaySound(2,0);
                //mPlayer.start();
            }
        });
        findViewById(R.id.word_review_collect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        findViewById(R.id.word_review_translate1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(process==1){


                    Thread T1=new Thread(){
                        public void run(){
                            handler.post(myThread1);
                        }
                    };
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.submit(Thread1);
                    executor.submit(T1);
                    executor.shutdown();
                }
            }
        });
        findViewById(R.id.word_review_translate2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(process==0){


                    Thread T1=new Thread(){
                        public void run(){
                            handler.post(myThread2);
                        }
                    };
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.submit(Thread2);
                    executor.submit(T1);
                    executor.shutdown();
                }
            }
        });
        findViewById(R.id.word_review_translate3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(process==2){


                    Thread T1=new Thread(){
                        public void run(){
                            handler.post(myThread3);
                        }
                    };
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.submit(Thread3);
                    executor.submit(T1);
                    executor.shutdown();

                }
            }
        });
        findViewById(R.id.word_review_translate4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Thread T1=new Thread(){
                    public void run(){
                        handler.post(myThread4);
                    }
                };
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(Thread4);
                executor.submit(T1);
                executor.shutdown();
            }
        });

        /*
        findViewById(R.id.word_review_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView word=findViewById(R.id.word_review_word);
                TextView pronounce=findViewById(R.id.word_review_pronounce);
                Button translate1=findViewById(R.id.word_review_translate1);
                Button translate2=findViewById(R.id.word_review_translate2);
                Button translate3=findViewById(R.id.word_review_translate3);
                Button translate4=findViewById(R.id.word_review_translate4);
                Button unknown=findViewById(R.id.word_review_unknown);
                Button next=findViewById(R.id.word_review_next);
                Button collect=findViewById(R.id.word_review_collect);
                TextView stat1=findViewById(R.id.word_review_stat1);
                TextView stat2=findViewById(R.id.word_review_stat2);
                TextView stat3=findViewById(R.id.word_review_stat3);
                TextView stat4=findViewById(R.id.word_review_stat4);

                word.setText("alliance");
                pronounce.setText("[əˈlaɪəns]");
                unknown.setText("不认识");
                translate1.setText("vt. 背诵；叙述；列举vi. 背诵；叙述");
                translate2.setText("prep. 对，对抗；与……相对，与……相比");
                translate3.setText("n. 联盟，联合；联姻");
                translate4.setText("n. 牡蛎，[无脊椎] 蚝；沉默寡言的人");
                translate1.setTextColor(getResources().getColor(R.color.colorDefaultGray));
                translate2.setTextColor(getResources().getColor(R.color.colorDefaultGray));
                translate3.setTextColor(getResources().getColor(R.color.colorDefaultGray));
                translate4.setTextColor(getResources().getColor(R.color.colorDefaultGray));
                next.setBackgroundResource(R.drawable.btn_press);
                next.setText("");
                stat1.setText("");
                stat2.setText("");
                stat3.setText("");
                stat4.setText("");
                collect.setBackgroundResource(R.drawable.btn_collect);

            }
        });
        final int ans=2;
        findViewById(R.id.word_review_translate1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{

                    if(ans==1){

                        Thread myThread = new Thread(){//创建子线程
                            @Override
                            public void run() {
                                try{

                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        };
                        myThread.start();//启动线程


                    }
                    else{
                        Thread myThread = new Thread(){//创建子线程
                            @Override
                            public void run() {
                                try{
                                    Button right;
                                    TextView stat;
                                    if(ans==2){
                                        right=findViewById(R.id.word_review_translate2);
                                        stat=findViewById(R.id.word_review_stat2);
                                    }
                                    else if(ans==3){
                                        right=findViewById(R.id.word_review_translate3);
                                        stat=findViewById(R.id.word_review_stat3);
                                    }
                                    else{
                                        right=findViewById(R.id.word_review_translate4);
                                        stat=findViewById(R.id.word_review_stat4);
                                    }

                                    TextView progress=findViewById(R.id.word_review_Progress);
                                    TextView word=findViewById(R.id.word_review_word);
                                    TextView pronounce=findViewById(R.id.word_review_pronounce);
                                    ProgressBar bar=findViewById(R.id.word_review_ProgressBar);
                                    Button translate1=findViewById(R.id.word_review_translate1);
                                    Button translate2=findViewById(R.id.word_review_translate2);
                                    Button translate3=findViewById(R.id.word_review_translate3);
                                    Button translate4=findViewById(R.id.word_review_translate4);
                                    Button unknown=findViewById(R.id.word_review_unknown);
                                    Button next=findViewById(R.id.word_review_next);
                                    Button collect=findViewById(R.id.word_review_collect);
                                    TextView stat1=findViewById(R.id.word_review_stat1);
                                    TextView stat2=findViewById(R.id.word_review_stat2);
                                    TextView stat3=findViewById(R.id.word_review_stat3);
                                    TextView stat4=findViewById(R.id.word_review_stat4);

                                    translate1.setTextColor(getResources().getColor(R.color.colorRed));
                                    stat1.setText("×");
                                    stat1.setTextColor(getResources().getColor(R.color.colorRed));
                                    right.setTextColor(getResources().getColor(R.color.colorGreen));
                                    stat.setTextColor(getResources().getColor(R.color.colorGreen));
                                    stat.setText("√");
                                    unknown.setText("查看释义");
                                    next.setBackgroundResource(R.drawable.btn_shape);
                                    next.setText("下一个");

                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        };
                        myThread.start();//启动线程
                    }
                }
                catch(Exception e){
                    String TAG="word_review";
                    Log.e(TAG,Log.getStackTraceString(e));
                }

            }
            void UpdateView(){

            }
        });
        findViewById(R.id.word_review_translate2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.word_review_translate3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.word_review_translate4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.word_review_unknown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        */
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
    Runnable myThread1=new Runnable() {
        @Override
        public void run() {
            try{


                process=process+1;
                System.out.println(process);
                progress.setText(Integer.toString(process+1)+"/"+4);
                word.setText(wordlist[process]);
                pronounce.setText("[rɪˈzɜːv]");
                translate1.setText(chinese1[process]);
                translate2.setText(chinese2[process]);
                translate3.setText(chinese3[process]);
                translate4.setText(chinese4[process]);
                bar.setProgress(process+1);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
    Runnable Thread1=new Runnable() {
        @Override
        public void run() {
            try{
                translate1.setTextColor(getResources().getColor(R.color.colorGreen));
                stat1.setText("√");
                stat1.setTextColor(getResources().getColor(R.color.colorGreen));
                sleep(1000);
                translate1.setTextColor(getResources().getColor(R.color.colorDefaultGray));
                stat1.setText("");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
    Runnable Thread2=new Runnable() {
        @Override
        public void run() {
            try{
                translate2.setTextColor(getResources().getColor(R.color.colorGreen));
                stat2.setText("√");
                stat2.setTextColor(getResources().getColor(R.color.colorGreen));
                sleep(1000);
                translate2.setTextColor(getResources().getColor(R.color.colorDefaultGray));
                stat2.setText("");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
    Runnable Thread3=new Runnable() {
        @Override
        public void run() {
            try{
                translate3.setTextColor(getResources().getColor(R.color.colorGreen));
                stat3.setText("√");
                stat3.setTextColor(getResources().getColor(R.color.colorGreen));
                sleep(1000);
                translate3.setTextColor(getResources().getColor(R.color.colorDefaultGray));
                stat3.setText("");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
    Runnable Thread4=new Runnable() {
        @Override
        public void run() {
            try{
                translate4.setTextColor(getResources().getColor(R.color.colorGreen));
                stat4.setText("√");
                stat4.setTextColor(getResources().getColor(R.color.colorGreen));
                sleep(1000);
                translate4.setTextColor(getResources().getColor(R.color.colorDefaultGray));
                stat4.setText("");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
    Runnable myThread2=new Runnable() {
        @Override
        public void run() {
            try{


                process=process+1;
                System.out.println(process);
                progress.setText(Integer.toString(process+1)+"/"+4);
                word.setText(wordlist[process]);
                pronounce.setText("[rɪˈspektɪv]");
                translate1.setText(chinese1[process]);
                translate2.setText(chinese2[process]);
                translate3.setText(chinese3[process]);
                translate4.setText(chinese4[process]);
                bar.setProgress(process+1);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
    Runnable myThread3=new Runnable() {
        @Override
        public void run() {
            try{


                process=process+1;
                System.out.println(process);
                progress.setText(Integer.toString(process+1)+"/"+4);
                word.setText(wordlist[process]);
                pronounce.setText("[rɪˈzʌltənt]");
                translate1.setText(chinese1[process]);
                translate2.setText(chinese2[process]);
                translate3.setText(chinese3[process]);
                translate4.setText(chinese4[process]);
                bar.setProgress(process+1);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
    Runnable myThread4=new Runnable() {
        @Override
        public void run() {
            try{


                finish();
                Toast.makeText(getApplicationContext(),"恭喜您背完所有单词！", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
}
