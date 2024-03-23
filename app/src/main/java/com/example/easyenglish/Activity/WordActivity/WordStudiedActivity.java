package com.example.easyenglish.Activity.WordActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.example.easyenglish.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Connect.ConnectMessage;

public class WordStudiedActivity extends AppCompatActivity {
    private ListView listview;
    private String[] wordlist;
    private String[] chinese;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_studied);
        Intent getIntent=getIntent();
        String s1=getIntent.getStringExtra("1");
        id=getIntent.getStringExtra("2");

        wordlist=s1.split("%");

        List<HashMap<String,String>> data=new ArrayList<>();
        for(int i=0;i<10;i++){
            HashMap<String,String> map=new HashMap<>();
            map.put("text1",wordlist[i]);
            map.put("text2",wordlist[10+i]);
            data.add(map);
        }

        SimpleAdapter simpleAdapter=new SimpleAdapter(WordStudiedActivity.this,
                data,
                android.R.layout.simple_list_item_2,
                new String []{"text1","text2"},
                new int []{android.R.id.text1,android.R.id.text2});

        listview=findViewById(R.id.word_studied_listview);
        listview.setAdapter(simpleAdapter);
        listview.setTextFilterEnabled(true);//设置lv可以被过滤
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(WordStudiedActivity.this, WordSearchedActivity.class);
                intent.putExtra("1",wordlist[i]);
                startActivityForResult(intent,1);
                /*
                switch (i){
                    case 0:
                        Toast.makeText(WordSearchActivity.this,"你点击了"+i+"按钮",Toast.LENGTH_SHORT).show();
                        break;//当我们点击某一项就能吐司我们点了哪一项

                    case 1:
                        Toast.makeText(WordSearchActivity.this,"你点击了"+i+"按钮",Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        Toast.makeText(WordSearchActivity.this,"你点击了"+i+"按钮",Toast.LENGTH_SHORT).show();
                        break;

                    case 3:
                        Toast.makeText(WordSearchActivity.this,"你点击了"+i+"按钮",Toast.LENGTH_SHORT).show();
                        break;

                    case 4:
                        Toast.makeText(WordSearchActivity.this,"你点击了"+i+"按钮",Toast.LENGTH_SHORT).show();
                        break;
                }
                 */
            }
        });
        findViewById(R.id.word_studied_Exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.word_studied_Retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(WordStudiedActivity.this, WordStudyActivity.class);
                //intent.putExtra("1","Activity");
                startActivityForResult(intent,1);
            }
        });
        findViewById(R.id.word_studied_Finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                new Thread(studiedRun).start();
                Toast.makeText(getApplicationContext(),"恭喜您学完一组单词!",Toast.LENGTH_SHORT).show();

            }
        });
    }
    ConnectMessage a=new ConnectMessage();
    Runnable studiedRun=new Runnable() {
        @Override
        public void run() {
            try{
                //16 单词英文 -> 单词详细信息
                JSONObject object = new JSONObject();
                object.put("jsonid", "21");
                object.put("userid", id);
                object.put("wordtype", "cet6");
                object.put("wordnumber", "10");
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

                String status = object.getString("status");
                System.out.println(status);

            }
            catch(Exception e){
                String TAG="word";
                Log.e(TAG,Log.getStackTraceString(e));
            }
        }

    };
}
