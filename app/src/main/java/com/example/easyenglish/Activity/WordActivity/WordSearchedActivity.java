package com.example.easyenglish.Activity.WordActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easyenglish.R;

public class WordSearchedActivity extends AppCompatActivity {
    private SearchView searchview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_searched);
        Intent getIntent=getIntent();
        String s=getIntent.getStringExtra("1");
        TextView t=findViewById(R.id.word_searched_pronounce);
        //t.setText(s);
        Intent intent = new Intent();
        intent.putExtra("3","main");
        setResult(2,intent);
        searchview=findViewById(R.id.word_searched_searchview);
        searchview.setIconifiedByDefault(false);
        // 为该SearchView组件设置事件监听器

        searchview.setQueryHint("revive");
        findViewById(R.id.word_searched_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.word_searched_searchview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.word_searched_collect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.word_searched_voice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
