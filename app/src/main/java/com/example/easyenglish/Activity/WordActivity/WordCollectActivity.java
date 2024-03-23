package com.example.easyenglish.Activity.WordActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.easyenglish.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.os.SystemClock.sleep;

public class WordCollectActivity extends Activity{
    private ListView listview;
    // 自动完成的列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_collect);
        List<HashMap<String,String>> data2=new ArrayList<>();
        HashMap<String,String> map=new HashMap<>();
        map.put("text1","text");
        map.put("text2","词根为text的单词");
        data2.add(map);
        map=new HashMap<>();
        map.put("text1","res");
        map.put("text2","res开头的单词");
        data2.add(map);

        SimpleAdapter simpleAdapter=new SimpleAdapter(WordCollectActivity.this,
                data2,
                android.R.layout.simple_list_item_2,
                new String []{"text1","text2"},
                new int []{android.R.id.text1,android.R.id.text2});

        listview=findViewById(R.id.word_collect_listview);
        listview.setAdapter(simpleAdapter);
        listview.setTextFilterEnabled(true);//设置lv可以被过虑
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                finish();
                sleep(350);
                Intent intent=new Intent(WordCollectActivity.this, WordCollectedActivity.class);
                intent.putExtra("1","Activity");
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if(resultCode == 2){
            //取消逻辑处理
            Toast.makeText(this, "您的选择是:" + data.getStringExtra("3"), Toast.LENGTH_SHORT).show();
        }

    }
}