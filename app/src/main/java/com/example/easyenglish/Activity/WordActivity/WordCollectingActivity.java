package com.example.easyenglish.Activity.WordActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easyenglish.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.os.SystemClock.sleep;

public class WordCollectingActivity extends AppCompatActivity {
    private ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_collecting);

        List<HashMap<String,String>> data2=new ArrayList<>();
        HashMap<String,String> map=new HashMap<>();
        map.put("text1","text");
        map.put("text2","词根为text的单词");
        data2.add(map);
        SimpleAdapter simpleAdapter=new SimpleAdapter(WordCollectingActivity.this,
                data2,
                android.R.layout.simple_list_item_2,
                new String []{"text1","text2"},
                new int []{android.R.id.text1,android.R.id.text2});

        listview=findViewById(R.id.word_collecting_listview);
        listview.setAdapter(simpleAdapter);
        listview.setTextFilterEnabled(true);//设置lv可以被过虑
        map=new HashMap<>();
        map.put("text1","res");
        map.put("text2","res开头的单词");
        data2.add(map);
        findViewById(R.id.word_collecting_Exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("result","ok");
                setResult(1,intent);
                finish();
            }
        });
        findViewById(R.id.word_collecting_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WordCollectingActivity.this, WordCollectCreateActivity.class);
                intent.putExtra("1","Activity");
                startActivityForResult(intent,1);
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Intent intent=new Intent(WordCollectingActivity.this, WordSearchedActivity.class);
                //intent.putExtra("1","Activity");
                //startActivityForResult(intent,1);
                sleep(300);
                new Thread(Run).start();
                finish();
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

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        List<HashMap<String,String>> data2=new ArrayList<>();
        HashMap<String,String> map=new HashMap<>();
        map.put("text1","text");
        map.put("text2","词根为text的单词");
        data2.add(map);
        map=new HashMap<>();
        map.put("text1","res");
        map.put("text2","res开头的单词");
        data2.add(map);


        SimpleAdapter simpleAdapter=new SimpleAdapter(WordCollectingActivity.this,
                data2,
                android.R.layout.simple_list_item_2,
                new String []{"text1","text2"},
                new int []{android.R.id.text1,android.R.id.text2});

        listview=findViewById(R.id.word_collecting_listview);
        listview.setAdapter(simpleAdapter);
        listview.setTextFilterEnabled(true);//设置lv可以被过虑

    }
    Runnable Run=new Runnable() {
        @Override
        public void run() {
            try{
                Looper.prepare();
                Toast.makeText(getApplicationContext(),"收藏成功！",Toast.LENGTH_SHORT).show();

                Looper.loop();

            }
            catch(Exception e){
                String TAG="word";
                Log.e(TAG,Log.getStackTraceString(e));
            }
        }
    };
}
