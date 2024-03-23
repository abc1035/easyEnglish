package com.example.easyenglish.Activity.WordActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easyenglish.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.os.SystemClock.sleep;

public class WordCollectedActivity extends AppCompatActivity {
    private ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_collected);
        List<HashMap<String,String>> data2=new ArrayList<>();
        HashMap<String,String> map=new HashMap<>();
        map.put("text1","resemble");
        map.put("text2","vt. 类似，像");
        data2.add(map);
        map=new HashMap<>();
        map.put("text1","respective");
        map.put("text2","adj. 分别的，各自的");
        data2.add(map);
        map.put("text1","reserve");
        map.put("text2","v. 预订；储备；拥有；n. 自然保护区；居住地；预备队；预备役部队；保留意见；储备金；");
        data2.add(map);
        map=new HashMap<>();
        map.put("text1","resultant");
        map.put("text2","adj. 由此导致的，因而发生的n. 合力，合成速率；结果");
        data2.add(map);

        SimpleAdapter simpleAdapter=new SimpleAdapter(WordCollectedActivity.this,
                data2,
                android.R.layout.simple_list_item_2,
                new String []{"text1","text2"},
                new int []{android.R.id.text1,android.R.id.text2});

        listview=findViewById(R.id.word_collected_listview);
        listview.setAdapter(simpleAdapter);
        listview.setTextFilterEnabled(true);//设置lv可以被过虑
        findViewById(R.id.word_collected_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                sleep(450);
                startActivity(new Intent(WordCollectedActivity.this, WordReviewActivity.class));
            }
        });
    }
}